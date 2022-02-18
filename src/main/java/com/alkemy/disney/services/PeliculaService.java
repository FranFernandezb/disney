package com.alkemy.disney.services;

import com.alkemy.disney.entities.Pelicula;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.repositories.PeliculaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional buscarPeliculaPorId(String id) {
        return peliculaRepository.findById(id);
    }

    public ArrayList<Pelicula> buscarPorTitulo(String titulo) {
        return peliculaRepository.findByTitulo(titulo);
    }

    public ArrayList<Pelicula> buscarPorGenero(String id) {
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
}
