package com.ong.desktop.vistas;

import com.ong.desktop.modelos.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FormularioUsuario {

    public interface AlGuardar {
        void guardar(Usuario usuario);
    }

    public static void mostrar(Stage padre, Usuario usuarioExistente, AlGuardar callback) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.WINDOW_MODAL);
        ventana.initOwner(padre);
        ventana.setTitle(usuarioExistente == null ? "Nuevo Usuario" : "Editar Usuario");

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

        Label titulo = new Label(usuarioExistente == null ? "Nuevo Usuario" : "Editar Usuario");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        titulo.setStyle("-fx-text-fill: #212529;");

        header.getChildren().addAll(iconoBack, titulo);

        // ===== Contenido =====
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));

        // ===== Tarjeta con ícono =====
        VBox tarjeta = new VBox(5);
        tarjeta.setPadding(new Insets(15));
        tarjeta.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: #f8c8e0;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 12px;"
        );

        Label lblIcono = new Label("👤");
        lblIcono.setStyle("-fx-font-size: 30px;");

        Label lblTituloTarjeta = new Label(usuarioExistente == null ? "Nuevo usuario" : "Editando usuario");
        lblTituloTarjeta.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblTituloTarjeta.setStyle("-fx-text-fill: #d63384;");

        tarjeta.getChildren().addAll(lblIcono, lblTituloTarjeta);

        // ===== Grid de campos =====
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);

        Label lblNombre = new Label("👤 Nombre *");
        lblNombre.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextField txtNombre = new TextField();
        txtNombre.setPrefHeight(38);

        Label lblEmail = new Label("📧 Email *");
        lblEmail.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        TextField txtEmail = new TextField();
        txtEmail.setPrefHeight(38);

        Label lblPassword = new Label("🔒 Contraseña *");
        lblPassword.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPrefHeight(38);

        Label lblRol = new Label("🛡️ Rol *");
        lblRol.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");
        ComboBox<String> cmbRol = new ComboBox<>();
        cmbRol.getItems().addAll("ADMINISTRADOR", "RESPONSABLE_INVENTARIO", "USUARIO_OPERATIVO", "CONSULTA");
        cmbRol.setValue("CONSULTA");
        cmbRol.setMaxWidth(Double.MAX_VALUE);
        cmbRol.setPrefHeight(38);

        CheckBox chkActivo = new CheckBox("✅ Usuario activo");
        chkActivo.setSelected(true);
        chkActivo.setStyle("-fx-font-size: 13px;");

        if (usuarioExistente != null) {
            txtNombre.setText(usuarioExistente.getNombre());
            txtEmail.setText(usuarioExistente.getEmail());
            txtPassword.setText(usuarioExistente.getPasswordHash());
            cmbRol.setValue(usuarioExistente.getRol() != null ? usuarioExistente.getRol() : "CONSULTA");
            chkActivo.setSelected(usuarioExistente.getActivo() != null ? usuarioExistente.getActivo() : true);
        }

        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 0, 1);
        grid.add(lblEmail, 1, 0);
        grid.add(txtEmail, 1, 1);

        grid.add(lblPassword, 0, 2);
        grid.add(txtPassword, 0, 3);
        grid.add(lblRol, 1, 2);
        grid.add(cmbRol, 1, 3);

        grid.add(chkActivo, 0, 4, 2, 1);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        contenido.getChildren().addAll(tarjeta, grid);

        // ===== Botones =====
        Button btnGuardar = new Button("💾  Guardar Usuario");
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
                    throw new Exception("Ingresá el nombre");
                }
                if (txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()) {
                    throw new Exception("Ingresá el email");
                }
                if (txtPassword.getText() == null || txtPassword.getText().isEmpty()) {
                    throw new Exception("Ingresá la contraseña");
                }

                Usuario u = new Usuario();
                u.setNombre(txtNombre.getText().trim());
                u.setEmail(txtEmail.getText().trim());
                u.setPasswordHash(txtPassword.getText());
                u.setRol(cmbRol.getValue());
                u.setActivo(chkActivo.isSelected());
                if (usuarioExistente != null) {
                    u.setIdUsuario(usuarioExistente.getIdUsuario());
                }
                callback.guardar(u);
                ventana.close();
            } catch (Exception ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Datos inválidos");
                alerta.setContentText(ex.getMessage());
                alerta.showAndWait();
            }
        });

        root.getChildren().addAll(header, contenido, botones);

        Scene scene = new Scene(root, 700, 500);
        ventana.setScene(scene);
        ventana.showAndWait();
    }
}