package com.alkemy.disney.services;

import com.alkemy.disney.entities.Personaje;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.repositories.PersonajeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    public List<Personaje> listarPersonajes() {
        return personajeRepository.findAll();
    }

    public boolean eliminarPersonaje(String id) {
        try {
            personajeRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public Personaje guardarPersonaje(Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    public Optional listarPersonajePorId(String id) throws ErrorServicio {

        if (personajeRepository.findById(id).isPresent()) {
            return personajeRepository.findById(id);
        } else {
            throw new ErrorServicio("No se ha encontrado ningún personaje con ese ID.");
        }

    }

    public ArrayList<Personaje> buscarPorNombre(String nombre) throws ErrorServicio {

        if (personajeRepository.findByNombre(nombre).isEmpty()) {
            throw new ErrorServicio("No se ha encontrado un personaje con ese nombre.");
        }

        return personajeRepository.findByNombre(nombre);
    }

    public ArrayList<Personaje> buscarPorEdad(Integer edad) throws ErrorServicio {

        if (personajeRepository.findByEdad(edad).isEmpty()) {
            throw new ErrorServicio("No se ha encontrado un personaje con esa edad. ");
        }

        return personajeRepository.findByEdad(edad);
    }

    public ArrayList<Personaje> buscarPorPeli(String id) throws ErrorServicio {

        if (personajeRepository.buscarPorPeli(id).isEmpty()) {
            throw new ErrorServicio("No se ha encontrado un personaje para esa película.");
        }

        return personajeRepository.buscarPorPeli(id);
    }

    public void validate(MultipartFile imagen, String nombre, Integer edad, Double peso, String historia, String idPelicula) throws ErrorServicio {

        if (imagen == null || imagen.isEmpty()) {
            throw new ErrorServicio("El personaje debe tener una imagen asociada.");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El personaje debe tener un nombre");
        }

        if (edad == null || edad < 0) {
            throw new ErrorServicio("El personaje debe tener una edad y debe ser mayor a 0.");
        }

        if (peso == null || peso < 0) {
            throw new ErrorServicio("El personaje debe tener un peso y debe ser mayor a 0.");
        }

        if (historia == null || historia.isEmpty()) {
            throw new ErrorServicio("El personaje debe tener una historia.");
        }

        if (idPelicula == null || idPelicula.isEmpty()) {
            throw new ErrorServicio("El personaje debe tener una película asociada.");
        }
    }
}
