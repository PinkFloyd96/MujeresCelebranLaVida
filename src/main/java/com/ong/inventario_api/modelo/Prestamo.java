package com.ong.inventario_api.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    @ManyToOne
    @JoinColumn(name = "id_receptor", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Entidad receptor;

    @ManyToOne
    @JoinColumn(name = "id_articulo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Articulo articulo;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_responsable", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario responsable;

    @Column(name = "fecha_prestamo")
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_prevista_devolucion", nullable = false)
    private LocalDate fechaPrevistaDevolucion;

    @Column(name = "fecha_real_devolucion")
    private LocalDateTime fechaRealDevolucion;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado_devolucion", columnDefinition = "estado_conservacion")
    private EstadoConservacion estadoDevolucion;

    @ManyToOne
    @JoinColumn(name = "id_responsable_recepcion_devolucion")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario responsableRecepcionDevolucion;

    private String observaciones;

    // Getters y Setters
    public Integer getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Integer idPrestamo) { this.idPrestamo = idPrestamo; }

    public Entidad getReceptor() { return receptor; }
    public void setReceptor(Entidad receptor) { this.receptor = receptor; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Usuario getResponsable() { return responsable; }
    public void setResponsable(Usuario responsable) { this.responsable = responsable; }

    public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDateTime fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaPrevistaDevolucion() { return fechaPrevistaDevolucion; }
    public void setFechaPrevistaDevolucion(LocalDate fechaPrevistaDevolucion) { this.fechaPrevistaDevolucion = fechaPrevistaDevolucion; }

    public LocalDateTime getFechaRealDevolucion() { return fechaRealDevolucion; }
    public void setFechaRealDevolucion(LocalDateTime fechaRealDevolucion) { this.fechaRealDevolucion = fechaRealDevolucion; }

    public EstadoConservacion getEstadoDevolucion() { return estadoDevolucion; }
    public void setEstadoDevolucion(EstadoConservacion estadoDevolucion) { this.estadoDevolucion = estadoDevolucion; }

    public Usuario getResponsableRecepcionDevolucion() { return responsableRecepcionDevolucion; }
    public void setResponsableRecepcionDevolucion(Usuario responsableRecepcionDevolucion) { this.responsableRecepcionDevolucion = responsableRecepcionDevolucion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}