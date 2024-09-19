package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.Authority;
import com.utp.springboot.backend.apirest.monitor.models.entity.Usuario;
import com.utp.springboot.backend.apirest.monitor.models.service.IUsuarioService;
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

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {

    @Autowired
    @Qualifier("usuarioServiceJPA")
    private IUsuarioService usuarioService;


    @GetMapping("/usuarios")
    public List<Usuario> index(){

        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/page/{page}")
    public Page<Usuario> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return usuarioService.findAll(pageable);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();

        try {
            usuario = usuarioService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (usuario == null){
            response.put("mensaje", "El usuario ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario,
                                    BindingResult result){

        Usuario usuarioNuevo = null;
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
            Authority authority = new Authority();
            authority.setTipo("ROLE_USER");
            List<Authority> listaAuthorities = new ArrayList<>();
            listaAuthorities.add(authority);
            usuario.setAuthorities(listaAuthorities);

            usuarioNuevo = usuarioService.save(usuario);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido creado con éxito!");
        response.put("usuario", usuarioNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario,
                                    BindingResult result,
                                    @PathVariable Long id){

        Usuario usuarioActual = usuarioService.findById(id);
        Usuario usuarioUpdated = null;

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

        if (usuarioActual == null){
            response.put("mensaje", "Error: No se pudo editar, el usuario ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

//            usuarioActual.setId(usuario.getId());
            usuarioActual.setUsername(usuario.getUsername());
            usuarioActual.setPassword(usuario.getPassword());
            usuarioActual.setEnable(usuario.getEnable());
            usuarioActual.setAuthorities(usuario.getAuthorities());

            usuarioUpdated = usuarioService.save(usuarioActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar el usuario en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "El usuario ha sido actualizado con éxito!");
        response.put("usuario", usuarioUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {

            Optional<Usuario> optionalUsuario = usuarioService.findByIdOptional(id);
            if (optionalUsuario.isPresent()){
                usuarioService.delete(id);
                response.put("mensaje", "El usuario ha sido eliminado con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, el usuario ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);


        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el usuario en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
