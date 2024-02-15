package com.alkemy.disney.services;

import com.alkemy.disney.entities.Genero;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.repositories.GeneroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public Genero guardarGenero(Genero genero) {
        return generoRepository.save(genero);
    }

    public List<Genero> listarGeneros() {
        return generoRepository.findAll();
    }

    public boolean eliminarGenero(String id) {
        try {
            generoRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public Optional buscarPorId(String id) throws ErrorServicio {

        if (generoRepository.findById(id).isPresent()) {
            return generoRepository.findById(id);
        } else {
            throw new ErrorServicio("No se ha encontrado un género con ese ID.");
        }

    }

    public void validate(String name, MultipartFile image) throws ErrorServicio {

        if (name == null || name.isEmpty()) {
            throw new ErrorServicio("El género debe tener un name.");
        }

        if (image == null || image.isEmpty()) {
            throw new ErrorServicio("El género debe tener una image.");

        }
    }
}
