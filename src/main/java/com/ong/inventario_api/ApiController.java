package com.ong.inventario_api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {
    private final JdbcTemplate jdbc;
    private static final Pattern SAFE = Pattern.compile("[a-z_]+[a-z0-9_]*");
    private static final Map<String,String> TABLES = new LinkedHashMap<>();
    private static final Map<String,String> PK = new HashMap<>();
    static {
        add("articulos","articulo","id_articulo"); add("articulo","articulo","id_articulo");
        add("categorias","categoria","id_categoria"); add("categoria","categoria","id_categoria");
        add("ubicaciones","ubicacion","id_ubicacion"); add("ubicacion","ubicacion","id_ubicacion");
        add("entidades","tercero","id_tercero"); add("terceros","tercero","id_tercero"); add("tercero","tercero","id_tercero");
        add("usuarios","usuario","id_usuario"); add("usuario","usuario","id_usuario");
        add("roles","rol","id_rol"); add("rol","rol","id_rol"); add("usuario_rol","usuario_rol",null);
        add("donaciones","donacion","id_donacion"); add("donacion","donacion","id_donacion");
        add("detalle_donacion","detalle_donacion","id_detalle_donacion");
        add("entregas","entrega","id_entrega"); add("entrega","entrega","id_entrega");
        add("detalle_entrega","detalle_entrega","id_detalle_entrega");
        add("prestamos","prestamo","id_prestamo"); add("prestamo","prestamo","id_prestamo");
        add("detalle_prestamo","detalle_prestamo","id_detalle_prestamo");
        add("devoluciones","devolucion","id_devolucion"); add("devolucion","devolucion","id_devolucion");
        add("detalle_devolucion","detalle_devolucion","id_detalle_devolucion");
        add("reservas","reserva","id_reserva"); add("reserva","reserva","id_reserva");
        add("detalle_reserva","detalle_reserva","id_detalle_reserva");
        add("fotografias","fotografia","id_fotografia"); add("fotografia","fotografia","id_fotografia");
        add("stock","stock","id_stock"); add("movimientos","movimiento","id_movimiento"); add("historial","movimiento","id_movimiento"); add("movimiento","movimiento","id_movimiento");
    }
    private static void add(String route, String table, String pk) { TABLES.put(route,table); if(pk!=null) PK.put(table,pk); }
    public ApiController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping("/salud") public Map<String,Object> salud() { return Map.of("estado","ok","baseDeDatos","mujeres_celebran_la_vida"); }
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String,Object> body) {
        String username=Objects.toString(body.getOrDefault("usuario",body.getOrDefault("username","")),"");
        String password=Objects.toString(body.getOrDefault("contrasena",body.getOrDefault("password",body.getOrDefault("passwordHash",""))),"");
        List<Map<String,Object>> matches=jdbc.query("SELECT id_usuario,nombre,apellido,usuario,email,activo FROM usuario WHERE usuario=? AND contrasena=? AND activo=TRUE",(rs,n)->{
            Map<String,Object> m=new LinkedHashMap<>();m.put("idUsuario",rs.getInt("id_usuario"));m.put("nombre",rs.getString("nombre"));m.put("apellido",rs.getString("apellido"));m.put("usuario",rs.getString("usuario"));m.put("email",rs.getString("email"));m.put("activo",rs.getBoolean("activo"));return m;
        },username,password);
        if(matches.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Usuario o contraseña incorrectos");
        return ResponseEntity.ok(matches.get(0));
    }

    @GetMapping("/{recurso}")
    public List<Map<String,Object>> listar(@PathVariable String recurso) {
        String table = table(recurso);
        if (table.equals("articulo")) return listarArticulos();
        return jdbc.query("SELECT * FROM " + table + " ORDER BY 1", (rs, n) -> transform(rs, table));
    }

    @GetMapping("/{recurso}/{id}")
    public Map<String,Object> obtener(@PathVariable String recurso, @PathVariable int id) {
        String table = table(recurso);
        if (table.equals("articulo")) return listarArticulos().stream().filter(x -> Objects.equals(number(x.get("idArticulo")), id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe el artículo"));
        String pk = PK.get(table); if(pk==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Esta tabla no tiene identificador simple para esta operación");
        return jdbc.query("SELECT * FROM " + table + " WHERE " + pk + "=?", (rs,n)->transform(rs,table), id).stream().findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Registro no encontrado"));
    }

    @PostMapping("/{recurso}") @Transactional
    public ResponseEntity<Map<String,Object>> crear(@PathVariable String recurso, @RequestBody Map<String,Object> body) {
        String table=table(recurso);
        if(table.equals("articulo")) return ResponseEntity.status(201).body(crearArticulo(body));
        Map<String,Object> data=toColumns(body,table,false);
        if(data.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No se recibieron columnas válidas para la tabla " + table);
        String cols=String.join(",",data.keySet()); String marks=String.join(",",Collections.nCopies(data.size(),"?"));
        String sql="INSERT INTO "+table+" ("+cols+") VALUES ("+marks+")";
        String pk=PK.get(table);
        if(pk!=null) sql += " RETURNING " + pk;
        Object[] values=data.values().toArray();
        try {
            if(pk!=null) { Number id=jdbc.queryForObject(sql, Number.class, values); return ResponseEntity.status(201).body(obtener(recurso,id.intValue())); }
            jdbc.update(sql,values); return ResponseEntity.status(201).body(data);
        } catch(DataIntegrityViolationException ex) { throw new ResponseStatusException(HttpStatus.CONFLICT,"No se pudo guardar: revisá claves relacionadas y campos obligatorios de la base de datos."); }
    }

    @PutMapping("/{recurso}/{id}") @Transactional
    public Map<String,Object> actualizar(@PathVariable String recurso,@PathVariable int id,@RequestBody Map<String,Object> body) {
        String table=table(recurso);
        if(table.equals("articulo")) return actualizarArticulo(id,body);
        String pk=PK.get(table); if(pk==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Esta tabla no admite actualización por ID");
        Map<String,Object> data=toColumns(body,table,true); data.remove(pk);
        if(data.isEmpty()) return obtener(recurso,id);
        String sets=String.join(",",data.keySet().stream().map(k->k+"=?").toList()); List<Object> args=new ArrayList<>(data.values()); args.add(id);
        int changed=jdbc.update("UPDATE "+table+" SET "+sets+" WHERE "+pk+"=?",args.toArray());
        if(changed==0) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Registro no encontrado");
        return obtener(recurso,id);
    }

    @DeleteMapping("/{recurso}/{id}") @Transactional
    public ResponseEntity<Void> borrar(@PathVariable String recurso,@PathVariable int id) {
        String table=table(recurso); if(table.equals("articulo")) { jdbc.update("DELETE FROM fotografia WHERE id_articulo=?",id); jdbc.update("DELETE FROM stock WHERE id_articulo=?",id); table="articulo"; }
        String pk=PK.get(table); if(pk==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Esta tabla no admite eliminación por ID");
        try { int n=jdbc.update("DELETE FROM "+table+" WHERE "+pk+"=?",id); if(n==0) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Registro no encontrado"); return ResponseEntity.noContent().build(); }
        catch(DataIntegrityViolationException ex) { throw new ResponseStatusException(HttpStatus.CONFLICT,"No se puede eliminar porque otros registros dependen de este dato."); }
    }

    private List<Map<String,Object>> listarArticulos() {
        String sql="SELECT a.*, COALESCE(s.cantidad,0) AS cantidad, COALESCE(s.estado,'DISPONIBLE') AS estado_stock FROM articulo a LEFT JOIN LATERAL (SELECT SUM(cantidad) cantidad, MIN(estado) estado FROM stock WHERE id_articulo=a.id_articulo GROUP BY id_articulo) s ON TRUE ORDER BY a.id_articulo";
        return jdbc.query(sql,(rs,n)->{Map<String,Object> m=transform(rs,"articulo"); m.put("nombre",m.get("descripcion")); m.put("cantidad",rs.getInt("cantidad")); m.put("estadoActual",rs.getString("estado_stock")); m.put("estadoConservacion",m.get("condicion")); return m;});
    }
    private Map<String,Object> crearArticulo(Map<String,Object> body) {
        Map<String,Object> d=toColumns(body,"articulo",false);
        d.remove("cantidad"); d.remove("estado");
        if(!d.containsKey("descripcion") && body.get("nombre")!=null) d.put("descripcion",body.get("nombre"));
        if(!d.containsKey("tipo_control")) d.put("tipo_control","CANTIDAD"); if(!d.containsKey("fecha_ingreso")) d.put("fecha_ingreso",java.time.LocalDate.now()); if(!d.containsKey("stock_minimo")) d.put("stock_minimo",0);
        if(!d.containsKey("codigo_inventario")||!d.containsKey("descripcion")||!d.containsKey("id_categoria")||!d.containsKey("id_ubicacion")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El artículo requiere código, descripción, categoría y ubicación.");
        String cols=String.join(",",d.keySet()); String marks=String.join(",",Collections.nCopies(d.size(),"?"));
        Integer id=jdbc.queryForObject("INSERT INTO articulo ("+cols+") VALUES ("+marks+") RETURNING id_articulo",Integer.class,d.values().toArray());
        int qty=body.get("cantidad") instanceof Number x?Math.max(0,x.intValue()):0; String estado=Objects.toString(body.getOrDefault("estadoActual","DISPONIBLE"));
        if(qty>0) jdbc.update("INSERT INTO stock(id_articulo,cantidad,estado) VALUES (?,?,?)",id,qty,estado);
        return obtener("articulos",id);
    }
    private Map<String,Object> actualizarArticulo(int id,Map<String,Object> body) {
        Map<String,Object> d=toColumns(body,"articulo",true); Object qty=body.get("cantidad"); d.remove("cantidad"); d.remove("estado"); d.remove("id_articulo"); d.remove("estado_stock");
        if(!d.isEmpty()) {String sets=String.join(",",d.keySet().stream().map(k->k+"=?").toList());List<Object>a=new ArrayList<>(d.values());a.add(id);if(jdbc.update("UPDATE articulo SET "+sets+" WHERE id_articulo=?",a.toArray())==0)throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Artículo no encontrado");}
        if(qty instanceof Number q) {String st=Objects.toString(body.getOrDefault("estadoActual","DISPONIBLE")); jdbc.update("DELETE FROM stock WHERE id_articulo=?",id); if(q.intValue()>0) jdbc.update("INSERT INTO stock(id_articulo,cantidad,estado) VALUES (?,?,?)",id,q.intValue(),st);}
        return obtener("articulos",id);
    }
    private String table(String route) { String t=TABLES.get(route.toLowerCase(Locale.ROOT)); if(t==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Recurso API desconocido: "+route); return t; }
    private Map<String,Object> toColumns(Map<String,Object> body,String table,boolean update) {
        Set<String> allowed=columns(table); Map<String,Object> out=new LinkedHashMap<>();
        for(var e:body.entrySet()) { String key=snake(e.getKey()); if(e.getValue() instanceof Map<?,?> nested) { Object id=nested.get("id"+capitalize(tableIdName(table))); if(id==null) id=nested.values().stream().filter(Number.class::isInstance).findFirst().orElse(null); if(id!=null) e=new AbstractMap.SimpleEntry<>(e.getKey(),id); }
            String col=key;
            if(table.equals("articulo")) { if(key.equals("nombre")) col="descripcion"; if(key.equals("estado_actual")) col="estado"; if(key.equals("estado_conservacion")) col="condicion"; }
            if(table.equals("tercero")) { if(key.equals("tipo")) col="tipo_tercero"; if(key.equals("nombre_completo")) col="nombre"; if(key.equals("documento_identidad")) col="documento"; }
            if(table.equals("usuario")) { if(key.equals("password_hash")||key.equals("contrasena")) col="contrasena"; if(key.equals("username")) col="usuario"; }
            if(table.equals("donacion")) { if(key.equals("id_donante")) col="id_tercero"; if(key.equals("id_responsable_recepcion")) col="id_usuario"; if(key.equals("fecha_recepcion")) col="fecha_donacion"; }
            if(table.equals("entrega")) { if(key.equals("id_receptor")) col="id_tercero"; if(key.equals("id_responsable")) col="id_usuario"; if(key.equals("motivo_campana")) col="motivo"; }
            if(table.equals("prestamo")) { if(key.equals("id_receptor")) col="id_tercero"; if(key.equals("id_responsable")) col="id_usuario_registra"; if(key.equals("fecha_prevista_devolucion")) col="fecha_devolucion_prevista"; }
            if(table.equals("movimiento")) { if(key.equals("tipo_operacion")) col="tipo_movimiento"; if(key.equals("fecha_registro")) col="fecha_movimiento"; if(key.equals("descripcion_movimiento")) col="observaciones"; }
            if(allowed.contains(col)) out.put(col, normalizeDate(e.getValue(),col));
        }
        return out;
    }
    private Set<String> columns(String table) { return new HashSet<>(jdbc.query("SELECT column_name FROM information_schema.columns WHERE table_schema='public' AND table_name=? ORDER BY ordinal_position",(rs,n)->rs.getString(1),table)); }
    private Map<String,Object> transform(ResultSet rs,String table) throws SQLException {
        ResultSetMetaData md=rs.getMetaData(); Map<String,Object> m=new LinkedHashMap<>();
        for(int i=1;i<=md.getColumnCount();i++) { String col=md.getColumnLabel(i); Object val=rs.getObject(i); m.put(camel(col),val); }
        if(table.equals("tercero")) {m.put("idEntidad",m.get("idTercero"));m.put("tipo",m.get("tipoTercero"));m.put("nombreCompleto",m.get("nombre"));m.put("documentoIdentidad",m.get("documento"));}
        if(table.equals("usuario")) {m.put("passwordHash",m.get("contrasena"));m.put("rol",null);m.remove("contrasena");}
        if(table.equals("donacion")) {m.put("idDonacion",m.get("idDonacion"));m.put("fechaRecepcion",m.get("fechaDonacion"));}
        if(table.equals("movimiento")) {m.put("tipoOperacion",m.get("tipoMovimiento"));m.put("fechaRegistro",m.get("fechaMovimiento"));m.put("descripcionMovimiento",m.get("observaciones"));}
        return m;
    }
    private String snake(String s) { if(s.matches("[a-z0-9_]+")) return s; return s.replaceAll("([a-z0-9])([A-Z])","$1_$2").toLowerCase(Locale.ROOT); }
    private String camel(String s) { StringBuilder b=new StringBuilder();boolean up=false;for(char c:s.toCharArray()){if(c=='_'){up=true;}else{b.append(up?Character.toUpperCase(c):c);up=false;}}return b.toString(); }
    private Object normalizeDate(Object v,String col) { if(v instanceof String s && (col.startsWith("fecha_")||col.equals("fecha_movimiento"))) { try { if(col.endsWith("_movimiento")) return Timestamp.valueOf(s.replace('T',' ')); if(s.length()>=10)return java.sql.Date.valueOf(s.substring(0,10)); } catch(Exception ignored){} } return v; }
    private String tableIdName(String table) { return PK.getOrDefault(table,"id").replace("id_",""); }
    private String capitalize(String s) { return s.isEmpty()?s:Character.toUpperCase(s.charAt(0))+s.substring(1); }
    private Integer number(Object o) { return o instanceof Number n?n.intValue():null; }
}
