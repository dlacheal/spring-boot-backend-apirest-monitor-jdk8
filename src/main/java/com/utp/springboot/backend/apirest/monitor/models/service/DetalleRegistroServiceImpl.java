package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Authority;
import com.utp.springboot.backend.apirest.monitor.models.entity.DetalleRegistro;
import com.utp.springboot.backend.apirest.monitor.models.repository.ICategoriaRepository;
import com.utp.springboot.backend.apirest.monitor.models.repository.IDetalleRegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Repository("detalleRegistroServiceJPA")
public class DetalleRegistroServiceImpl implements IDetalleRegistroService{

    @Autowired
    private IDetalleRegistroRepository detalleRegistroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleRegistro> findAll() {
        return (List<DetalleRegistro>)detalleRegistroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetalleRegistro> findAll(Pageable pageable) {
        return detalleRegistroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleRegistro findById(Long id) {
        return detalleRegistroRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleRegistro> findByIdOptional(Long id) {
        return detalleRegistroRepository.findById(id);
    }

    @Override
    @Transactional
    public DetalleRegistro save(DetalleRegistro detalleRegistro) {
        return detalleRegistroRepository.save(detalleRegistro);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        detalleRegistroRepository.deleteById(id);
    }
}
