package com.utp.springboot.backend.apirest.monitor.models.repository;

import com.utp.springboot.backend.apirest.monitor.models.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IProyectoRepository extends JpaRepository<Proyecto, Long> {
}
