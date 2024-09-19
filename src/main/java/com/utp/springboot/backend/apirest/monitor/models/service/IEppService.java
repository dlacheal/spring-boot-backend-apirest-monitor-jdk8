package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Categoria;
import com.utp.springboot.backend.apirest.monitor.models.entity.Epp;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IEppService {

    public List<Epp> findAll();
    public Page<Epp> findAll(Pageable pageable);
    public Epp findById(Long id);
    public Optional<Epp> findByIdOptional(Long id);
    public Epp save(Epp epp);
    public void delete(Long id);
    public List<Categoria> findAllCategorias();

}
