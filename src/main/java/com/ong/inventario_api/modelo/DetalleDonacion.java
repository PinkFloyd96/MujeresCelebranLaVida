package com.ong.inventario_api.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "detalle_donacion")
public class DetalleDonacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_donacion")
    private Integer idDetalleDonacion;

    @ManyToOne
    @JoinColumn(name = "id_donacion", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DonacionRecepcion donacion;

    @ManyToOne
    @JoinColumn(name = "id_articulo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Articulo articulo;

    @Column(nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado_conservacion_inicial", columnDefinition = "estado_conservacion")
    private EstadoConservacion estadoConservacionInicial;

    // Getters y Setters
    public Integer getIdDetalleDonacion() { return idDetalleDonacion; }
    public void setIdDetalleDonacion(Integer idDetalleDonacion) { this.idDetalleDonacion = idDetalleDonacion; }

    public DonacionRecepcion getDonacion() { return donacion; }
    public void setDonacion(DonacionRecepcion donacion) { this.donacion = donacion; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public EstadoConservacion getEstadoConservacionInicial() { return estadoConservacionInicial; }
    public void setEstadoConservacionInicial(EstadoConservacion estadoConservacionInicial) { this.estadoConservacionInicial = estadoConservacionInicial; }
}