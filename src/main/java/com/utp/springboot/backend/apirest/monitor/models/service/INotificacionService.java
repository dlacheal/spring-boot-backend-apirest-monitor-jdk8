package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Notificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface INotificacionService {

    public List<Notificacion> findAll();
    public Page<Notificacion> findAll(Pageable pageable);
    public Notificacion findById(Long id);
    public Optional<Notificacion> findByIdOptional(Long id);
    public Notificacion save(Notificacion notificacion);
    public void delete(Long id);
}
