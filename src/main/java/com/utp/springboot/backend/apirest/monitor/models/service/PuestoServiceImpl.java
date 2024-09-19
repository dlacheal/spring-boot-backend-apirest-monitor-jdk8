package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Authority;
import com.utp.springboot.backend.apirest.monitor.models.entity.Puesto;
import com.utp.springboot.backend.apirest.monitor.models.repository.IPuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository("puestoServiceJPA")
public class PuestoServiceImpl implements IPuestoService{

    @Autowired
    private IPuestoRepository puestoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Puesto> findAll() {
        return (List<Puesto>)puestoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Puesto> findAll(Pageable pageable) {
        return puestoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Puesto findById(Long id) {
        return puestoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Puesto> findByIdOptional(Long id) {
        return puestoRepository.findById(id);
    }

    @Override
    @Transactional
    public Puesto save(Puesto puesto) {
        return puestoRepository.save(puesto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        puestoRepository.deleteById(id);
    }
}
