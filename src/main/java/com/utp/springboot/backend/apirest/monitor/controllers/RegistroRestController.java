package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.Empleado;
import com.utp.springboot.backend.apirest.monitor.models.entity.Registro;
import com.utp.springboot.backend.apirest.monitor.models.service.IEmpleadoService;
import com.utp.springboot.backend.apirest.monitor.models.service.IRegistroService;
//import jakarta.validation.Valid;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RegistroRestController {

    @Autowired
    @Qualifier("registroServiceJPA")
    private IRegistroService registroService;

    @Autowired
    @Qualifier("empleadoServiceJPA")
    private IEmpleadoService empleadoService;

    @GetMapping("/registros")
    public List<Registro> index(){

        return registroService.findAll();
    }

    @GetMapping("/registros/count")
    public Long countRegistros(){

        return registroService.count();
    }

    @GetMapping("/registros/page/{page}")
    public Page<Registro> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return registroService.findAll(pageable);
    }

    @GetMapping("/registros/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Registro registro = null;
        Empleado empleado = null;
        Map<String, Object> response = new HashMap<>();

        try {
            registro = empleadoService.fetchRegistroByIdWithEmpleadoWithItemWithEpp(id);
            if(registro != null){
                empleado =  registro.getCodigoEmpleado();

            }
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (registro == null){
            response.put("mensaje", "El registro ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Registro>(registro, HttpStatus.OK);
    }

    @PostMapping("/registros")
    public ResponseEntity<?> create(@Valid @RequestBody Registro registro,
                                    BindingResult result){

        Registro registroNuevo = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            /*
            List<String> errors = new ArrayList<>();
            for(FieldError error: result.getFieldErrors()){
                errors.add("El campo '" + error.getField() + "' " + error.getDefaultMessage());

            }
            */
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            registroNuevo = registroService.save(registro);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El registro ha sido creado con éxito!");
        response.put("registro", registroNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/registros/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Registro registro,
                                    BindingResult result,
                                    @PathVariable Long id){

        Registro registroActual = registroService.findById(id);
        Registro registroUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            /*
            List<String> errors = new ArrayList<>();
            for(FieldError error: result.getFieldErrors()){
                errors.add("El campo '" + error.getField() + "' " + error.getDefaultMessage());

            }
            */
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (registroActual == null){
            response.put("mensaje", "Error: No se pudo editar, el registro ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

//            registroActual.setId(registro.getId());
            registroActual.setRevisado(registro.getRevisado());
            registroActual.setEntregado(registro.getEntregado());
            registroActual.setAprobado(registro.getAprobado());
            registroActual.setPlantaOperario(registro.getPlantaOperario());
            registroActual.setNombreProyectoOperario(registro.getNombreProyectoOperario());

            registroUpdated = registroService.save(registroActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar el registro en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "El registro ha sido actualizado con éxito!");
        response.put("registro", registroUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/registros/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {

            Optional<Registro> optionalRegistro = registroService.findByIdOptional(id);
            if (optionalRegistro.isPresent()){
                registroService.delete(id);
                response.put("mensaje", "El registro ha sido eliminado con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, el registro ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);


        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el registro en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
