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
    public ArrayList<String> listarPersonajes() {
        List<Personaje> lista = personajeService.listarPersonajes();
        ArrayList<String> personajes = new ArrayList();

        lista.forEach((l) -> {
            personajes.add("{ Imagen: " + l.getImagen() + ", Nombre: " + l.getNombre() + " }");
        });
        return personajes;
    }

    @GetMapping("/detalles")
    public List<Personaje> listarDetalles() {
        return personajeService.listarPersonajes();
    }

    @GetMapping("/name")
    public ArrayList<Personaje> obtenerPorNombre(@RequestParam("nombre") String nombre) {
        return personajeService.buscarPorNombre(nombre);
    }

    @GetMapping("/age")
    public ArrayList<Personaje> obtenerPorEdad(@RequestParam("edad") Integer edad) {
        return personajeService.buscarPorEdad(edad);
    }

    @GetMapping("/movie") //ARREGLAR ESTE MÉTODO. HACER UNA QUERY ENTERA EN VEZ DE UTILIZAR UN METODO EN EL REPO
    public ArrayList<Personaje> obtenerPorPeli(@RequestParam("pelicula") String id) {
        return personajeService.buscarPorPeli(id);
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

    @DeleteMapping(path = "/{id}")
    public String eliminarPersonaje(@PathVariable("id") String id) {
        boolean ok = this.personajeService.eliminarPersonaje(id);

        if (ok) {
            return "Se eliminó el personaje correctamente.";
        } else {
            return "No se pudo eliminar al personaje con ese Id.";
        }
    }
    
    @PutMapping(path = "/{id}")
    public Personaje editarPersonaje(@PathVariable("id") String id,
            @RequestParam MultipartFile imagen,
            @RequestParam String nombre,
            @RequestParam Integer edad,
            @RequestParam Double peso,
            @RequestParam String historia,
            @RequestParam String idPelicula) {

        Personaje personaje = (Personaje) personajeService.listarPersonajePorId(id).get();
        personaje.setNombre(nombre);
        personaje.setEdad(edad);
        personaje.setPeso(peso);
        personaje.setHistoria(historia);
        personaje.setPelicula((Pelicula) peliculaService.buscarPeliculaPorId(idPelicula).get());

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
