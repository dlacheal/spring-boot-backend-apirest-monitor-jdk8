package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Notificacion;
import com.utp.springboot.backend.apirest.monitor.models.repository.INotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository("notificacionServiceJPA")
public class NotificacionService implements INotificacionService{

    @Autowired
    INotificacionRepository notificacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> findAll() {
        return (List<Notificacion>)notificacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notificacion> findAll(Pageable pageable) {
        return notificacionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Notificacion findById(Long id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notificacion> findByIdOptional(Long id) {
        return notificacionRepository.findById(id);
    }

    @Override
    @Transactional
    public Notificacion save(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        notificacionRepository.deleteById(id);
    }
}
