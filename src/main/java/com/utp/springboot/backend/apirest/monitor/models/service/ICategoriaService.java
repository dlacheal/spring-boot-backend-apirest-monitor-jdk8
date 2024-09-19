package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    public List<Categoria> findAll();
    public Page<Categoria> findAll(Pageable pageable);
    public Categoria findById(Long id);
    public Optional<Categoria> findByIdOptional(Long id);
    public Categoria save(Categoria categoria);
    public void delete(Long id);

}
