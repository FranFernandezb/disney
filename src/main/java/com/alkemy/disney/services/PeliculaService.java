package com.alkemy.disney.services;

import com.alkemy.disney.entities.Pelicula;
import com.alkemy.disney.repositories.PeliculaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeliculaService {
    
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    public List<Pelicula> mostrarPeliculas(){
        return peliculaRepository.findAll();
    }
    
    public Pelicula guardarPelicula(Pelicula pelicula){
        return peliculaRepository.save(pelicula);
    }
    
    public boolean eliminarPelicula(String id){
        try{
            peliculaRepository.deleteById(id);
            return true;
        } catch(Exception err){
            return false;
        }
    }
    
    public Optional buscarPeliculaPorId(String id){
        return peliculaRepository.findById(id);
    }
}
