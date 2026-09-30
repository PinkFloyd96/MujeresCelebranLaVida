package com.ong.desktop.vistas;

import com.ong.desktop.modelos.Categoria;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FormularioCategoria {

    public interface AlGuardar {
        void guardar(Categoria categoria);
    }

    public static void mostrar(Stage padre, Categoria categoriaExistente, AlGuardar callback) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.WINDOW_MODAL);
        ventana.initOwner(padre);
        ventana.setTitle(categoriaExistente == null ? "Nueva Categoría" : "Editar Categoría");

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #fdf6fa;");

        // ===== Header =====
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: white; -fx-border-color: transparent transparent #f0d0e0 transparent; -fx-border-width: 0 0 1px 0;");

        Label iconoBack = new Label("↩");
        iconoBack.setStyle("-fx-font-size: 20px; -fx-cursor: hand;");
        iconoBack.setOnMouseClicked(e -> ventana.close());

        Label titulo = new Label(categoriaExistente == null ? "Nueva Categoría" : "Editar Categoría");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        titulo.setStyle("-fx-text-fill: #212529;");

        header.getChildren().addAll(iconoBack, titulo);

        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));

        VBox tarjeta = new VBox(5);
        tarjeta.setPadding(new Insets(15));
        tarjeta.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: #f8c8e0;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 12px;"
        );

        Label lblIcono = new Label("🏷️");
        lblIcono.setStyle("-fx-font-size: 30px;");

        Label lblTituloTarjeta = new Label(categoriaExistente == null ? "Nueva categoría" : "Editando categoría");
        lblTituloTarjeta.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblTituloTarjeta.setStyle("-fx-text-fill: #d63384;");

        tarjeta.getChildren().addAll(lblIcono, lblTituloTarjeta);

        Label lblNombre = new Label("🏷️ Nombre *");
        lblNombre.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextField txtNombre = new TextField();
        txtNombre.setPrefHeight(38);
        txtNombre.setPromptText("Ej: Calzado, Vestimenta, Muebles...");

        Label lblDescripcion = new Label("💬 Descripción");
        lblDescripcion.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextArea txtDescripcion = new TextArea();
        txtDescripcion.setPrefRowCount(3);
        txtDescripcion.setPrefHeight(80);
        txtDescripcion.setWrapText(true);
        txtDescripcion.setPromptText("Ej: Zapatos, sandalias, botas...");

        if (categoriaExistente != null) {
            txtNombre.setText(categoriaExistente.getNombre());
            txtDescripcion.setText(categoriaExistente.getDescripcion());
        }

        contenido.getChildren().addAll(tarjeta, lblNombre, txtNombre, lblDescripcion, txtDescripcion);

        Button btnGuardar = new Button("💾  Guardar Categoría");
        btnGuardar.setPrefHeight(45);
        btnGuardar.setMaxWidth(Double.MAX_VALUE);
        btnGuardar.setStyle(
                "-fx-background-color: #d63384; -fx-text-fill: white;" +
                "-fx-font-size: 14px; -fx-font-weight: bold;" +
                "-fx-background-radius: 10px; -fx-cursor: hand;"
        );

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefHeight(45);
        btnCancelar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #6c757d;" +
                "-fx-border-color: #6c757d; -fx-border-width: 1px;" +
                "-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-cursor: hand;"
        );
        btnCancelar.setOnAction(e -> ventana.close());

        HBox botones = new HBox(10, btnCancelar, btnGuardar);
        HBox.setHgrow(btnGuardar, Priority.ALWAYS);
        botones.setPadding(new Insets(20));
        botones.setStyle("-fx-background-color: white; -fx-border-color: #f0d0e0 transparent transparent transparent; -fx-border-width: 1px 0 0 0;");

        btnGuardar.setOnAction(e -> {
            try {
                if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
                    throw new Exception("Ingresá un nombre para la categoría");
                }
                Categoria c = new Categoria();
                c.setNombre(txtNombre.getText().trim());
                c.setDescripcion(txtDescripcion.getText());
                if (categoriaExistente != null) {
                    c.setIdCategoria(categoriaExistente.getIdCategoria());
                }
                callback.guardar(c);
                ventana.close();
            } catch (Exception ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Datos inválidos");
                alerta.setContentText(ex.getMessage());
                alerta.showAndWait();
            }
        });

        root.getChildren().addAll(header, contenido, botones);

        Scene scene = new Scene(root, 500, 500);
        ventana.setScene(scene);
        ventana.showAndWait();
    }
}