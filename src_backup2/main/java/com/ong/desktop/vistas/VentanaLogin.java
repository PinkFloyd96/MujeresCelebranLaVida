package com.ong.desktop.vistas;

import com.ong.desktop.modelos.Usuario;
import com.ong.desktop.servicios.ApiServicio;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VentanaLogin {

    public interface AlIniciarSesion {
        void iniciar(Usuario usuario);
    }

    public static void mostrar(Stage stage, AlIniciarSesion callback) {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #f8f9fa;");

        Label titulo = new Label("Sistema de Inventario");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));
        titulo.setStyle("-fx-text-fill: #d63384;");

        Label subtitulo = new Label("Mujeres Celebran la Vida");
        subtitulo.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 14px;");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.setMaxWidth(300);
        txtEmail.setPrefHeight(35);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.setMaxWidth(300);
        txtPassword.setPrefHeight(35);

        Button btnLogin = new Button("Iniciar Sesión");
        btnLogin.setStyle("-fx-background-color: #d63384; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 30px; -fx-cursor: hand;");
        btnLogin.setMaxWidth(300);
        
        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 12px;");
        lblError.setVisible(false);

        ApiServicio api = new ApiServicio();
        btnLogin.setOnAction(e -> {
            lblError.setVisible(false);
            try {
                Usuario usuario = api.login(txtEmail.getText(), txtPassword.getText());
                
                callback.iniciar(usuario);
            } catch (Exception ex) {
                lblError.setText(ex.getMessage());
                lblError.setVisible(true);
            }
        });

        txtPassword.setOnAction(e -> btnLogin.fire());

        root.getChildren().addAll(titulo, subtitulo, txtEmail, txtPassword, btnLogin, lblError);

        Scene scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesión - ONG");
        stage.show();
    }
}
