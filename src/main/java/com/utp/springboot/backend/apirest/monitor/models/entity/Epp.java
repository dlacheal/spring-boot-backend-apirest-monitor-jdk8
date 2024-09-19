package com.utp.springboot.backend.apirest.monitor.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "epp")
public class Epp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, message = "El número de carácteres mínimo debe ser 4")
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Column(name = "stock_actual")
    private int stockActual;

    @Column(name = "talla")
    private String talla;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "ruta_imagen")
    private String rutaImagen;

    //@NotNull(message = " no puede ser vacio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria codigoCategoria;
}
