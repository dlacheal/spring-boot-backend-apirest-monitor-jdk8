package com.utp.springboot.backend.apirest.monitor.models.repository;

import com.utp.springboot.backend.apirest.monitor.models.entity.DetalleRegistro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleRegistroRepository extends JpaRepository<DetalleRegistro, Long> {
}
