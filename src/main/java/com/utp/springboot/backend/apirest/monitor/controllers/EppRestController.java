package com.utp.springboot.backend.apirest.monitor.controllers;

import com.utp.springboot.backend.apirest.monitor.models.entity.Categoria;
import com.utp.springboot.backend.apirest.monitor.models.entity.Epp;
import com.utp.springboot.backend.apirest.monitor.models.service.IEppService;
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
public class EppRestController {

    @Autowired
    @Qualifier("eppServiceJPA")
    private IEppService eppService;

    @Value("${spring.path.img.upload.epp}")
    private String uploadEpps;


    @GetMapping("/epps")
    public List<Epp> index(){

        return eppService.findAll();
    }

    @GetMapping("/epps/categorias")
    public List<Categoria> listarCategorias(){

        return eppService.findAllCategorias();
    }

    @GetMapping("/epps/page/{page}")
    public Page<Epp> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,10);
        return eppService.findAll(pageable);
    }

    @GetMapping("/epps/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Epp epp = null;
        Map<String, Object> response = new HashMap<>();

        try {
            epp = eppService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (epp == null){
            response.put("mensaje", "El epp ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Epp>(epp, HttpStatus.OK);
    }

    @PostMapping("/epps")
    public ResponseEntity<?> create(@Valid @RequestBody Epp epp,
                                    BindingResult result){

        Epp eppNuevo = null;
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
            eppNuevo = eppService.save(epp);
        }catch (DataAccessException e){

            response.put("mensaje", "Error al realizar el insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El epp ha sido creado con éxito!");
        response.put("epp", eppNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/epps/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Epp epp,
                                    BindingResult result,
                                    @PathVariable Long id){

        Epp eppActual = eppService.findById(id);
        Epp eppUpdated = null;

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

        if (eppActual == null){
            response.put("mensaje", "Error: No se pudo editar, el epp ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

//            eppActual.setId(epp.getId());
            eppActual.setNombre(epp.getNombre());
            eppActual.setStockActual(epp.getStockActual());
            eppActual.setTalla(epp.getTalla());
            eppActual.setImagen(epp.getImagen());
            eppActual.setRutaImagen(epp.getRutaImagen());
            eppActual.setPrecio(epp.getPrecio());
            eppActual.setCodigoCategoria(epp.getCodigoCategoria());

            eppUpdated = eppService.save(eppActual);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar el epp en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "El epp ha sido actualizado con éxito!");
        response.put("epp", eppUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/epps/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Epp> optionalEpp = eppService.findByIdOptional(id);
            if (optionalEpp.isPresent()){

                Epp epp = eppService.findById(id);
                String nomreFotoAnterior = epp.getRutaImagen();
                if (nomreFotoAnterior != null && nomreFotoAnterior.length() > 0) {
                    Path rutaFotoAnterior = Paths.get(uploadEpps).resolve(nomreFotoAnterior).toAbsolutePath();
                    File archivoFotoAnterior = rutaFotoAnterior.toFile();
                    if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                        archivoFotoAnterior.delete();
                    }
                }

                eppService.delete(id);
                response.put("mensaje", "El epp ha sido eliminado con éxito!");

                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }
            response.put("mensaje", "Error: No se pudo eliminar, el epp ID: ".concat(id.toString().concat(" no existe en la base datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar el epp en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/epps/uploads")
    public ResponseEntity<?> uploadEpp(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();

        Epp epp = eppService.findById(id);

        if(!archivo.isEmpty()){
            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = Paths.get(uploadEpps).resolve(nombreArchivo).toAbsolutePath();
            log.info(rutaArchivo);

            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            }catch (IOException e){

                response.put("mensaje", "Error al subir la imagen " + nombreArchivo);
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nomreFotoAnterior = epp.getRutaImagen();
            if (nomreFotoAnterior != null && nomreFotoAnterior.length() > 0) {
                Path rutaFotoAnterior = Paths.get(uploadEpps).resolve(nomreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                    archivoFotoAnterior.delete();
                }
            }

            epp.setRutaImagen(nombreArchivo);

            eppService.save(epp);

            response.put("epp", epp);
            response.put("mensaje", "Has subido correctamente la imagen del epp: " + nombreArchivo);

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @GetMapping("/epps/uploads/img/{nombreFoto:.+}")
    public  ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){

        Path rutaArchivo = Paths.get(uploadEpps).resolve(nombreFoto).toAbsolutePath();
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
