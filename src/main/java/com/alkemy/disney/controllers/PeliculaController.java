package com.alkemy.disney.controllers;

import com.alkemy.disney.entities.Genero;
import com.alkemy.disney.entities.Pelicula;
import com.alkemy.disney.entities.Personaje;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.services.GeneroService;
import com.alkemy.disney.services.PeliculaService;
import com.alkemy.disney.services.PersonajeService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pelicula")
public class PeliculaController {
    
    @Autowired
    private PeliculaService peliculaService;
    
    @Autowired
    private GeneroService generoService;
    
    @Autowired
    private PersonajeService personajeService;
    
    @GetMapping("/detalle")
    public List<Pelicula> listarDetalles() {
        return peliculaService.mostrarPeliculas();
    }
    
    @GetMapping()
    public ArrayList<String> listarPeliculas() {
        List<Pelicula> lista = peliculaService.mostrarPeliculas();
        ArrayList<String> peliculas = new ArrayList();
        
        lista.forEach((l) -> {
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            String date = formato.format(l.getFechaCreacion());
            peliculas.add("{ Imagen: " + l.getImagen() + ", Título: " + l.getTitulo() + ", Fecha de Creación: " + date + " }");
        });
        return peliculas;
    }
    
    @GetMapping("/name")
    public ArrayList<Pelicula> obtenerPorTitulo(@RequestParam("titulo") String titulo) throws ErrorServicio {
        return peliculaService.buscarPorTitulo(titulo);
    }
    
    @GetMapping("/genre")
    public ArrayList<Pelicula> obtenerPorGenero(@RequestParam("id") String id) throws ErrorServicio {
        return peliculaService.buscarPorGenero(id);
    }
    
    @GetMapping("/order")
    public ArrayList<Pelicula> ordenar(@RequestParam String orden) throws ErrorServicio {
        return peliculaService.ordenar(orden);
    }
    
    @PostMapping("/create") //EN VEZ DE REPETIR DOS VECES EL CODIGO PARA CREAR Y MODIFICAR UNA PELI. HACER METODOS PARA GUARDAR IMAGENES Y FECHAS.
    public Pelicula crearPelicula(@RequestParam MultipartFile imagen,
            @RequestParam String titulo,
            @RequestParam String fechaString,
            @RequestParam Integer calificacion,
            @RequestParam String idGenero,
            @RequestParam String idPersonaje) throws ErrorServicio {
        
        Pelicula pelicula = new Pelicula();
        
        peliculaService.validate(imagen, titulo, fechaString, calificacion, idGenero, idPersonaje);
        
        pelicula.setTitulo(titulo);
        pelicula.setCalificacion(calificacion);
        pelicula.setGenero((Genero) generoService.buscarPorId(idGenero).get());
        
        Personaje personaje = (Personaje) personajeService.listarPersonajePorId(idPersonaje).get();
        
        List<Personaje> lista = pelicula.getPersonajes();
        lista.add(personaje);
        pelicula.setPersonajes(lista);
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(fechaString);
            pelicula.setFechaCreacion(fecha);
            
        } catch (ParseException ex) {
            ex.getMessage();
        }
        
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            
            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                
                pelicula.setImagen(imagen.getOriginalFilename());
            } catch (IOException ex) {
            }
        }
        
        return peliculaService.guardarPelicula(pelicula);
    }
    
    @DeleteMapping(path = "/{id}")
    public String eliminarPelicula(@PathVariable("id") String id) {
        boolean ok = this.peliculaService.eliminarPelicula(id);
        
        if (ok) {
            return "Se eliminó la película ";
        } else {
            return "No se pudo eliminar la película con ese Id";
        }
    }
    
    @PutMapping(path = "{id}") // BUSCAR COMO DOCUMENTAR UNA API CON POSTMAN.   
    public Pelicula modificarPelicula(@PathVariable("id") String id,
            @RequestParam MultipartFile imagen,
            @RequestParam String titulo,
            @RequestParam String fechaString,
            @RequestParam Integer calificacion,
            @RequestParam String idGenero,
            @RequestParam String idPersonaje) throws ErrorServicio {
        
        Pelicula pelicula = (Pelicula) peliculaService.buscarPeliculaPorId(id).get();
        pelicula.setTitulo(titulo);
        pelicula.setCalificacion(calificacion);
        pelicula.setGenero((Genero) generoService.buscarPorId(idGenero).get());
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(fechaString);
            pelicula.setFechaCreacion(fecha);
            
        } catch (ParseException ex) {
            ex.getMessage();
        }
        
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            
            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                
                pelicula.setImagen(imagen.getOriginalFilename());
            } catch (IOException ex) {
            }
        }
        
        Personaje personaje = (Personaje) personajeService.listarPersonajePorId(idPersonaje).get();
        
        List<Personaje> lista = pelicula.getPersonajes();
        lista.add(personaje);
        pelicula.setPersonajes(lista);
        return peliculaService.guardarPelicula(pelicula);
        
    }
    
}
