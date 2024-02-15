package com.alkemy.disney.controllers;

import com.alkemy.disney.entities.Genero;
import com.alkemy.disney.entities.Movie;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.services.GeneroService;
import com.alkemy.disney.services.MovieService;
import com.alkemy.disney.services.CharacterService;
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
@RequestMapping("/Movie")
public class MovieController {
    
    @Autowired
    private MovieService MovieService;
    
    @Autowired
    private GeneroService generoService;
    
    @Autowired
    private CharacterService CharacterService;
    
    @GetMapping("/detalle")
    public List<Movie> listarDetalles() {
        return MovieService.displayMovies();
    }
    
    @GetMapping()
    public ArrayList<String> listarMovies() {
        List<Movie> lista = MovieService.displayMovies();
        ArrayList<String> Movies = new ArrayList();
        
        lista.forEach((l) -> {
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            String date = formato.format(l.getcreationDate());
            Movies.add("{ image: " + l.getimage() + ", Título: " + l.gettittle() + ", Fecha de Creación: " + date + " }");
        });
        return Movies;
    }
    
    @GetMapping("/name")
    public ArrayList<Movie> obtenerPortittle(@RequestParam("tittle") String tittle) throws ErrorServicio {
        return MovieService.buscarPortittle(tittle);
    }
    
    @GetMapping("/genre")
    public ArrayList<Movie> obtenerPorGenero(@RequestParam("id") String id) throws ErrorServicio {
        return MovieService.buscarPorGenero(id);
    }
    
    @GetMapping("/order")
    public ArrayList<Movie> ordenar(@RequestParam String orden) throws ErrorServicio {
        return MovieService.ordenar(orden);
    }
    
    @PostMapping("/create") //EN VEZ DE REPETIR DOS VECES EL CODIGO PARA CREAR Y MODIFICAR UNA PELI. HACER METODOS PARA GUARDAR imageES Y FECHAS.
    public Movie crearMovie(@RequestParam MultipartFile image,
            @RequestParam String tittle,
            @RequestParam String fechaString,
            @RequestParam Integer score,
            @RequestParam String idGenero,
            @RequestParam String idCharacter) throws ErrorServicio {
        
        Movie Movie = new Movie();
        
        MovieService.validate(image, tittle, fechaString, score, idGenero, idCharacter);
        
        Movie.settittle(tittle);
        Movie.setscore(score);
        Movie.setGenero((Genero) generoService.buscarPorId(idGenero).get());
        
        Character Character = CharacterService.listarCharacterPorId(idCharacter);
        
        List<Character> lista = Movie.getCharacters();
        lista.add(Character);
        Movie.setCharacters(lista);
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(fechaString);
            Movie.setcreationDate(fecha);
            
        } catch (ParseException ex) {
            ex.getMessage();
        }
        
        if (!image.isEmpty()) {
            Path directorioimagees = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioimagees.toFile().getAbsolutePath();
            
            try {
                byte[] bytesImg = image.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + image.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                
                Movie.setimage(image.getOriginalFilename());
            } catch (IOException ex) {
            }
        }
        
        return MovieService.saveMovie(Movie);
    }
    
    @DeleteMapping(path = "/{id}")
    public String deleteMovie(@PathVariable("id") String id) {
        boolean ok = this.MovieService.deleteMovie(id);
        
        if (ok) {
            return "Se eliminó la película ";
        } else {
            return "No se pudo eliminar la película con ese Id";
        }
    }
    
    @PutMapping(path = "{id}") // BUSCAR COMO DOCUMENTAR UNA API CON POSTMAN.   
    public Movie modificarMovie(@PathVariable("id") String id,
            @RequestParam MultipartFile image,
            @RequestParam String tittle,
            @RequestParam String fechaString,
            @RequestParam Integer score,
            @RequestParam String idGenero,
            @RequestParam String idCharacter) throws ErrorServicio {
        
        Movie Movie = (Movie) MovieService.buscarMoviePorId(id).get();
        Movie.settittle(tittle);
        Movie.setscore(score);
        Movie.setGenero((Genero) generoService.buscarPorId(idGenero).get());
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(fechaString);
            Movie.setcreationDate(fecha);
            
        } catch (ParseException ex) {
            ex.getMessage();
        }
        
        if (!image.isEmpty()) {
            Path directorioimagees = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioimagees.toFile().getAbsolutePath();
            
            try {
                byte[] bytesImg = image.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + image.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                
                Movie.setimage(image.getOriginalFilename());
            } catch (IOException ex) {
            }
        }
        
        Character Character = (Character) CharacterService.listarCharacterPorId(idCharacter).get();
        
        List<Character> lista = Movie.getCharacters();
        lista.add(Character);
        Movie.setCharacters(lista);
        return MovieService.saveMovie(Movie);
        
    }
    
}
