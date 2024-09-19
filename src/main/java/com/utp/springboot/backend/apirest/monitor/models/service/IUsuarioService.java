package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Usuario;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    public List<Usuario> findAll();
    public Page<Usuario> findAll(Pageable pageable);
    public Usuario findById(Long id);
    public Optional<Usuario> findByIdOptional(Long id);
    public Usuario save(Usuario usuario);
    public void delete(Long id);
    public Usuario findByUsername(String username);

}
