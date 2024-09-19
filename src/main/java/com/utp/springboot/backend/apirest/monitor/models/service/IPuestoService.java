package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Puesto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IPuestoService {

    public List<Puesto> findAll();
    public Page<Puesto> findAll(Pageable pageable);
    public Puesto findById(Long id);
    public Optional<Puesto> findByIdOptional(Long id);
    public Puesto save(Puesto puesto);
    public void delete(Long id);

}
