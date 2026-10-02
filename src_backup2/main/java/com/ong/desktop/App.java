package com.ong.desktop;

import com.ong.desktop.modelos.Usuario;
import com.ong.desktop.vistas.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private BorderPane root;
    private VBox menuLateral;
    private Button botonActivo;

    @Override
    public void start(Stage stage) {
        VentanaLogin.mostrar(stage, usuario -> {
            construirAppPrincipal(stage, usuario);
        });
    }

    private void construirAppPrincipal(Stage stage, Usuario usuario) {
        root = new BorderPane();

        Label titulo = new Label(
                "Sistema de Inventario - Mujeres Celebran la Vida   |   "
                        + usuario.getNombre() + " (" + usuario.getRol() + ")");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        HBox.setHgrow(titulo, javafx.scene.layout.Priority.ALWAYS);
        titulo.setMaxWidth(Double.MAX_VALUE);

        Button btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle(
                "-fx-background-color: #a02060; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 8px 15px; -fx-cursor: hand; -fx-background-radius: 5px;");
        btnCerrarSesion.setOnAction(e -> {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Cerrar Sesión");
            confirmacion.setHeaderText("¿Estás seguro que querés cerrar sesión?");
            confirmacion.setContentText("Vas a volver a la pantalla de login.");
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    stage.close();
                    Stage nuevoStage = new Stage();
                    VentanaLogin.mostrar(nuevoStage, usuarioNuevo -> construirAppPrincipal(nuevoStage, usuarioNuevo));
                }
            });
        });

        HBox barraSuperior = new HBox(15, titulo, btnCerrarSesion);
        barraSuperior.setStyle("-fx-background-color: #d63384; -fx-padding: 15px; -fx-alignment: center-left;");
        barraSuperior.setAlignment(Pos.CENTER_LEFT);

        root.setTop(barraSuperior);
        menuLateral = new VBox(5);
        menuLateral.setStyle(
                "-fx-background-color: #f8f9fa; -fx-padding: 15px; -fx-min-width: 200px; -fx-border-color: #dee2e6; -fx-border-width: 0 1px 0 0;");

        Label lblMenu = new Label("MÓDULOS");
        lblMenu.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6c757d; -fx-padding: 5px;");
        menuLateral.getChildren().add(lblMenu);

        String rol = usuario.getRol() != null ? usuario.getRol() : "CONSULTA";

        Button btnDashboard = crearBotonMenu("Dashboard");
        btnDashboard.setOnAction(e -> mostrarPanel(btnDashboard, new PanelDashboard().construir()));
        menuLateral.getChildren().add(btnDashboard);

        agregarBotonSi(rol, new String[] { "ADMINISTRADOR", "RESPONSABLE_INVENTARIO", "USUARIO_OPERATIVO", "CONSULTA" },
                "Artículos", stage, "articulos");
        agregarBotonSi(rol, new String[] { "ADMINISTRADOR", "RESPONSABLE_INVENTARIO", "CONSULTA" },
                "Categorías", stage, "categorias");
        agregarBotonSi(rol, new String[] { "ADMINISTRADOR", "RESPONSABLE_INVENTARIO", "CONSULTA" },
                "Ubicaciones", stage, "ubicaciones");
        agregarBotonSi(rol, new String[] { "ADMINISTRADOR" },
                "Usuarios", stage, "usuarios");
        agregarBotonSi(rol, new String[] { "ADMINISTRADOR", "RESPONSABLE_INVENTARIO", "CONSULTA" },
                "Entidades", stage, "entidades");
        agregarBotonSi(rol, new String[] { "ADMINISTRADOR", "RESPONSABLE_INVENTARIO", "CONSULTA" },
                "Donaciones", stage, "donaciones");
        agregarBotonSi(rol, new String[] { "ADMINISTRADOR", "USUARIO_OPERATIVO", "CONSULTA" },
                "Préstamos", stage, "prestamos");
        agregarBotonSi(rol, new String[] { "ADMINISTRADOR", "RESPONSABLE_INVENTARIO", "USUARIO_OPERATIVO", "CONSULTA" },
                "Entregas", stage, "entregas");
        agregarBotonSi(rol, new String[] { "ADMINISTRADOR", "RESPONSABLE_INVENTARIO", "USUARIO_OPERATIVO", "CONSULTA" },
                "Historial", stage, "historial");

        root.setLeft(menuLateral);

        mostrarPanel(btnDashboard, new PanelDashboard().construir());
        mostrarPanel(btnDashboard, new PanelDashboard().construir());

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Inventario - ONG");
        stage.setScene(scene);
        stage.show();
    }

    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(
                "-fx-background-color: transparent; -fx-padding: 10px 15px; -fx-font-size: 14px; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> {
            if (btn != botonActivo) {
                btn.setStyle(
                        "-fx-background-color: #e9ecef; -fx-padding: 10px 15px; -fx-font-size: 14px; -fx-cursor: hand;");
            }
        });
        btn.setOnMouseExited(e -> {
            if (btn != botonActivo) {
                btn.setStyle(
                        "-fx-background-color: transparent; -fx-padding: 10px 15px; -fx-font-size: 14px; -fx-cursor: hand;");
            }
        });
        return btn;
    }

    private void mostrarPanel(Button boton, VBox panel) {
        if (botonActivo != null) {
            botonActivo.setStyle(
                    "-fx-background-color: transparent; -fx-padding: 10px 15px; -fx-font-size: 14px; -fx-cursor: hand;");
        }
        botonActivo = boton;
        boton.setStyle(
                "-fx-background-color: #d63384; -fx-text-fill: white; -fx-padding: 10px 15px; -fx-font-size: 14px; -fx-cursor: hand;");
        root.setCenter(panel);
    }

    
    private void agregarBotonSi(String rolUsuario, String[] rolesPermitidos, String textoBoton, Stage stage,
            String tipo) {
        boolean permitido = false;
        for (String r : rolesPermitidos) {
            if (r.equals(rolUsuario)) {
                permitido = true;
                break;
            }
        }
        if (!permitido)
            return;

        Button btn = crearBotonMenu(textoBoton);
        btn.setOnAction(e -> mostrarPanel(btn, crearPanelPorTipo(tipo, stage, rolUsuario)));
        menuLateral.getChildren().add(btn);
    }

        private VBox crearPanelPorTipo(String tipo, Stage stage, String rol) {
        switch (tipo) {
            case "articulos":   return new PanelArticulos(stage, rol).construir();
            case "categorias":  return new PanelCategorias(stage, rol).construir();
            case "ubicaciones": return new PanelUbicaciones(stage, rol).construir();
            case "usuarios":    return new PanelUsuarios(stage, rol).construir();
            case "entidades":   return new PanelEntidades(stage, rol).construir();
            case "donaciones":  return new PanelDonaciones(stage, rol).construir();
            case "prestamos":   return new PanelPrestamos(stage, rol).construir();
            case "entregas":    return new PanelEntregas(stage, rol).construir();
            case "historial":   return new PanelHistorial(stage, rol).construir();
            default: throw new IllegalArgumentException("Tipo desconocido: " + tipo);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
