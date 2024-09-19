package com.utp.springboot.backend.apirest.monitor.models.entity;

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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, message = "El número de carácteres mínimo debe ser 4")
    @Column(name = "username")
    private String username;

    @NotEmpty
    @Size(min = 4, message = "El número de carácteres mínimo debe ser 4")
    @Column(name = "password")
    private String password;

    @Column(name = "enable")
    private Boolean enable;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private List<Authority> authorities;

    @OneToOne
    @JoinColumn(name = "empleado_id")
    private Empleado codigoEmpleado;

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public boolean isEnable() {
        return enable;
    }
}
