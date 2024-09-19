package com.utp.springboot.backend.apirest.monitor.models.service;

import com.utp.springboot.backend.apirest.monitor.models.entity.Empleado;
import com.utp.springboot.backend.apirest.monitor.models.entity.Registro;
import com.utp.springboot.backend.apirest.monitor.models.repository.IEmpleadoRepository;
import com.utp.springboot.backend.apirest.monitor.models.repository.IRegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository("empleadoServiceJPA")
public class EmpleadoServiceImpl implements IEmpleadoService{

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IRegistroRepository registroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        return (List<Empleado>)empleadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Empleado> findAll(Pageable pageable) {
        return empleadoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Empleado findById(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> findByIdOptional(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Registro fetchRegistroByIdWithEmpleadoWithItemWithEpp(Long id) {
        return registroRepository.fetchByIdEmpleadoWithItemWithEpp(id);
    }

    @Override
    @Transactional
    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        empleadoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Registro findRegistroById(Long id) {
        return registroRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Registro saveRegistro(Registro registro) {
        return registroRepository.save(registro);
    }

    @Override
    @Transactional
    public void deleteRegistroById(Long id) {
        registroRepository.deleteById(id);
    }

}
