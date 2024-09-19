package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.Proyecto;
import com.utp.springboot.backend.apirest.monitor.models.service.IProyectoService;
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
public class ProyectoRestController {

    @Autowired
    @Qualifier("proyectoServiceJPA")
    private IProyectoService proyectoService;


    @GetMapping("/proyectos")
    public List<Proyecto> index(){

        return proyectoService.findAll();
    }

    @GetMapping("/proyectos/page/{page}")
    public Page<Proyecto> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return proyectoService.findAll(pageable);
    }

    @GetMapping("/proyectos/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Proyecto proyecto = null;
        Map<String, Object> response = new HashMap<>();

        try {
            proyecto = proyectoService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (proyecto == null){
            response.put("mensaje", "El proyecto ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Proyecto>(proyecto, HttpStatus.OK);
    }

    @PostMapping("/proyectos")
    public ResponseEntity<?> create(@Valid @RequestBody Proyecto proyecto,
                                    BindingResult result){

        Proyecto proyectoNuevo = null;
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
            proyectoNuevo = proyectoService.save(proyecto);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El proyecto ha sido creado con éxito!");
        response.put("proyecto", proyectoNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/proyectos/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Proyecto proyecto,
                                    BindingResult result,
                                    @PathVariable Long id){

        Proyecto proyectoActual = proyectoService.findById(id);
        Proyecto proyectoUpdated = null;

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

        if (proyectoActual == null){
            response.put("mensaje", "Error: No se pudo editar, el proyecto ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

//            proyectoActual.setId(proyecto.getId());
            proyectoActual.setNombreProyecto(proyecto.getNombreProyecto());
            proyectoActual.setFechaInicio(proyecto.getFechaInicio());
            proyectoActual.setUbicacion(proyecto.getUbicacion());
            proyectoActual.setTiempoEstimado(proyecto.getTiempoEstimado());
            proyectoActual.setTiempoReal(proyecto.getTiempoReal());

            proyectoUpdated = proyectoService.save(proyectoActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar el proyecto en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "El uthority ha sido actualizado con éxito!");
        response.put("proyecto", proyectoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/proyectos/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Proyecto> optionalProyecto = proyectoService.findByIdOptional(id);
            if (optionalProyecto.isPresent()){
                proyectoService.delete(id);
                response.put("mensaje", "El proyecto ha sido eliminado con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, el proyecto ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);


        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el proyecto en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
