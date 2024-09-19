package com.utp.springboot.backend.apirest.monitor.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "proyecto")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, message = "El número de carácteres mínimo debe ser 4")
    @Column(name = "nombre_proyecto")
    private String nombreProyecto;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "tiempo_estimado")
    private int tiempoEstimado;

    @Column(name = "tiempo_real")
    private int tiempoReal;

    @Column(name = "ubicacion")
    private String ubicacion;

    //    @JsonManagedReference
    @OneToMany(mappedBy = "codigoProyecto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"codigoProyecto", "hibernateLazyInitializer", "handler"})
    private List<Registro> registros;
}
