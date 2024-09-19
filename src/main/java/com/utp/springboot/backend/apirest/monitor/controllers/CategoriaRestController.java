package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.Categoria;
import com.utp.springboot.backend.apirest.monitor.models.service.ICategoriaService;
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
public class CategoriaRestController {

    @Autowired
    @Qualifier("categoriaServiceJPA")
    private ICategoriaService categoriaService;


    @GetMapping("/categorias")
    public List<Categoria> index(){

        return categoriaService.findAll();
    }

    @GetMapping("/categorias/page/{page}")
    public Page<Categoria> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return categoriaService.findAll(pageable);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Categoria categoria = null;
        Map<String, Object> response = new HashMap<>();

        try {
            categoria = categoriaService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (categoria == null){
            response.put("mensaje", "La categoria ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
    }

    @PostMapping("/categorias")
    public ResponseEntity<?> create(@Valid @RequestBody Categoria categoria,
                                    BindingResult result){

        Categoria categoriaNuevo = null;
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
            categoriaNuevo = categoriaService.save(categoria);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La categoria ha sido creada con éxito!");
        response.put("categoria", categoriaNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria,
                                    BindingResult result,
                                    @PathVariable Long id){

        Categoria categoriaActual = categoriaService.findById(id);
        Categoria categoriaUpdated = null;

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

        if (categoriaActual == null){
            response.put("mensaje", "Error: No se pudo editar, el categoria ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            //categoriaActual.setId(categoria.getId());
            categoriaActual.setDescripcion(categoria.getDescripcion());

            categoriaUpdated = categoriaService.save(categoriaActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar la categoria en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "La categoria ha sido actualizado con éxito!");
        response.put("categoria", categoriaUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {

            Optional<Categoria> optionalCategoria = categoriaService.findByIdOptional(id);
            if (optionalCategoria.isPresent()){
                categoriaService.delete(id);
                response.put("mensaje", "La categoria ha sido eliminada con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, la categoria ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);


        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el categoria en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
