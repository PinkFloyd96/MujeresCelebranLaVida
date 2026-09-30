package com.ong.desktop.vistas;

import com.ong.desktop.modelos.Articulo;
import com.ong.desktop.servicios.ApiServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.ong.desktop.servicios.Exportador;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class PanelArticulos {

    private final ApiServicio api = new ApiServicio();
    private final TableView<Articulo> tabla = new TableView<>();
    private final Stage owner;

    private final ObservableList<Articulo> todosLosArticulos = FXCollections.observableArrayList();

    private final TextField txtBuscar = new TextField();
    private final ComboBox<String> cmbEstado = new ComboBox<>();
    private final ComboBox<String> cmbCategoria = new ComboBox<>();
    private final String rol;
    private Integer categoriaInicial = null;
    private Runnable alVolverDelDetalle;

    public PanelArticulos(Stage owner, String rol) {
        this.owner = owner;
        this.rol = rol;
    }

    public VBox construir() {
        TableColumn<Articulo, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idArticulo"));
        colId.setPrefWidth(50);

        TableColumn<Articulo, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoInventario"));
        colCodigo.setPrefWidth(110);

        TableColumn<Articulo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(180);

        TableColumn<Articulo, Integer> colCantidad = new TableColumn<>("Cant.");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCantidad.setPrefWidth(60);

        TableColumn<Articulo, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoActual"));
        colEstado.setPrefWidth(110);

        TableColumn<Articulo, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setPrefWidth(180);

        TableColumn<Articulo, String> colConservacion = new TableColumn<>("Conservación");
        colConservacion.setCellValueFactory(new PropertyValueFactory<>("estadoConservacion"));
        colConservacion.setPrefWidth(110);

        TableColumn<Articulo, String> colColor = new TableColumn<>("Color");
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colColor.setPrefWidth(90);

        TableColumn<Articulo, String> colTamano = new TableColumn<>("Tamaño");
        colTamano.setCellValueFactory(new PropertyValueFactory<>("tamano"));
        colTamano.setPrefWidth(80);

        TableColumn<Articulo, String> colProcedencia = new TableColumn<>("Procedencia");
        colProcedencia.setCellValueFactory(new PropertyValueFactory<>("procedencia"));
        colProcedencia.setPrefWidth(120);

        TableColumn<Articulo, Integer> colStockMin = new TableColumn<>("Stock mín.");
        colStockMin.setCellValueFactory(new PropertyValueFactory<>("stockMinimo"));
        colStockMin.setPrefWidth(80);

        TableColumn<Articulo, String> colFoto = new TableColumn<>("Foto");
        colFoto.setPrefWidth(60);
        colFoto.setCellValueFactory(new PropertyValueFactory<>("rutaFoto"));
        colFoto.setCellFactory(col -> new TableCell<Articulo, String>() {
            private final ImageView iv = new ImageView();
            {
                iv.setFitWidth(40);
                iv.setFitHeight(40);
                iv.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String ruta, boolean empty) {
                super.updateItem(ruta, empty);
                if (empty || ruta == null || ruta.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        File f = new File(ruta);
                        if (f.exists()) {
                            iv.setImage(new Image(f.toURI().toString()));
                            setGraphic(iv);
                        } else {
                            setGraphic(null);
                        }
                    } catch (Exception ex) {
                        setGraphic(null);
                    }
                }
            }
        });

        tabla.getColumns().addAll(colId, colFoto, colCodigo, colNombre, colCantidad, colEstado, colDescripcion,
                colConservacion, colColor, colTamano,
                colProcedencia, colStockMin);
        tabla.setRowFactory(tv -> {
            TableRow<Articulo> row = new TableRow<Articulo>() {
                @Override
                protected void updateItem(Articulo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setStyle("");
                    } else if (item.getStockMinimo() != null
                            && item.getCantidad() != null
                            && item.getCantidad() <= item.getStockMinimo()) {
                        setStyle("-fx-background-color: #ffcccc;");
                    } else {
                        setStyle("");
                    }
                }
            };
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    abrirDetalle(row.getItem());
                }
            });
            return row;
        });
        txtBuscar.setPromptText("🔍 Buscar por nombre, código o descripción...");
        txtBuscar.setPrefWidth(280);

        cmbEstado.setPromptText("Estado");
        cmbEstado.getItems().addAll("", "DISPONIBLE", "RESERVADO", "PRESTADO", "EN_REPARACION", "ENTREGADO", "BAJA");
        cmbEstado.setPrefWidth(140);

        cmbCategoria.setPromptText("Categoría");
        cmbCategoria.getItems().add("");
        try {
            api.listarCategorias()
                    .forEach(c -> cmbCategoria.getItems().add(c.getIdCategoria() + " - " + c.getNombre()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (categoriaInicial != null) {
            for (String item : cmbCategoria.getItems()) {
                if (item.startsWith(categoriaInicial + " - ")) {
                    cmbCategoria.setValue(item);
                    break;
                }
            }
        }
        cmbCategoria.setPrefWidth(180);

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-cursor: hand;");
        btnLimpiar.setOnAction(e -> {
            txtBuscar.clear();
            cmbEstado.setValue("");
            cmbCategoria.setValue("");
            aplicarFiltros();
        });

        txtBuscar.textProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        cmbEstado.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        cmbCategoria.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());

        boolean puedeEditar = !"CONSULTA".equals(rol);

        HBox barraFiltros = new HBox(10, txtBuscar, cmbEstado, cmbCategoria, btnLimpiar);
        barraFiltros.setStyle(
                "-fx-padding: 10px; -fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");

        Button btnVer = new Button(" Ver Detalle");
        Button btnNuevo = new Button("Nuevo");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");
        Button btnRecargar = new Button("Recargar");
        Button btnExportar = new Button("Exportar");

        btnNuevo.setOnAction(e -> abrirFormulario(null));
        btnEditar.setOnAction(e -> {
            Articulo sel = tabla.getSelectionModel().getSelectedItem();
            if (sel == null) {
                mostrarAlerta("Seleccioná un artículo primero");
                return;
            }
            abrirFormulario(sel);
        });
        btnEliminar.setOnAction(e -> eliminarSeleccionado());
        btnRecargar.setOnAction(e -> cargar());
        btnExportar.setOnAction(e -> exportar());
        btnVer.setOnAction(e -> {
            Articulo sel = tabla.getSelectionModel().getSelectedItem();
            if (sel == null) {
                mostrarAlerta("Seleccioná un artículo primero");
                return;
            }
            abrirDetalle(sel);
        });

        HBox barraBotones;
        if (puedeEditar) {
            barraBotones = new HBox(10, btnVer, btnNuevo, btnEditar, btnEliminar, btnRecargar, btnExportar);
        } else {
            barraBotones = new HBox(10, btnVer, btnRecargar, btnExportar);
        }
        barraBotones.setStyle("-fx-padding: 10px;");

        VBox panel = new VBox(0, barraFiltros, barraBotones, tabla);
        panel.setStyle("-fx-padding: 15px;");
        VBox.setVgrow(tabla, javafx.scene.layout.Priority.ALWAYS);

        cargar();
        return panel;
    }

    private void cargar() {
        try {
            List<Articulo> lista = api.listarArticulos();
            todosLosArticulos.setAll(lista);
            aplicarFiltros();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("No se pudo conectar con la API:\n" + e.getMessage());
        }
    }

    private void aplicarFiltros() {
        String texto = txtBuscar.getText() != null ? txtBuscar.getText().toLowerCase().trim() : "";
        String estado = cmbEstado.getValue();
        String categoria = cmbCategoria.getValue();

        Integer idCategoriaFiltro = null;
        if (categoria != null && !categoria.isEmpty()) {
            try {
                idCategoriaFiltro = Integer.parseInt(categoria.split(" - ")[0]);
            } catch (NumberFormatException ex) {
            }
        }

        final Integer idCat = idCategoriaFiltro;

        List<Articulo> filtrados = todosLosArticulos.stream()
                .filter(a -> {
                    if (!texto.isEmpty()) {
                        String nombre = a.getNombre() != null ? a.getNombre().toLowerCase() : "";
                        String codigo = a.getCodigoInventario() != null ? a.getCodigoInventario().toLowerCase() : "";
                        String desc = a.getDescripcion() != null ? a.getDescripcion().toLowerCase() : "";
                        if (!nombre.contains(texto) && !codigo.contains(texto) && !desc.contains(texto)) {
                            return false;
                        }
                    }
                    if (estado != null && !estado.isEmpty()) {
                        if (!estado.equals(a.getEstadoActual()))
                            return false;
                    }
                    if (idCat != null) {
                        if (a.getIdCategoria() == null || !a.getIdCategoria().equals(idCat))
                            return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        tabla.setItems(FXCollections.observableArrayList(filtrados));
    }

    private void abrirFormulario(Articulo existente) {
        FormularioArticulo.mostrar(owner, existente, articulo -> {
            try {
                if (articulo.getIdArticulo() == null) {
                    api.crearArticulo(articulo);
                } else {
                    api.actualizarArticulo(articulo.getIdArticulo(), articulo);
                }
                cargar();
            } catch (Exception ex) {
                ex.printStackTrace();
                mostrarAlerta("Error al guardar:\n" + ex.getMessage());
            }
        });
    }

    private void eliminarSeleccionado() {
        Articulo sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarAlerta("Seleccioná un artículo primero");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setHeaderText("¿Eliminar este artículo?");
        confirmacion.setContentText(sel.getNombre());
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                try {
                    api.borrarArticulo(sel.getIdArticulo());
                    cargar();
                } catch (Exception ex) {
                    mostrarAlerta("Error al eliminar:\n" + ex.getMessage());
                }
            }
        });
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void exportar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar reporte de artículos");
        fileChooser.setInitialFileName("articulos");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));

        File archivo = fileChooser.showSaveDialog(owner);
        if (archivo == null)
            return;

        try {
            // Preparar datos
            String[] encabezados = { "ID", "Código", "Nombre", "Cantidad", "Estado", "Descripción", "Conservación" };
            List<String[]> filas = new java.util.ArrayList<>();

            for (Articulo a : tabla.getItems()) {
                filas.add(new String[] {
                        String.valueOf(a.getIdArticulo()),
                        a.getCodigoInventario() != null ? a.getCodigoInventario() : "",
                        a.getNombre() != null ? a.getNombre() : "",
                        String.valueOf(a.getCantidad()),
                        a.getEstadoActual() != null ? a.getEstadoActual() : "",
                        a.getDescripcion() != null ? a.getDescripcion() : "",
                        a.getEstadoConservacion() != null ? a.getEstadoConservacion() : ""
                });
            }

            String ruta = archivo.getAbsolutePath();
            if (ruta.toLowerCase().endsWith(".pdf")) {
                Exportador.exportarPDF(ruta, "Reporte de Artículos", encabezados, filas);
            } else {
                if (!ruta.toLowerCase().endsWith(".xlsx")) {
                    ruta = ruta + ".xlsx";
                }
                Exportador.exportarExcel(ruta, "Artículos", encabezados, filas);
            }

            mostrarAlerta("Reporte exportado correctamente:\n" + ruta);
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Error al exportar:\n" + ex.getMessage());
        }
    }

    public void setCategoriaInicial(Integer idCategoria) {
        this.categoriaInicial = idCategoria;
    }

    private void abrirDetalle(Articulo articulo) {
        Stage ventanaDetalle = new Stage();
        ventanaDetalle.setTitle("Detalle del Artículo");

        PanelDetalleArticulo detalle = new PanelDetalleArticulo(
                owner, articulo, rol,
                () -> ventanaDetalle.close() // Al volver, cerrar la ventana
        );

        Scene scene = new Scene(detalle.construir(), 900, 700);
        ventanaDetalle.setScene(scene);
        ventanaDetalle.show();
    }

}