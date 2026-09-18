package com.ong.inventario_api.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donaciones_recepcion")
public class DonacionRecepcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donacion")
    private Integer idDonacion;

    @ManyToOne
    @JoinColumn(name = "id_donante")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Entidad donante;

    @ManyToOne
    @JoinColumn(name = "id_responsable_recepcion", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario responsableRecepcion;

    @Column(name = "fecha_recepcion")
    private LocalDateTime fechaRecepcion;

    private String observaciones;

    // Getters y Setters
    public Integer getIdDonacion() { return idDonacion; }
    public void setIdDonacion(Integer idDonacion) { this.idDonacion = idDonacion; }

    public Entidad getDonante() { return donante; }
    public void setDonante(Entidad donante) { this.donante = donante; }

    public Usuario getResponsableRecepcion() { return responsableRecepcion; }
    public void setResponsableRecepcion(Usuario responsableRecepcion) { this.responsableRecepcion = responsableRecepcion; }

    public LocalDateTime getFechaRecepcion() { return fechaRecepcion; }
    public void setFechaRecepcion(LocalDateTime fechaRecepcion) { this.fechaRecepcion = fechaRecepcion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}