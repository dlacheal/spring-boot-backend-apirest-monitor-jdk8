package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Authority;
import com.utp.springboot.backend.apirest.monitor.models.entity.Proyecto;
import com.utp.springboot.backend.apirest.monitor.models.repository.IProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository("proyectoServiceJPA")
public class ProyectoServiceImpl implements IProyectoService{

    @Autowired
    private IProyectoRepository proyectoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Proyecto> findAll() {
        return (List<Proyecto>)proyectoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Proyecto> findAll(Pageable pageable) {
        return proyectoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Proyecto findById(Long id) {
        return proyectoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proyecto> findByIdOptional(Long id) {
        return proyectoRepository.findById(id);
    }

    @Override
    @Transactional
    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        proyectoRepository.deleteById(id);
    }
}
