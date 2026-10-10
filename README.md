# API — Mujeres Celebran la Vida

Esta versión usa únicamente las tablas del esquema definitivo PostgreSQL `mujeres_celebran_la_vida`. No ejecuta scripts de creación o modificación de tablas (`spring.sql.init.mode=never`) y Hibernate/JPA fue retirado: las consultas se realizan con JDBC contra las tablas existentes.

## Antes de ejecutarla

1. Hacé una copia de seguridad de la carpeta de la API anterior.
2. Abrí `src/main/resources/application.properties`.
3. Si tu usuario PostgreSQL tiene contraseña, definí la variable de entorno `DB_PASSWORD` con tu contraseña local. No la compartas ni la subas a GitHub. También se puede ajustar `DB_USERNAME` si tu usuario no es `postgres`.
4. Confirmá que PostgreSQL esté iniciado y que exista la base `mujeres_celebran_la_vida`.
5. Desde esta carpeta, en Windows ejecutá `mvnw.cmd spring-boot:run` (necesita Java 17 o posterior e Internet la primera vez para descargar dependencias).
6. Comprobá `http://localhost:8080/api/salud`. Debe devolver estado `ok`.

## Rutas

Se exponen CRUD (`GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}`) para los recursos que corresponden a las tablas finales:

- `/api/articulos` → `articulo` y lectura de cantidades desde `stock`
- `/api/stock` → `stock`
- `/api/categorias` → `categoria`
- `/api/ubicaciones` → `ubicacion`
- `/api/entidades` y `/api/terceros` → `tercero`
- `/api/usuarios` → `usuario`
- `/api/roles` y `/api/usuario_rol`
- `/api/donaciones` y `/api/detalle_donacion`
- `/api/entregas` y `/api/detalle_entrega`
- `/api/prestamos` y `/api/detalle_prestamo`
- `/api/devoluciones` y `/api/detalle_devolucion`
- `/api/reservas` y `/api/detalle_reserva`
- `/api/fotografias` → `fotografia`
- `/api/movimientos` y `/api/historial` → `movimiento`

También existe `POST /api/auth/login` para validar credenciales contra `usuario`. Este inicio de sesión es básico y no reemplaza una implementación de seguridad con contraseñas cifradas.

## Importante sobre el esquema

La API filtra los nombres de columna contra las columnas que existen realmente en cada tabla, pero las operaciones de negocio que involucran encabezado + detalle deben enviarse a sus rutas correspondientes (por ejemplo, guardar una donación y luego sus filas de `detalle_donacion`). No se crean automáticamente detalles ni movimientos al crear un encabezado. Antes de usarla con datos reales, probá primero con registros de prueba y validá las claves foráneas y los valores permitidos de `estado` y `tipo_movimiento`.

Las rutas antiguas del escritorio se mantienen como alias para facilitar la integración, pero algunos objetos compuestos de la aplicación de escritorio pueden requerir ajustes en `ApiServicio.java` para enviar los IDs (`idTercero`, `idUsuario`, etc.) y los detalles por separado.
