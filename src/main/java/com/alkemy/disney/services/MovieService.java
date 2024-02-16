package com.alkemy.disney.services;

import com.alkemy.disney.entities.Movie;
import com.alkemy.disney.excepciones.ServiceException;
import com.alkemy.disney.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> displayMovies() {
        return this.movieRepository.findAll();
    }

    public Movie saveMovie(Movie Movie) {
        return this.movieRepository.save(Movie);
    }

    public boolean deleteMovie(String id) {
        try {
            this.movieRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public Optional findMovieById(String id) throws ServiceException {

        if (this.movieRepository.findById(id).isPresent()) {
            return this.movieRepository.findById(id);
        } else {
            throw new ServiceException("No se ha podido encontrar una película con ese ID.");
        }

    }

    public List<Movie> findByTittle(String tittle) throws ServiceException {

        if (this.movieRepository.findBytittle(tittle).isEmpty()) {
            throw new ServiceException("No se ha podido encontrar una película con ese título.");
        }

        return this.movieRepository.findBytittle(tittle);
    }

    public ArrayList<Movie> findByGender(String id) throws ServiceException {

        if (this.movieRepository.findByGender(id).isEmpty()) {
            throw new ServiceException("No se ha podido encontrar una película de ese género.");
        }
        return this.movieRepository.findByGender(id);
    }

    public List<Movie> sort(String orden) throws ServiceException {
        if (orden.toUpperCase().matches("ASC")) {
            return this.movieRepository.findAllByOrderByCreationDateAsc();
        } else if (orden.toUpperCase().matches("DESC")) {
            return this.movieRepository.findAllByOrderByCreationDateDesc();
        } else {
            throw new ServiceException("NO SE HA INGRESADO NINGÚN ORDEN VÁLIDO.");
        }
    }

    public void validate(MultipartFile image, String tittle, String fechaString, Integer score, String idGender, String idCharacter) throws ServiceException {
        if (image == null || image.isEmpty()) {
            throw new ServiceException("La película debe tener una image asociada.");
        }

        if (tittle == null || tittle.isEmpty()) {
            throw new ServiceException("La película debe tener un título");
        }

        if (fechaString == null || fechaString.isEmpty()) {
            throw new ServiceException("La película debe tener una fecha de creación");
        }

        if (score == null || score < 1 || score > 5) {
            throw new ServiceException("La película debe estar calificada y debe ser del 1 al 5.");
        }

        if (idGender == null || idGender.isEmpty()) {
            throw new ServiceException("La película debe tener un género asociado.");
        }

        if (idCharacter == null || idCharacter.isEmpty()) {
            throw new ServiceException("La película debe tener un Character asociado.");

        }

    }
}
