package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.Persona;
import com.utp.springboot.backend.apirest.monitor.models.service.IPersonaService;
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
public class PersonaRestController {

    @Autowired
    @Qualifier("personaServiceJPA")
    private IPersonaService personaService;


    @GetMapping("/personas")
    public List<Persona> index(){

        return personaService.findAll();
    }

    @GetMapping("/personas/page/{page}")
    public Page<Persona> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return personaService.findAll(pageable);
    }

    @GetMapping("/personas/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Persona persona = null;
        Map<String, Object> response = new HashMap<>();

        try {
            persona = personaService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (persona == null){
            response.put("mensaje", "La persona ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Persona>(persona, HttpStatus.OK);
    }

    @PostMapping("/personas")
    public ResponseEntity<?> create(@Valid @RequestBody Persona persona,
                                    BindingResult result){

        Persona personaNuevo = null;
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
            personaNuevo = personaService.save(persona);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "la persona ha sido creada con éxito!");
        response.put("persona", personaNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/personas/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Persona persona,
                                    BindingResult result,
                                    @PathVariable Long id){

        Persona personaActual = personaService.findById(id);
        Persona personaUpdated = null;

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

        if (personaActual == null){
            response.put("mensaje", "Error: No se pudo editar, el persona ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

//            personaActual.setId(persona.getId());
            personaActual.setNombres(persona.getNombres());
            personaActual.setApellidos(persona.getApellidos());
            personaActual.setTipoDocumento(persona.getTipoDocumento());
            personaActual.setNumeroDocumento(persona.getNumeroDocumento());
            personaActual.setTelefono(persona.getTelefono());
            personaActual.setDireccion(persona.getDireccion());
            personaActual.setEmail(persona.getEmail());

            personaUpdated = personaService.save(personaActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar la persona en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "La persona ha sido actualizado con éxito!");
        response.put("persona", personaUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/personas/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Persona> optionalPersona= personaService.findByIdOptional(id);
            if (optionalPersona.isPresent()){
                personaService.delete(id);
                response.put("mensaje", "La Persona ha sido eliminada con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, la persona ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);


        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el persona en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
