package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.DetalleRegistro;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IDetalleRegistroService {

    public List<DetalleRegistro> findAll();
    public Page<DetalleRegistro> findAll(Pageable pageable);
    public DetalleRegistro findById(Long id);
    public Optional<DetalleRegistro> findByIdOptional(Long id);
    public DetalleRegistro save(DetalleRegistro detalleRegistro);
    public void delete(Long id);

}
