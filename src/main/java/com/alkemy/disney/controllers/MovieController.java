package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.MovieJsonResponse;
import com.alkemy.disney.entities.Gender;
import com.alkemy.disney.entities.Movie;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.excepciones.ServiceException;
import com.alkemy.disney.services.GenderService;
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

import com.alkemy.disney.utils.constants.Constants;
import com.alkemy.disney.utils.messages.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private CharacterService characterService;

    @GetMapping("/detalle")
    public List<Movie> listarDetalles() {
        return this.movieService.displayMovies();
    }

    @GetMapping()
    public List<MovieJsonResponse> listAllMovies() {
        List<MovieJsonResponse> movies = new ArrayList();
        try {
            List<Movie> movieList = this.movieService.displayMovies();

            movieList.forEach((movieFromList) -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String date = dateFormat.format(movieFromList.getCreationDate());
                MovieJsonResponse movieJsonResponse = new MovieJsonResponse(
                        movieFromList.getImage(),
                        movieFromList.getTittle(),
                        date
                );
                movies.add(movieJsonResponse);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    @GetMapping("/name")
    public List<Movie> findByTittle(@RequestParam("tittle") String tittle) throws ServiceException {
        return this.movieService.findByTittle(tittle);
    }

    @GetMapping("/gender")
    public ArrayList<Movie> findByGender(@RequestParam("id") String id) throws ServiceException {
        return this.movieService.findByGender(id);
    }

    @GetMapping("/order")
    public List<Movie> sort(@RequestParam String order) throws ServiceException {
        return this.movieService.sort(order);
    }

    @PostMapping("/create")
    //EN VEZ DE REPETIR DOS VECES EL CODIGO PARA CREAR Y MODIFICAR UNA PELI. HACER METODOS PARA GUARDAR imageES Y FECHAS.
    public Movie crearMovie(@RequestParam MultipartFile image,
                            @RequestParam String tittle,
                            @RequestParam String fechaString,
                            @RequestParam Integer score,
                            @RequestParam String idGender,
                            @RequestParam String idCharacter) throws ServiceException {

        Movie movie = new Movie();

        this.movieService.validate(image, tittle, fechaString, score, idGender, idCharacter);

        movie.setTittle(tittle);
        movie.setScore(score);
        movie.setGender((Gender) genderService.findById(idGender).get());

        Character Character = this.characterService.findCharacterById(idCharacter);

        List<Character> lista = movie.getCharacters();
        lista.add(Character);
        movie.setCharacters(lista);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(fechaString);
            movie.setCreationDate(fecha);

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

                movie.setImage(image.getOriginalFilename());
            } catch (IOException ex) {
            }
        }

        return this.movieService.saveMovie(movie);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteMovie(@PathVariable("id") String id) {
        String response = null;
        try {
            boolean ok = this.movieService.deleteMovie(id);
            response = ok
                    ? String.format(Messages.ENTITY_DELETED_WITH_SUCCESS, Constants.MOVIE, id)
                    : String.format(Messages.ENTITY_UNABLE_TO_BE_DELETED, Constants.MOVIE, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @PutMapping(path = "{id}") // BUSCAR COMO DOCUMENTAR UNA API CON POSTMAN.   
    public Movie modificarMovie(@PathVariable("id") String id,
                                @RequestParam MultipartFile image,
                                @RequestParam String tittle,
                                @RequestParam String fechaString,
                                @RequestParam Integer score,
                                @RequestParam String idGender,
                                @RequestParam String idCharacter) throws ServiceException {

        Movie movie = (Movie) this.movieService.findMovieById(id).get();
        movie.setTittle(tittle);
        movie.setScore(score);
        movie.setGender((Gender) genderService.findById(idGender).get());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = dateFormat.parse(fechaString);
            movie.setCreationDate(fecha);

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

                movie.setImage(image.getOriginalFilename());
            } catch (IOException ex) {
            }
        }

        Character character = this.characterService.findCharacterById(idCharacter);

        List<Character> lista = movie.getCharacters();
        lista.add(character);
        movie.setCharacters(lista);
        return this.movieService.saveMovie(movie);
    }
}
