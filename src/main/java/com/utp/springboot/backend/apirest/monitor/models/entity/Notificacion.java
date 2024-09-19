package com.utp.springboot.backend.apirest.monitor.models.entity;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notificacion")
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ruta_fotograma")
    private String rutaFotograma;

    @Column(name = "fecha_notificacion")
    private String fechaNotificacion;

    @Column(name = "revisado")
    private boolean revisado;

    @Column(name = "enviado")
    private boolean enviado;

    @Column(name = "criticidad")
    private int criticidad;

    @Column(name = "descripcion")
    private String descripcion;

}
