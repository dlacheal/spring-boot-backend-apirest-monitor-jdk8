package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Authority;
import com.utp.springboot.backend.apirest.monitor.models.repository.IAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository("authorityServiceJPA")
public class AuthorityServiceImpl implements IAuthorityService{

    @Autowired
    private IAuthorityRepository authorityRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Authority> findAll() { return (List<Authority>)authorityRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Page<Authority> findAll(Pageable pageable) { return authorityRepository.findAll(pageable); }

    @Override
    @Transactional(readOnly = true)
    public Authority findById(Long id) { return authorityRepository.findById(id).orElse(null); }

    @Override
    @Transactional(readOnly = true)
    public Optional<Authority> findByIdOptional(Long id) {
        return authorityRepository.findById(id);
    }

    @Override
    @Transactional
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    @Transactional
    public void delete(Long id) { authorityRepository.deleteById(id); }
}
