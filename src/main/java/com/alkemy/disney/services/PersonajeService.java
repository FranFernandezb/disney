package com.alkemy.disney.services;

import com.alkemy.disney.entities.Personaje;
import com.alkemy.disney.repositories.PersonajeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonajeService {
    
    @Autowired
    private PersonajeRepository personajeRepository;
    
    public List<Personaje> listarPersonajes(){
        return personajeRepository.findAll();
    }
    
    public boolean eliminarPersonaje(String id){
        try{
            personajeRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
    
    public Personaje guardarPersonaje(Personaje personaje){
        return personajeRepository.save(personaje);
    }
    
    public Optional listarPersonajePorId(String id){
        return personajeRepository.findById(id);
    }
    
    public ArrayList<Personaje> buscarPorNombre(String nombre){
        return personajeRepository.findByNombre(nombre);
    }
    
    public ArrayList<Personaje> buscarPorEdad(Integer edad){
        return personajeRepository.findByEdad(edad);
    }
    
    public ArrayList<Personaje> buscarPorPeli(String id){
        return personajeRepository.findByPelicula(id);
    }
    
    
}
