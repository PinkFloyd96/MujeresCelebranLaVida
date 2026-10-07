package com.ong.desktop.vistas;

import com.ong.desktop.modelos.Articulo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;

public class FormularioArticulo {

    public interface AlGuardar {
        void guardar(Articulo articulo);
    }

    public static void mostrar(Stage padre, Articulo articuloExistente, AlGuardar callback) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.WINDOW_MODAL);
        ventana.initOwner(padre);
        ventana.setTitle(articuloExistente == null ? "Nuevo Artículo" : "Editar Artículo");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtCodigo = new TextField();
        TextField txtNombre = new TextField();
        TextField txtDescripcion = new TextField();
        TextField txtCantidad = new TextField();
        TextField txtStockMinimo = new TextField();
        TextField txtColor = new TextField();
        TextField txtTamano = new TextField();
        TextField txtProcedencia = new TextField();
        TextField txtRutaFoto = new TextField();
        txtRutaFoto.setEditable(false);
        txtRutaFoto.setPromptText("Sin foto seleccionada");
        ImageView vistaPrevia = new ImageView();
        vistaPrevia.setFitHeight(80);
        vistaPrevia.setFitWidth(80);
        vistaPrevia.setPreserveRatio(true);
        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("DISPONIBLE", "RESERVADO", "PRESTADO", "EN_REPARACION", "ENTREGADO", "BAJA");
        ComboBox<String> cmbConservacion = new ComboBox<>();
        cmbConservacion.getItems().addAll("NUEVO", "BUENO", "REGULAR", "DETERIORADO");

        System.out.println("ABriendo formulario");
        System.out.println("Articulo existente: "
                + (articuloExistente != null ? "Si (id=" + articuloExistente.getIdArticulo() + ")" : "No(nuevo)"));
        if (articuloExistente != null) {
            System.out.println("Conservacion recibida: [" + articuloExistente.getEstadoConservacion() + "]");
            System.out.println("Estado recibido:[" + articuloExistente.getEstadoActual() + "]");

        }

        if (articuloExistente != null) {
            txtColor.setText(articuloExistente.getColor() != null ? articuloExistente.getColor() : "");
            txtTamano.setText(articuloExistente.getTamano() != null ? articuloExistente.getTamano() : "");
            txtProcedencia
                    .setText(articuloExistente.getProcedencia() != null ? articuloExistente.getProcedencia() : "");
            txtCodigo.setText(articuloExistente.getCodigoInventario());
            txtNombre.setText(articuloExistente.getNombre());
            txtDescripcion.setText(articuloExistente.getDescripcion());
            txtCantidad.setText(String.valueOf(articuloExistente.getCantidad()));
            txtStockMinimo.setText(String
                    .valueOf(articuloExistente.getStockMinimo() != null ? articuloExistente.getStockMinimo() : 0));

            String estado = articuloExistente.getEstadoActual();
            cmbEstado.setValue(estado != null ? estado : "DISPONIBLE");

            String conservacion = articuloExistente.getEstadoConservacion();
            cmbConservacion.setValue(conservacion != null ? conservacion : "BUENO");

            // ===== NUEVO: precarga de la foto =====
            if (articuloExistente.getRutaFoto() != null && !articuloExistente.getRutaFoto().isEmpty()) {
                txtRutaFoto.setText(articuloExistente.getRutaFoto());
                try {
                    File f = new File(articuloExistente.getRutaFoto());
                    if (f.exists()) {
                        vistaPrevia.setImage(new Image(f.toURI().toString()));
                    }
                } catch (Exception ex) {
                    System.out.println("No se pudo cargar la imagen: " + ex.getMessage());
                }
            }
        } else {
            cmbEstado.setValue("DISPONIBLE");
            cmbConservacion.setValue("BUENO");
        }

        grid.add(new Label("Código:"), 0, 0);
        grid.add(txtCodigo, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Descripción:"), 0, 2);
        grid.add(txtDescripcion, 1, 2);
        grid.add(new Label("Cantidad:"), 0, 3);
        grid.add(txtCantidad, 1, 3);
        grid.add(new Label("Estado:"), 0, 4);
        grid.add(cmbEstado, 1, 4);
        grid.add(new Label("Conservación:"), 0, 5);
        grid.add(cmbConservacion, 1, 5);
        grid.add(new Label("Stock mínimo:"), 0, 6);
        grid.add(txtStockMinimo, 1, 6);
        grid.add(new Label("Color:"), 0, 7);
        grid.add(txtColor, 1, 7);
        grid.add(new Label("Tamaño:"), 0, 8);
        grid.add(txtTamano, 1, 8);
        grid.add(new Label("Procedencia:"), 0, 9);
        grid.add(txtProcedencia, 1, 9);

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");
        Button btnSubirFoto = new Button("📷 Subir Foto");
        btnSubirFoto.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar foto del artículo");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File archivo = fc.showOpenDialog(null);
            if (archivo != null) {
                txtRutaFoto.setText(archivo.getAbsolutePath());
                try {
                    Image img = new Image(archivo.toURI().toString());
                    vistaPrevia.setImage(img);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnCancelar.setOnAction(e -> ventana.close());
        btnGuardar.setOnAction(e -> {
            try {
                Articulo a = new Articulo();
                a.setCodigoInventario(txtCodigo.getText());
                a.setNombre(txtNombre.getText());
                a.setDescripcion(txtDescripcion.getText());
                a.setCantidad(Integer.parseInt(txtCantidad.getText()));
                a.setEstadoActual(cmbEstado.getValue());
                a.setEstadoConservacion(cmbConservacion.getValue());
                a.setColor(txtColor.getText());
                a.setTamano(txtTamano.getText());
                a.setRutaFoto(txtRutaFoto.getText());
                a.setProcedencia(txtProcedencia.getText());

                if (!txtStockMinimo.getText().isEmpty()) {
                    a.setStockMinimo(Integer.parseInt(txtStockMinimo.getText()));
                } else {
                    a.setStockMinimo(0);
                }

                if (articuloExistente != null) {
                    a.setIdArticulo(articuloExistente.getIdArticulo());
                    a.setIdCategoria(articuloExistente.getIdCategoria());
                    a.setIdUbicacion(articuloExistente.getIdUbicacion());
                } else {
                    a.setIdCategoria(5);
                    a.setIdUbicacion(1);
                }
                System.out.println("=== DEBUG ARTÍCULO A GUARDAR ===");
                System.out.println("Código: " + a.getCodigoInventario());
                System.out.println("Nombre: " + a.getNombre());
                System.out.println("Cantidad: " + a.getCantidad());
                System.out.println("Estado: " + a.getEstadoActual());
                System.out.println("Conservación: " + a.getEstadoConservacion());
                System.out.println("ID Categoría: " + a.getIdCategoria());
                System.out.println("ID Ubicación: " + a.getIdUbicacion());
                System.out.println("Color: " + a.getColor());
                System.out.println("Tamaño: " + a.getTamano());
                System.out.println("Procedencia: " + a.getProcedencia());
                System.out.println("Ruta Foto: " + a.getRutaFoto());
                callback.guardar(a);
                ventana.close();
            } catch (Exception ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Datos inválidos");
                alerta.setContentText(ex.getMessage());
                alerta.showAndWait();
            }
        });

        grid.add(btnGuardar, 0, 11);
        grid.add(btnCancelar, 1, 11);
        grid.add(new Label("Foto:"), 0, 10);
        HBox fotoBox = new HBox(10, txtRutaFoto, btnSubirFoto, vistaPrevia);
        fotoBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        grid.add(fotoBox, 1, 10);

        Scene scene = new Scene(grid, 400, 350);
        ventana.setScene(scene);
        ventana.showAndWait();
    }
}