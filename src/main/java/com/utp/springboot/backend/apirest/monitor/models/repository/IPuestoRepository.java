package com.utp.springboot.backend.apirest.monitor.models.repository;

import com.utp.springboot.backend.apirest.monitor.models.entity.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPuestoRepository extends JpaRepository<Puesto, Long> {
}
