package com.utp.springboot.backend.apirest.monitor.models.repository;

import com.utp.springboot.backend.apirest.monitor.models.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRegistroRepository extends JpaRepository<Registro, Long> {
    //@Query("select r from Registro r join fetch r.codigoEmpleado e join fetch r.detalleRegistroList l join fetch l.codigoEpp  where r.id=?1")
    @Query("select r from Registro r join fetch r.codigoEmpleado where r.id=?1")
    Registro fetchByIdEmpleadoWithItemWithEpp(Long id);
}
