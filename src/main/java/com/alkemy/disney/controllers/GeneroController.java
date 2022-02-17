package com.alkemy.disney.controllers;

import com.alkemy.disney.entities.Genero;
import com.alkemy.disney.services.GeneroService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/genero")
public class GeneroController {
    
    @Autowired
    private GeneroService generoService;

    @GetMapping()
    public List<Genero> listarGeneros() {
        return generoService.listarGeneros();
    }

    @PostMapping("/create")
    public Genero crearGenero(@RequestParam("nombre") String nombre, @RequestParam("imagen") MultipartFile imagen) {

        Genero genero = new Genero();
        genero.setNombre(nombre);
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                genero.setImagen(imagen.getOriginalFilename());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return generoService.guardarGenero(genero);
    }
    
    @DeleteMapping(path = "/{id}")
    public String eliminar(@PathVariable("id")String id){
        boolean ok = this.generoService.eliminarGenero(id);
        
        if (ok){
            return "Se eliminó el género";
        } else{
            return "No se pudo eliminar al género con ese Id";
        }
    }
    
    @PutMapping(path = "/{id}")
    public Genero modificarGenero (@PathVariable("id")String id, @RequestParam String nombre, @RequestParam MultipartFile imagen){
        
        Genero genero = (Genero) generoService.buscarPorId(id).get();
        
        genero.setNombre(nombre);
        
        Path directorioImagenes = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                genero.setImagen(imagen.getOriginalFilename());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
        return generoService.guardarGenero(genero);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
