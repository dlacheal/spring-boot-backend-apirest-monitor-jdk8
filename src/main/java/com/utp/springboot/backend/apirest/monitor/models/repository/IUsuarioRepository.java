package com.utp.springboot.backend.apirest.monitor.models.repository;

import com.utp.springboot.backend.apirest.monitor.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByUsername(String username);

    @Query("select u from Usuario u where u.codigoEmpleado.id = ?1")
    public Usuario findByCodigoEmpleado(String codigoEmpleado);
}
