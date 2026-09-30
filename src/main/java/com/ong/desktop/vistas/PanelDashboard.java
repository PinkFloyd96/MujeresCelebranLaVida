package com.ong.desktop.vistas;

import com.ong.desktop.modelos.Articulo;
import com.ong.desktop.modelos.Categoria;
import com.ong.desktop.modelos.Usuario;
import com.ong.desktop.servicios.ApiServicio;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.ong.desktop.servicios.Notificaciones;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

public class PanelDashboard {

    private final ApiServicio api = new ApiServicio();
    private final Stage owner;
    private final Usuario usuario;
    private final Consumer<Integer> alHacerClicCategoria;

    private static final Map<String, String> EMOJIS = new HashMap<>();
    static {
        EMOJIS.put("Calzado", "👞");
        EMOJIS.put("Vestimenta", "👕");
        EMOJIS.put("Muebles", "🪑");
        EMOJIS.put("Servicios", "🛠️");
        EMOJIS.put("Electrodomésticos", "🔌");
        EMOJIS.put("Tecnología", "💻");
        EMOJIS.put("Otro", "📦");
    }

    public PanelDashboard(Stage owner, Usuario usuario, Consumer<Integer> alHacerClicCategoria) {
        this.owner = owner;
        this.usuario = usuario;
        this.alHacerClicCategoria = alHacerClicCategoria;
    }

    public VBox construir() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: linear-gradient(to bottom right, #fdf6fa, #f8c8e0);");

        HBox barra = new HBox(15);
        barra.setAlignment(Pos.CENTER_LEFT);

        ImageView logo = new ImageView();
        try {
            logo.setImage(new Image(getClass().getResourceAsStream("/logo.png")));
            logo.setFitHeight(70);
            logo.setPreserveRatio(true);
        } catch (Exception ex) {
            Label fallback = new Label("💗");
            fallback.setStyle("-fx-font-size: 40px;");
            logo = null;
        }

       
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        StackPane campanaConBadge = new StackPane();
        Label campana = new Label("🔔");
        campana.setStyle("-fx-font-size: 24px; -fx-cursor: hand;");

        List<Notificaciones.Alerta> alertas = new ArrayList<>();
        try {
            List<Articulo> articulos = api.listarArticulos();
            alertas = Notificaciones.calcular(articulos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final List<Notificaciones.Alerta> alertasFinal = alertas;

        if (!alertas.isEmpty()) {
            Label badge = new Label(String.valueOf(alertas.size()));
            badge.setStyle(
                    "-fx-background-color: #dc3545; -fx-text-fill: white;" +
                            "-fx-font-size: 9px; -fx-font-weight: bold;" +
                            "-fx-background-radius: 50%; -fx-padding: 2px 5px;" +
                            "-fx-min-width: 16px; -fx-min-height: 16px;");
            badge.setAlignment(Pos.CENTER);
            StackPane.setAlignment(badge, Pos.TOP_RIGHT);
            StackPane.setMargin(badge, new Insets(-5, -5, 0, 0));
            campanaConBadge.getChildren().addAll(campana, badge);
        } else {
            campanaConBadge.getChildren().add(campana);
        }

        campana.setOnMouseClicked(e -> mostrarNotificaciones(campana, alertasFinal));
        ;

        barra.getChildren().addAll(logo, spacer, campanaConBadge);

        VBox bienvenida = new VBox(5);
        bienvenida.setAlignment(Pos.CENTER);
        bienvenida.setPadding(new Insets(20, 0, 10, 0));

        Label lblBienvenida = new Label("BIENVENIDA");
        lblBienvenida.setStyle("-fx-font-size: 11px; -fx-text-fill: #d63384; -fx-font-weight: bold;");

        Label lblPregunta = new Label("¿Qué deseas gestionar hoy?");
        lblPregunta.setFont(Font.font("System", FontWeight.BOLD, 22));
        lblPregunta.setStyle("-fx-text-fill: #212529;");

        Label lblSub = new Label("Seleccioná una opción para comenzar");
        lblSub.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");

        bienvenida.getChildren().addAll(lblBienvenida, lblPregunta, lblSub);

        TilePane tarjetas = new TilePane();
        tarjetas.setHgap(20);
        tarjetas.setVgap(20);
        tarjetas.setPrefTileWidth(240);
        tarjetas.setPrefTileHeight(150);
        tarjetas.setAlignment(Pos.CENTER);

        try {
            List<Categoria> categorias = api.listarCategorias();
            for (Categoria c : categorias) {
                tarjetas.getChildren().add(crearTarjetaCategoria(c));
            }
        } catch (Exception e) {
            Label error = new Label("Error al cargar categorías: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;");
            tarjetas.getChildren().add(error);
        }

        panel.getChildren().addAll(barra, bienvenida, tarjetas);
        return panel;
    }

    private VBox crearTarjetaCategoria(Categoria categoria) {
        VBox tarjeta = new VBox(8);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPadding(new Insets(15));
        tarjeta.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3);" +
                        "-fx-cursor: hand;");

        String emoji = EMOJIS.getOrDefault(categoria.getNombre(), "📦");
        Label lblEmoji = new Label(emoji);
        lblEmoji.setStyle("-fx-font-size: 40px;");

        Label lblNombre = new Label(categoria.getNombre());
        lblNombre.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblNombre.setStyle("-fx-text-fill: #212529;");

        Label lblDesc = new Label("Control de stock de " + categoria.getNombre().toLowerCase());
        lblDesc.setStyle("-fx-font-size: 10px; -fx-text-fill: #6c757d;");
        lblDesc.setWrapText(true);
        lblDesc.setAlignment(Pos.CENTER);
        lblDesc.setMaxWidth(200);

        tarjeta.getChildren().addAll(lblEmoji, lblNombre, lblDesc);

        tarjeta.setOnMouseEntered(e -> tarjeta.setStyle(
                "-fx-background-color: #fdf6fa;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(214,51,132,0.4), 12, 0, 0, 5);" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: #d63384; -fx-border-width: 2px; -fx-border-radius: 15px;"));
        tarjeta.setOnMouseExited(e -> tarjeta.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3);" +
                        "-fx-cursor: hand;"));

        tarjeta.setOnMouseClicked(e -> alHacerClicCategoria.accept(categoria.getIdCategoria()));

        return tarjeta;
    }

