package com.utp.springboot.backend.apirest.monitor.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "detalle_registro")
public class DetalleRegistro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "nombre_epp")
    private String nombreEpp;

    @Column(name = "talla_epp")
    private String tallaEpp;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

    @Column(name = "conformidad")
    private Boolean conformidad;

    @Column(name = "motivo_entrega")
    private String motivoEntrega;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epp_id")
    private Epp codigoEpp;

//    @JoinColumn(name = "registro_id")
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    private Registro codigoRegistro;

}
