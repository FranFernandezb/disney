package com.alkemy.disney.controllers;

import com.alkemy.disney.entities.Pelicula;
import com.alkemy.disney.entities.Personaje;
import com.alkemy.disney.services.PeliculaService;
import com.alkemy.disney.services.PersonajeService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/characters")
public class PersonajeController {
    
    @Autowired
    private PersonajeService personajeService;
    
    @Autowired
    private PeliculaService peliculaService;
    
    
    @GetMapping()
    public ArrayList<String> listarPersonajes(){
        List<Personaje> lista = personajeService.listarPersonajes();
        ArrayList<String> personajes = new ArrayList();
        
        lista.forEach((l) -> {
            personajes.add("{ Imagen: " + l.getImagen() + ", Nombre: "+ l.getNombre()+" }" );
        });
        return personajes;
    }
    
    @GetMapping("/detalles")
    public List<Personaje> listarDetalles(){
        return personajeService.listarPersonajes();
    }
    
    @PostMapping("/create")
    public Personaje crearPersonaje(@RequestParam MultipartFile imagen,
            @RequestParam String nombre,
            @RequestParam Integer edad,
            @RequestParam Double peso,
            @RequestParam String historia,
            @RequestParam String idPelicula) {

        Personaje personaje = new Personaje();
        personaje.setNombre(nombre);
        personaje.setEdad(edad);
        personaje.setPeso(peso);
        personaje.setHistoria(historia);
        personaje.setPelicula((Pelicula) peliculaService.buscarPeliculaPorId(idPelicula).get()); //ARREGLAR ESTO CON VALIDACION IF OPTIONAL.ISPRESENT()
        
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                personaje.setImagen(imagen.getOriginalFilename());
            } catch (IOException ex) {
            }
        }
        

        return personajeService.guardarPersonaje(personaje);
    }

}
