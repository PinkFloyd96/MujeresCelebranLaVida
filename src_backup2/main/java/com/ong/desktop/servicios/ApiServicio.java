package com.ong.desktop.servicios;
import com.ong.desktop.modelos.Categoria;
import com.ong.desktop.modelos.Ubicacion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ong.desktop.modelos.Articulo;
import com.ong.desktop.modelos.Usuario;
import com.ong.desktop.modelos.Entidad;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import com.ong.desktop.modelos.DonacionRecepcion;
import com.ong.desktop.modelos.Prestamo;
import com.ong.desktop.modelos.EntregaDefinitiva;
import com.ong.desktop.modelos.HistorialMovimiento;

public class ApiServicio {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiServicio() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Articulo> listarArticulos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/articulos"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<Articulo>>() {});
    }

    public Articulo crearArticulo(Articulo articulo) throws Exception {
        String json = objectMapper.writeValueAsString(articulo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/articulos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear. Código: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(), Articulo.class);
    }

    public Articulo actualizarArticulo(Integer id, Articulo articulo) throws Exception {
        String json = objectMapper.writeValueAsString(articulo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/articulos/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), Articulo.class);
    }

    public void borrarArticulo(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/articulos/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar. Código: " + response.statusCode());
        }
    }

    public List<Categoria> listarCategorias() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/categorias"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar categorías. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<Categoria>>() {});
    }

    public Categoria crearCategoria(Categoria categoria) throws Exception {
        String json = objectMapper.writeValueAsString(categoria);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/categorias"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear categoría. Código: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(), Categoria.class);
    }

    public Categoria actualizarCategoria(Integer id, Categoria categoria) throws Exception {
        String json = objectMapper.writeValueAsString(categoria);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/categorias/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar categoría. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), Categoria.class);
    }

    public void borrarCategoria(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/categorias/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar categoría. Código: " + response.statusCode());
        }
    }


    public List<Ubicacion> listarUbicaciones() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ubicaciones"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar ubicaciones. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<Ubicacion>>() {});
    }

    public Ubicacion crearUbicacion(Ubicacion ubicacion) throws Exception {
        String json = objectMapper.writeValueAsString(ubicacion);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ubicaciones"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear ubicación. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), Ubicacion.class);
    }

    public Ubicacion actualizarUbicacion(Integer id, Ubicacion ubicacion) throws Exception {
        String json = objectMapper.writeValueAsString(ubicacion);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ubicaciones/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar ubicación. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), Ubicacion.class);
    }

    public void borrarUbicacion(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ubicaciones/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar ubicación. Código: " + response.statusCode());
        }
    }


    public List<Usuario> listarUsuarios() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/usuarios"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar usuarios. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<Usuario>>() {});
    }

    public Usuario crearUsuario(Usuario usuario) throws Exception {
        String json = objectMapper.writeValueAsString(usuario);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/usuarios"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear usuario. Código: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(), Usuario.class);
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuario) throws Exception {
        String json = objectMapper.writeValueAsString(usuario);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/usuarios/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar usuario. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), Usuario.class);
    }

    public void borrarUsuario(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/usuarios/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar usuario. Código: " + response.statusCode());
        }
    }


    public List<Entidad> listarEntidades() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/entidades"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar entidades. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<Entidad>>() {});
    }

    public Entidad crearEntidad(Entidad entidad) throws Exception {
        String json = objectMapper.writeValueAsString(entidad);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/entidades"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear entidad. Código: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(), Entidad.class);
    }

    public Entidad actualizarEntidad(Integer id, Entidad entidad) throws Exception {
        String json = objectMapper.writeValueAsString(entidad);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/entidades/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar entidad. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), Entidad.class);
    }

    public void borrarEntidad(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/entidades/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar entidad. Código: " + response.statusCode());
        }
    }

    public List<DonacionRecepcion> listarDonaciones() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/donaciones"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar donaciones. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<DonacionRecepcion>>() {});
    }

    public DonacionRecepcion crearDonacion(DonacionRecepcion donacion) throws Exception {
        String json = objectMapper.writeValueAsString(donacion);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/donaciones"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear donación. Código: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(), DonacionRecepcion.class);
    }

    public DonacionRecepcion actualizarDonacion(Integer id, DonacionRecepcion donacion) throws Exception {
        String json = objectMapper.writeValueAsString(donacion);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/donaciones/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar donación. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), DonacionRecepcion.class);
    }

    public void borrarDonacion(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/donaciones/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar donación. Código: " + response.statusCode());
        }
    }

    public List<Prestamo> listarPrestamos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/prestamos"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar préstamos. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<Prestamo>>() {});
    }

    public Prestamo crearPrestamo(Prestamo prestamo) throws Exception {
        String json = objectMapper.writeValueAsString(prestamo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/prestamos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear préstamo. Código: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(), Prestamo.class);
    }

    public Prestamo actualizarPrestamo(Integer id, Prestamo prestamo) throws Exception {
        String json = objectMapper.writeValueAsString(prestamo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/prestamos/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar préstamo. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), Prestamo.class);
    }

    public void borrarPrestamo(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/prestamos/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar préstamo. Código: " + response.statusCode());
        }
    }

    public List<EntregaDefinitiva> listarEntregas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/entregas"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar entregas. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<EntregaDefinitiva>>() {});
    }

    public EntregaDefinitiva crearEntrega(EntregaDefinitiva entrega) throws Exception {
        String json = objectMapper.writeValueAsString(entrega);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/entregas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear entrega. Código: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(), EntregaDefinitiva.class);
    }

    public EntregaDefinitiva actualizarEntrega(Integer id, EntregaDefinitiva entrega) throws Exception {
        String json = objectMapper.writeValueAsString(entrega);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/entregas/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar entrega. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), EntregaDefinitiva.class);
    }

    public void borrarEntrega(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/entregas/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar entrega. Código: " + response.statusCode());
        }
    }

    public List<HistorialMovimiento> listarHistorial() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/historial"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al listar historial. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), new TypeReference<List<HistorialMovimiento>>() {});
    }

    public HistorialMovimiento crearHistorial(HistorialMovimiento movimiento) throws Exception {
        String json = objectMapper.writeValueAsString(movimiento);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/historial"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al crear movimiento. Código: " + response.statusCode() + " - " + response.body());
        }
        return objectMapper.readValue(response.body(), HistorialMovimiento.class);
    }

    public HistorialMovimiento actualizarHistorial(Integer id, HistorialMovimiento movimiento) throws Exception {
        String json = objectMapper.writeValueAsString(movimiento);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/historial/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al actualizar movimiento. Código: " + response.statusCode());
        }
        return objectMapper.readValue(response.body(), HistorialMovimiento.class);
    }

    public void borrarHistorial(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/historial/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Error al borrar movimiento. Código: " + response.statusCode());
        }
    }


    public Usuario login(String email, String password) throws Exception {
        String json = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), Usuario.class);
        } else if (response.statusCode() == 401) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        } else {
            throw new RuntimeException("Error al iniciar sesión. Código: " + response.statusCode());
        }
    }
}
