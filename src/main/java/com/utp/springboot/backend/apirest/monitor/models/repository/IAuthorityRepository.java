package com.utp.springboot.backend.apirest.monitor.models.repository;

import com.utp.springboot.backend.apirest.monitor.models.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorityRepository extends JpaRepository<Authority, Long> {
}