    private void mostrarNotificaciones(javafx.scene.Node ancla, List<Notificaciones.Alerta> alertas) {
        Popup popup = new Popup();
        popup.setAutoHide(true);

        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(15));
        contenido.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 5);");
        contenido.setMaxWidth(380);
        contenido.setMaxHeight(500);

        Label titulo = new Label("🔔 Notificaciones");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: #d63384;");

        contenido.getChildren().add(titulo);

        if (alertas.isEmpty()) {
            Label vacio = new Label("No tenés notificaciones pendientes ✅");
            vacio.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 12px;");
            contenido.getChildren().add(vacio);
        } else {
            ScrollPane scroll = new ScrollPane();
            VBox lista = new VBox(8);
            lista.setPadding(new Insets(5));

            for (Notificaciones.Alerta a : alertas) {
                HBox item = new HBox(10);
                item.setPadding(new Insets(8));
                item.setStyle(
                        "-fx-background-color: #f8f9fa;" +
                                "-fx-background-radius: 8px;" +
                                "-fx-border-color: " + a.color + ";" +
                                "-fx-border-width: 0 0 0 4px;" +
                                "-fx-border-radius: 8px;");

                Label icono = new Label(a.icono);
                icono.setStyle("-fx-font-size: 20px;");

                VBox textos = new VBox(2);
                Label tit = new Label(a.titulo);
                tit.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #212529;");
                Label det = new Label(a.detalle);
                det.setStyle("-fx-font-size: 10px; -fx-text-fill: #6c757d;");
                textos.getChildren().addAll(tit, det);

                item.getChildren().addAll(icono, textos);
                lista.getChildren().add(item);
            }

            scroll.setContent(lista);
            scroll.setFitToWidth(true);
            scroll.setPrefHeight(Math.min(400, alertas.size() * 60 + 20));
            scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
            contenido.getChildren().add(scroll);
        }

        popup.getContent().add(contenido);

        javafx.geometry.Bounds bounds = ancla.localToScreen(ancla.getBoundsInLocal());
        popup.show(ancla, bounds.getMaxX() - 380, bounds.getMaxY() + 10);
    }
}