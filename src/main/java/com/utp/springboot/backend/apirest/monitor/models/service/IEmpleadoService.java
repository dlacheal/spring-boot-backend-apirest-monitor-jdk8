package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Empleado;
import com.utp.springboot.backend.apirest.monitor.models.entity.Registro;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IEmpleadoService {

    public List<Empleado> findAll();
    public Page<Empleado> findAll(Pageable pageable);
    public Empleado findById(Long id);
    public Optional<Empleado> findByIdOptional(Long id);
    public Empleado save(Empleado empleado);
    public void delete(Long id);
    public Registro fetchRegistroByIdWithEmpleadoWithItemWithEpp(Long id);
    public Registro findRegistroById(Long id);
    public Registro saveRegistro(Registro registro);
    void deleteRegistroById(Long id);

}
