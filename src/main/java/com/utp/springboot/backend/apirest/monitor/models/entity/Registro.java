package com.utp.springboot.backend.apirest.monitor.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "registro")
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "numero_registro")
    private String numeroRegistro;

    @NotEmpty
    @Column(name = "formato_registro")
    private String formatoRegistro;

    @NotNull
    @Column(name = "numero_trabajadores")
    private int numeroTrabajadores;

    @Column(name = "revisado")
    private Boolean revisado;

    @Column(name = "aprobado")
    private Boolean aprobado;

    @Column(name = "entregado")
    private Boolean entregado;

    @NotEmpty
    @Column(name = "nombre_proyecto_operario")
    private String nombreProyectoOperario;

    @NotEmpty
    @Column(name = "planta_operario")
    private String plantaOperario;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    @JsonIgnoreProperties({"registros", "hibernateLazyInitializer", "handler"})
    private Empleado codigoEmpleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    @JsonIgnoreProperties({"registros", "hibernateLazyInitializer", "handler"})
    private Proyecto codigoProyecto;

    /* CascadeType.ALL */
    /* Cada vez que se agrega una una registro primero creas el registro y luego agrega de cada linea del detalle registro*/
    /* Cada vez que se elimina una una registro primero cada linea del detalle registro y luego la cotizacion*/
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "codigoRegistro")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "registro_id")
    private List<DetalleRegistro> detalleRegistroList;

    public Registro() {
        this.detalleRegistroList = new ArrayList<>();
    }
    @PrePersist
    public void prePersist(){
        this.fechaRegistro = new Date();

        this.validarCheckBox();
    }

    private void validarCheckBox() {
        if(this.revisado == null || this.revisado == false){
            setRevisado(false);
        }

        if(this.aprobado == null || this.aprobado == false){
            setAprobado(false);
        }

        if(this.entregado == null || this.entregado == false){
            setEntregado(false);
        }
    }
}

