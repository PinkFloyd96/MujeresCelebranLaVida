package com.ong.inventario_api.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_movimientos")
public class HistorialMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Integer idMovimiento;

    @ManyToOne
    @JoinColumn(name = "id_articulo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @Column(name = "tipo_operacion", nullable = false)
    private String tipoOperacion;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado_anterior", columnDefinition = "estado_articulo")
    private EstadoArticulo estadoAnterior;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado_nuevo", columnDefinition = "estado_articulo")
    private EstadoArticulo estadoNuevo;

    @Column(name = "descripcion_movimiento")
    private String descripcionMovimiento;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Getters y Setters
    public Integer getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Integer idMovimiento) { this.idMovimiento = idMovimiento; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTipoOperacion() { return tipoOperacion; }
    public void setTipoOperacion(String tipoOperacion) { this.tipoOperacion = tipoOperacion; }

    public EstadoArticulo getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(EstadoArticulo estadoAnterior) { this.estadoAnterior = estadoAnterior; }

    public EstadoArticulo getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(EstadoArticulo estadoNuevo) { this.estadoNuevo = estadoNuevo; }

    public String getDescripcionMovimiento() { return descripcionMovimiento; }
    public void setDescripcionMovimiento(String descripcionMovimiento) { this.descripcionMovimiento = descripcionMovimiento; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}