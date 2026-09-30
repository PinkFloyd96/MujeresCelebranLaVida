package com.ong.desktop.vistas;

import com.ong.desktop.modelos.Entidad;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FormularioEntidad {

    public interface AlGuardar {
        void guardar(Entidad entidad);
    }

    public static void mostrar(Stage padre, Entidad entidadExistente, AlGuardar callback) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.WINDOW_MODAL);
        ventana.initOwner(padre);
        ventana.setTitle(entidadExistente == null ? "Nueva Entidad" : "Editar Entidad");

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

        Label titulo = new Label(entidadExistente == null ? "Nueva Entidad" : "Editar Entidad");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        titulo.setStyle("-fx-text-fill: #212529;");

        header.getChildren().addAll(iconoBack, titulo);

        // ===== Contenido =====
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

        Label lblIcono = new Label("🏢");
        lblIcono.setStyle("-fx-font-size: 30px;");

        Label lblTituloTarjeta = new Label(entidadExistente == null ? "Nueva entidad" : "Editando entidad");
        lblTituloTarjeta.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblTituloTarjeta.setStyle("-fx-text-fill: #d63384;");

        tarjeta.getChildren().addAll(lblIcono, lblTituloTarjeta);

        // ===== Grid de campos =====
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);

        Label lblTipo = new Label("🏷️ Tipo *");
        lblTipo.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("PERSONA", "FAMILIA", "INSTITUCION", "DONANTE");
        cmbTipo.setValue("PERSONA");
        cmbTipo.setMaxWidth(Double.MAX_VALUE);
        cmbTipo.setPrefHeight(38);

        Label lblNombre = new Label("👤 Nombre completo *");
        lblNombre.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextField txtNombre = new TextField();
        txtNombre.setPrefHeight(38);

        Label lblDoc = new Label("🆔 Documento");
        lblDoc.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextField txtDoc = new TextField();
        txtDoc.setPrefHeight(38);

        Label lblTel = new Label("📞 Teléfono");
        lblTel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextField txtTel = new TextField();
        txtTel.setPrefHeight(38);

        Label lblEmail = new Label("📧 Email");
        lblEmail.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextField txtEmail = new TextField();
        txtEmail.setPrefHeight(38);

        Label lblDir = new Label("🏠 Dirección");
        lblDir.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextField txtDir = new TextField();
        txtDir.setPrefHeight(38);

        Label lblObs = new Label("💬 Observaciones");
        lblObs.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextArea txtObs = new TextArea();
        txtObs.setPrefRowCount(2);
        txtObs.setPrefHeight(60);
        txtObs.setWrapText(true);

        if (entidadExistente != null) {
            cmbTipo.setValue(entidadExistente.getTipo() != null ? entidadExistente.getTipo() : "PERSONA");
            txtNombre.setText(entidadExistente.getNombreCompleto());
            txtDoc.setText(entidadExistente.getDocumentoIdentidad());
            txtTel.setText(entidadExistente.getTelefono());
            txtEmail.setText(entidadExistente.getEmail());
            txtDir.setText(entidadExistente.getDireccion());
            txtObs.setText(entidadExistente.getObservaciones());
        }

        grid.add(lblTipo, 0, 0);
        grid.add(cmbTipo, 0, 1);
        grid.add(lblNombre, 1, 0);
        grid.add(txtNombre, 1, 1);

        grid.add(lblDoc, 0, 2);
        grid.add(txtDoc, 0, 3);
        grid.add(lblTel, 1, 2);
        grid.add(txtTel, 1, 3);

        grid.add(lblEmail, 0, 4);
        grid.add(txtEmail, 0, 5);
        grid.add(lblDir, 1, 4);
        grid.add(txtDir, 1, 5);

        grid.add(lblObs, 0, 6, 2, 1);
        grid.add(txtObs, 0, 7, 2, 1);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        contenido.getChildren().addAll(tarjeta, grid);

        
        Button btnGuardar = new Button("💾  Guardar Entidad");
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
                    throw new Exception("Ingresá el nombre completo");
                }

                Entidad en = new Entidad();
                en.setTipo(cmbTipo.getValue());
                en.setNombreCompleto(txtNombre.getText().trim());
                en.setDocumentoIdentidad(txtDoc.getText());
                en.setTelefono(txtTel.getText());
                en.setEmail(txtEmail.getText());
                en.setDireccion(txtDir.getText());
                en.setObservaciones(txtObs.getText());
                if (entidadExistente != null) {
                    en.setIdEntidad(entidadExistente.getIdEntidad());
                }
                callback.guardar(en);
                ventana.close();
            } catch (Exception ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Datos inválidos");
                alerta.setContentText(ex.getMessage());
                alerta.showAndWait();
            }
        });

        root.getChildren().addAll(header, contenido, botones);

        Scene scene = new Scene(root, 700, 650);
        ventana.setScene(scene);
        ventana.showAndWait();
    }
}