package com.alkemy.disney.controllers;

import com.alkemy.disney.entities.Genero;
import com.alkemy.disney.entities.Pelicula;
import com.alkemy.disney.services.GeneroService;
import com.alkemy.disney.services.PeliculaService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @GetMapping()
    public List<Pelicula> listarPeliculas() {
        return peliculaService.mostrarPeliculas();
    }

    @PostMapping("/create")
    public Pelicula crearPelicula(@RequestParam MultipartFile imagen,
            @RequestParam String titulo,
            @RequestParam String fechaString,
            @RequestParam Integer calificacion,
            @RequestParam String idGenero) {

        Pelicula pelicula = new Pelicula();
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

        return peliculaService.guardarPelicula(pelicula);
    }
}
