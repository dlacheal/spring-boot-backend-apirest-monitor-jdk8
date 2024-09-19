package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Categoria;
import com.utp.springboot.backend.apirest.monitor.models.entity.Epp;
import com.utp.springboot.backend.apirest.monitor.models.repository.IEppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository("eppServiceJPA")
public class EppServiceImpl implements IEppService{

    @Autowired
    private IEppRepository eppRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Epp> findAll() {
        return (List<Epp>)eppRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Epp> findAll(Pageable pageable) {
        return eppRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Epp findById(Long id) {
        return eppRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Epp> findByIdOptional(Long id) {
        return eppRepository.findById(id);
    }


    @Override
    @Transactional
    public Epp save(Epp epp) {
        return eppRepository.save(epp);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eppRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAllCategorias() {
        return eppRepository.findAllCategorias();
    }
}
