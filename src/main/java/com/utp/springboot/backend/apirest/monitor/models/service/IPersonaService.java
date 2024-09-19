package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Persona;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IPersonaService {

    public List<Persona> findAll();
    public Page<Persona> findAll(Pageable pageable);
    public Persona findById(Long id);
    public Optional<Persona> findByIdOptional(Long id);
    public Persona save(Persona persona);
    public void delete(Long id);

}
