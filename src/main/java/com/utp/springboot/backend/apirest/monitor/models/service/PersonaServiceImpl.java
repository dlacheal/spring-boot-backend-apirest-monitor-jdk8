package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Authority;
import com.utp.springboot.backend.apirest.monitor.models.entity.Persona;
import com.utp.springboot.backend.apirest.monitor.models.repository.IPersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository("personaServiceJPA")
public class PersonaServiceImpl implements IPersonaService{

    @Autowired
    private IPersonaRepository personaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> findAll() {
        return (List<Persona>)personaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Persona> findAll(Pageable pageable) {
        return personaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona findById(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findByIdOptional(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    @Transactional
    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personaRepository.deleteById(id);
    }
}
