package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Registro;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IRegistroService {

    public List<Registro> findAll();

    public Long count();
    public Page<Registro> findAll(Pageable pageable);
    public Registro findById(Long id);
    public Optional<Registro> findByIdOptional(Long id);
    public Registro save(Registro registro);
    public void delete(Long id);

}
