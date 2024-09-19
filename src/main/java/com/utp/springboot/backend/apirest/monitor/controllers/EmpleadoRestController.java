package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.Empleado;
import com.utp.springboot.backend.apirest.monitor.models.service.IEmpleadoService;
//import jakarta.validation.Valid;
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

import javax.validation.Valid;
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
public class EmpleadoRestController {

    @Autowired
    @Qualifier("empleadoServiceJPA")
    private IEmpleadoService empleadoService;

    @Value("${spring.path.img.upload.empleado}")
    private String uploadEmpleados;

    @GetMapping("/empleados")
    public List<Empleado> index(){

        return empleadoService.findAll();
    }

    @GetMapping("/empleados/page/{page}")
    public Page<Empleado> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return empleadoService.findAll(pageable);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Empleado empleado = null;
        Map<String, Object> response = new HashMap<>();

        try {
            empleado = empleadoService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (empleado == null){
            response.put("mensaje", "El empleado ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
    }

    @PostMapping("/empleados")
    public ResponseEntity<?> create(@Valid @RequestBody Empleado empleado,
                                    BindingResult result){

        Empleado empleadoNuevo = null;
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
            empleadoNuevo = empleadoService.save(empleado);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El empleado ha sido creado con éxito!");
        response.put("empleado", empleadoNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Empleado empleado,
                                    BindingResult result,
                                    @PathVariable Long id){

        Empleado empleadoActual = empleadoService.findById(id);
        Empleado empleadoUpdated = null;

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

        if (empleadoActual == null){
            response.put("mensaje", "Error: No se pudo editar, el empleado ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

//            empleadoActual.setId(empleado.getId());
            empleadoActual.setFoto(empleado.getFoto());
            empleadoActual.setRutaFoto(empleado.getRutaFoto());
            empleadoActual.setLicencia(empleado.getLicencia());
            empleadoActual.setTipoSangre(empleado.getTipoSangre());
            empleadoActual.setCodigoPersona(empleado.getCodigoPersona());
            empleadoActual.setCodigoPuesto(empleado.getCodigoPuesto());

            empleadoUpdated = empleadoService.save(empleadoActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar el empleado en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "El empleado ha sido actualizado con éxito!");
        response.put("empleado", empleadoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {

            Optional<Empleado> optionalEmpleado = empleadoService.findByIdOptional(id);
            if (optionalEmpleado.isPresent()){

                Empleado empleado = empleadoService.findById(id);
                String nomreFotoAnterior = empleado.getRutaFoto();
                if (nomreFotoAnterior != null && nomreFotoAnterior.length() > 0) {
                    Path rutaFotoAnterior = Paths.get(uploadEmpleados).resolve(nomreFotoAnterior).toAbsolutePath();
                    File archivoFotoAnterior = rutaFotoAnterior.toFile();
                    if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                        archivoFotoAnterior.delete();
                    }
                }

                empleadoService.delete(id);
                response.put("mensaje", "El empleado ha sido eliminado con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, el empleado ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);


        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el empleado en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/empleados/uploads")
    public ResponseEntity<?> uploadEmpleado(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();

        Empleado empleado = empleadoService.findById(id);

        if(!archivo.isEmpty()){
            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = Paths.get(uploadEmpleados).resolve(nombreArchivo).toAbsolutePath();
            log.info(rutaArchivo);

            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            }catch (IOException e){

                response.put("mensaje", "Error al subir la imagen " + nombreArchivo);
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nomreFotoAnterior = empleado.getRutaFoto();
            if (nomreFotoAnterior != null && nomreFotoAnterior.length() > 0) {
                Path rutaFotoAnterior = Paths.get(uploadEmpleados).resolve(nomreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                    archivoFotoAnterior.delete();
                }
            }

            empleado.setRutaFoto(nombreArchivo);

            empleadoService.save(empleado);

            response.put("empleado", empleado);
            response.put("mensaje", "Has subido correctamente la imagen del empleado: " + nombreArchivo);

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @GetMapping("/empleados/uploads/img/{nombreFoto:.+}")
    public  ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){

        Path rutaArchivo = Paths.get(uploadEmpleados).resolve(nombreFoto).toAbsolutePath();
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
