package com.alkemy.disney.services;

import com.alkemy.disney.entities.Pelicula;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.repositories.PeliculaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<Pelicula> mostrarPeliculas() {
        return peliculaRepository.findAll();
    }

    public Pelicula guardarPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public boolean eliminarPelicula(String id) {
        try {
            peliculaRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public Optional buscarPeliculaPorId(String id) throws ErrorServicio {

        if (peliculaRepository.findById(id).isPresent()) {
            return peliculaRepository.findById(id);
        } else {
            throw new ErrorServicio("No se ha podido encontrar una película con ese ID.");
        }

    }

    public ArrayList<Pelicula> buscarPorTitulo(String titulo) throws ErrorServicio {

        if (peliculaRepository.findByTitulo(titulo).isEmpty()) {
            throw new ErrorServicio("No se ha podido encontrar una película con ese título.");
        }

        return peliculaRepository.findByTitulo(titulo);
    }

    public ArrayList<Pelicula> buscarPorGenero(String id) throws ErrorServicio {

        if (peliculaRepository.buscarPorGenero(id).isEmpty()) {
            throw new ErrorServicio("No se ha podido encontrar una película de ese género.");
        }
        return peliculaRepository.buscarPorGenero(id);
    }

    public ArrayList<Pelicula> ordenar(String orden) throws ErrorServicio {
        if (orden.toUpperCase().matches("ASC")) {
            return peliculaRepository.ordenarASC();
        } else if (orden.toUpperCase().matches("DESC")) {
            return peliculaRepository.ordenarDESC();
        } else {
            throw new ErrorServicio("NO SE HA INGRESADO NINGÚN ORDEN VÁLIDO.");
        }
    }

    public void validate(MultipartFile imagen, String titulo, String fechaString, Integer calificacion, String idGenero, String idPersonaje) throws ErrorServicio {
        if (imagen == null || imagen.isEmpty()) {
            throw new ErrorServicio("La película debe tener una imagen asociada.");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("La película debe tener un título");
        }

        if (fechaString == null || fechaString.isEmpty()) {
            throw new ErrorServicio("La película debe tener una fecha de creación");
        }

        if (calificacion == null || calificacion < 1 || calificacion > 5) {
            throw new ErrorServicio("La película debe estar calificada y debe ser del 1 al 5.");
        }

        if (idGenero == null || idGenero.isEmpty()) {
            throw new ErrorServicio("La película debe tener un género asociado.");
        }

        if (idPersonaje == null || idPersonaje.isEmpty()) {
            throw new ErrorServicio("La película debe tener un personaje asociado.");

        }

    }
}
