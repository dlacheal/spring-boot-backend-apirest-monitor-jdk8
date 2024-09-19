package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Registro;
import com.utp.springboot.backend.apirest.monitor.models.repository.IRegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository("registroServiceJPA")
public class RegistroServiceImpl implements IRegistroService{

    @Autowired
    private IRegistroRepository registroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Registro> findAll() {
        return (List<Registro>)registroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Long count() {
        return (Long)registroRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Registro> findAll(Pageable pageable) {
        return registroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Registro findById(Long id) {
        return registroRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Registro> findByIdOptional(Long id) {
        return registroRepository.findById(id);
    }

    @Override
    @Transactional
    public Registro save(Registro registro) {
        return registroRepository.save(registro);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        registroRepository.deleteById(id);
    }
}
