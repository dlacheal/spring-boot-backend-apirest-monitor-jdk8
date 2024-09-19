package com.utp.springboot.backend.apirest.monitor.models.entity;

//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, message = "El número de carácteres mínimo debe ser 4")
    @Column(name = "nombres")
    private String nombres;

    @NotEmpty
    @Size(min = 4)
    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @NotEmpty
    @Size(min = 4, message = "El número de carácteres mínimo debe ser 8")
    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;
}
