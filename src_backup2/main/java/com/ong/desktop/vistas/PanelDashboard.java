package com.ong.desktop.vistas;

import com.ong.desktop.modelos.Articulo;
import com.ong.desktop.modelos.DonacionRecepcion;
import com.ong.desktop.modelos.EntregaDefinitiva;
import com.ong.desktop.modelos.Prestamo;
import com.ong.desktop.servicios.ApiServicio;
import javafx.geometry.Insets; 
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class PanelDashboard {

    private final ApiServicio api = new ApiServicio();
    private final TilePane contenedorTarjetas = new TilePane();

    public VBox construir() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(25));
        panel.setStyle("-fx-background-color: #f8f9fa;");

        Label titulo = new Label("Panel General");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 26));
        titulo.setStyle("-fx-text-fill: #d63384;");

        Label subtitulo = new Label("Resumen del estado actual del inventario");
        subtitulo.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 13px;");

        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setStyle("-fx-background-color: #d63384; -fx-text-fill: white; -fx-padding: 8px 20px; -fx-font-size: 13px; -fx-cursor: hand;");
        btnActualizar.setOnAction(e -> cargar());

        HBox header = new HBox(15, new VBox(5, titulo, subtitulo), new Region(), btnActualizar);
        HBox.setHgrow(header.getChildren().get(1), Priority.ALWAYS);
        header.setAlignment(Pos.CENTER_LEFT);

        contenedorTarjetas.setHgap(15);
        contenedorTarjetas.setVgap(15);
        contenedorTarjetas.setPrefTileWidth(220);
        contenedorTarjetas.setPrefTileHeight(120);

        panel.getChildren().addAll(header, contenedorTarjetas);

        cargar();
        return panel;
    }

    private void cargar() {
        contenedorTarjetas.getChildren().clear();
        try {
            List<Articulo> articulos = api.listarArticulos();
            List<DonacionRecepcion> donaciones = api.listarDonaciones();
            List<EntregaDefinitiva> entregas = api.listarEntregas();
            List<Prestamo> prestamos = api.listarPrestamos();

            long total = articulos.size();
            long disponibles = contarPorEstado(articulos, "DISPONIBLE");
            long reservados = contarPorEstado(articulos, "RESERVADO");
            long prestados = contarPorEstado(articulos, "PRESTADO");
            long enReparacion = contarPorEstado(articulos, "EN_REPARACION");
            long stockMinimo = articulos.stream()
                    .filter(a -> a.getStockMinimo() != null && a.getCantidad() != null
                            && a.getCantidad() <= a.getStockMinimo())
                    .count();

            contenedorTarjetas.getChildren().addAll(
                    crearTarjeta("TOTAL ARTÍCULOS", total, "#6c757d"),
                    crearTarjeta("DISPONIBLES", disponibles, "#28a745"),
                    crearTarjeta("RESERVADOS", reservados, "#ffc107"),
                    crearTarjeta("PRESTADOS", prestados, "#17a2b8"),
                    crearTarjeta("EN REPARACIÓN", enReparacion, "#dc3545"),
                    crearTarjeta("⚠ STOCK MÍNIMO", stockMinimo, stockMinimo > 0 ? "#dc3545" : "#28a745"),
                    crearTarjeta("DONACIONES", donaciones.size(), "#d63384"),
                    crearTarjeta("ENTREGAS", entregas.size(), "#6f42c1"),
                    crearTarjeta("PRÉSTAMOS", prestamos.size(), "#20c997")
            );

        } catch (Exception e) {
            e.printStackTrace();
            Label error = new Label("Error al cargar el dashboard:\n" + e.getMessage());
            error.setStyle("-fx-text-fill: red;");
            contenedorTarjetas.getChildren().add(error);
        }
    }

    private long contarPorEstado(List<Articulo> articulos, String estado) {
        return articulos.stream()
                .filter(a -> estado.equals(a.getEstadoActual()))
                .count();
    }

    private VBox crearTarjeta(String titulo, long valor, String colorHex) {
        VBox tarjeta = new VBox(10);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPadding(new Insets(20));
        tarjeta.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10px;" +
                "-fx-border-color: " + colorHex + ";" +
                "-fx-border-width: 0 0 0 5px;" +
                "-fx-border-radius: 10px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 5, 0, 0, 2);"
        );

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 11px; -fx-text-fill: #6c757d; -fx-font-weight: bold;");

        Label lblValor = new Label(String.valueOf(valor));
        lblValor.setFont(Font.font("System", FontWeight.BOLD, 38));
        lblValor.setTextFill(Color.web(colorHex));

        tarjeta.getChildren().addAll(lblTitulo, lblValor);
        return tarjeta;
    }
}
