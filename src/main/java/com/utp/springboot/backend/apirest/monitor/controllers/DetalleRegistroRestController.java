package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.DetalleRegistro;
import com.utp.springboot.backend.apirest.monitor.models.service.IDetalleRegistroService;
//import jakarta.validation.Valid;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DetalleRegistroRestController {

    @Autowired
    @Qualifier("detalleRegistroServiceJPA")
    private IDetalleRegistroService detalleRegistroService;


    @GetMapping("/detalleregistros")
    public List<DetalleRegistro> index(){

        return detalleRegistroService.findAll();
    }

    @GetMapping("/detalleregistros/page/{page}")
    public Page<DetalleRegistro> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return detalleRegistroService.findAll(pageable);
    }

    @GetMapping("/detalleregistros/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        DetalleRegistro detalleRegistro = null;
        Map<String, Object> response = new HashMap<>();

        try {
            detalleRegistro = detalleRegistroService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (detalleRegistro == null){
            response.put("mensaje", "El detalleRegistro ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<DetalleRegistro>(detalleRegistro, HttpStatus.OK);
    }

    @PostMapping("/detalleregistros")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleRegistro detalleRegistro,
                                    BindingResult result){

        DetalleRegistro detalleRegistroNuevo = null;
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
            detalleRegistroNuevo = detalleRegistroService.save(detalleRegistro);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El detalleRegistro ha sido creado con éxito!");
        response.put("detalleRegistro", detalleRegistroNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/detalleregistros/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DetalleRegistro detalleRegistro,
                                    BindingResult result,
                                    @PathVariable Long id){

        DetalleRegistro detalleRegistroActual = detalleRegistroService.findById(id);
        DetalleRegistro detalleRegistroUpdated = null;

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

        if (detalleRegistroActual == null){
            response.put("mensaje", "Error: No se pudo editar, el detalleRegistro ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

//            detalleRegistroActual.setId(detalleRegistro.getId());
//            detalleRegistroActual.setTipo(detalleRegistro.getTipo());

            detalleRegistroUpdated = detalleRegistroService.save(detalleRegistroActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar el detalleRegistro en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "El detalleRegistro ha sido actualizado con éxito!");
        response.put("detalleRegistro", detalleRegistroUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/detalleregistros/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {

            Optional<DetalleRegistro> optionalDetalleRegistro = detalleRegistroService.findByIdOptional(id);
            if (optionalDetalleRegistro.isPresent()){
                detalleRegistroService.delete(id);
                response.put("mensaje", "El detalleRegistro ha sido eliminado con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, el detalleRegistro ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);


        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el detalleRegistro en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
