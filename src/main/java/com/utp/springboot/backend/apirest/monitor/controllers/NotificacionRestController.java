package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.Notificacion;
import com.utp.springboot.backend.apirest.monitor.models.service.INotificacionService;
//import jakarta.validation.Valid;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api")
public class NotificacionRestController {

    @Autowired
    @Qualifier("notificacionServiceJPA")
    private INotificacionService notificacionService;

    @Value("${spring.path.img.upload.notificacion}")
    private String uploadNotificaciones;

    @GetMapping("/notificaciones")
    public List<Notificacion> index(){

        return notificacionService.findAll();
    }

    @GetMapping("/notificaciones/page/{page}")
    public Page<Notificacion> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return notificacionService.findAll(pageable);
    }

    @GetMapping("/notificaciones/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Notificacion notificacion = null;
        Map<String, Object> response = new HashMap<>();

        try {
            notificacion = notificacionService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (notificacion == null){
            response.put("mensaje", "La notificación ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Notificacion>(notificacion, HttpStatus.OK);
    }

    @PostMapping("/notificaciones")
    public ResponseEntity<?> create(@Valid @RequestBody Notificacion notificacion,
                                    BindingResult result){

        Notificacion notificacionNuevo = null;
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
            notificacionNuevo = notificacionService.save(notificacion);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La Notificación ha sido creada con éxito!");
        response.put("notificacion", notificacionNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/notificaciones/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Notificacion notificacion,
                                    BindingResult result,
                                    @PathVariable Long id){

        Notificacion notificacionActual = notificacionService.findById(id);
        Notificacion notificacionUpdated = null;

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

        if (notificacionActual == null){
            response.put("mensaje", "Error: No se pudo editar, la notificación ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

//            notificacionActual.setId(notificacion.getId());
            notificacionActual.setRutaFotograma(notificacion.getRutaFotograma());
            notificacionActual.setDescripcion(notificacion.getDescripcion());
            notificacionActual.setRevisado(notificacion.isRevisado());
            notificacionActual.setEnviado(notificacion.isEnviado());
            notificacionActual.setCriticidad(notificacion.getCriticidad());
            notificacionActual.setDescripcion(notificacion.getDescripcion());

            notificacionUpdated = notificacionService.save(notificacionActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar la notificacion en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "La notificacion ha sido actualizado con éxito!");
        response.put("notificacion", notificacionUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/notificaciones/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {

            Optional<Notificacion> optionalNotificacion = notificacionService.findByIdOptional(id);
            if (optionalNotificacion.isPresent()){

                Notificacion notificacion = notificacionService.findById(id);
                String nomreFotoAnterior = notificacion.getRutaFotograma();
                if (nomreFotoAnterior != null && nomreFotoAnterior.length() > 0) {
                    Path rutaFotoAnterior = Paths.get(uploadNotificaciones).resolve(nomreFotoAnterior).toAbsolutePath();
                    File archivoFotoAnterior = rutaFotoAnterior.toFile();
                    if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                        archivoFotoAnterior.delete();
                    }
                }

                notificacionService.delete(id);
                response.put("mensaje", "La notificacion ha sido eliminado con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, el notificacion ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);


        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el notificacion en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/notificaciones/uploads")
    public ResponseEntity<?> uploadNotificacion(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();

        Notificacion notificacion = notificacionService.findById(id);

        if(!archivo.isEmpty()){
            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = Paths.get(uploadNotificaciones).resolve(nombreArchivo).toAbsolutePath();
            log.info(rutaArchivo);

            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            }catch (IOException e){

                response.put("mensaje", "Error al subir la imagen " + nombreArchivo);
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nomreFotogramaAnterior = notificacion.getRutaFotograma();
            if (nomreFotogramaAnterior != null && nomreFotogramaAnterior.length() > 0) {
                Path rutaFotoAnterior = Paths.get(uploadNotificaciones).resolve(nomreFotogramaAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                    archivoFotoAnterior.delete();
                }
            }

            notificacion.setRutaFotograma(nombreArchivo);

            notificacionService.save(notificacion);

            response.put("notificacion", notificacion);
            response.put("mensaje", "Has subido correctamente la imagen de la notificación: " + nombreArchivo);

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @GetMapping("/notificaciones/uploads/img/{nombreFoto:.+}")
    public  ResponseEntity<Resource> verFotograma(@PathVariable String nombreFoto){

        Path rutaArchivo = Paths.get(uploadNotificaciones).resolve(nombreFoto).toAbsolutePath();
        log.info(rutaArchivo);
        Resource recurso = null;

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        }catch (MalformedURLException e){

        }

        if(!recurso.exists() && ! recurso.isReadable()){
            throw new RuntimeException("Error no se pudo cargar la imagen: " + nombreFoto);
        }
        HttpHeaders cabecera =  new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }
}
