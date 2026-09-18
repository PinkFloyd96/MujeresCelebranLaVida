package com.ong.inventario_api.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fotografias_articulo")
public class FotografiaArticulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fotografia")
    private Integer idFotografia;

    @ManyToOne
    @JoinColumn(name = "id_articulo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Articulo articulo;

    @Column(name = "url_fotografia", nullable = false)
    private String urlFotografia;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    @Column(name = "subido_en")
    private LocalDateTime subidoEn;

    // Getters y Setters
    public Integer getIdFotografia() { return idFotografia; }
    public void setIdFotografia(Integer idFotografia) { this.idFotografia = idFotografia; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public String getUrlFotografia() { return urlFotografia; }
    public void setUrlFotografia(String urlFotografia) { this.urlFotografia = urlFotografia; }

    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }

    public LocalDateTime getSubidoEn() { return subidoEn; }
    public void setSubidoEn(LocalDateTime subidoEn) { this.subidoEn = subidoEn; }
}