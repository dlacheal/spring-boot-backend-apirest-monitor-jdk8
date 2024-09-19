package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Proyecto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IProyectoService {

    public List<Proyecto> findAll();
    public Page<Proyecto> findAll(Pageable pageable);
    public Proyecto findById(Long id);
    public Optional<Proyecto> findByIdOptional(Long id);
    public Proyecto save(Proyecto proyecto);
    public void delete(Long id);

}
