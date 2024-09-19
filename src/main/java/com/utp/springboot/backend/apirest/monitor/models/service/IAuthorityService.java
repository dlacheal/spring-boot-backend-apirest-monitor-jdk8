package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAuthorityService {

    public List<Authority> findAll();
    public Page<Authority> findAll(Pageable pageable);
    public Authority findById(Long id);
    public Optional<Authority> findByIdOptional(Long id);
    public Authority save(Authority authority);
    public void delete(Long id);

}
