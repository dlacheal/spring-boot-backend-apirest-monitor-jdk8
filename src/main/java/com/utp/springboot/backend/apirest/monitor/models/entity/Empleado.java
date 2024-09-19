package com.utp.springboot.backend.apirest.monitor.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "ruta_foto")
    private String rutaFoto;

    @NotEmpty
    @Column(name = "licencia")
    private String licencia;

    @NotEmpty
    @Column(name = "tipo_sangre")
    private String tipoSangre;

    @OneToOne
    @JoinColumn(name = "puesto_id")
    private Puesto codigoPuesto;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona codigoPersona;

    //    @JsonManagedReference
    @OneToMany(mappedBy = "codigoEmpleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"codigoEmpleado", "hibernateLazyInitializer", "handler"})
    private List<Registro> registros;
}
