package com.utp.springboot.backend.apirest.monitor.models.repository;

import com.utp.springboot.backend.apirest.monitor.models.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificacionRepository extends JpaRepository<Notificacion, Long> {
}
