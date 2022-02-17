package com.alkemy.disney.services;

import com.alkemy.disney.entities.Personaje;
import com.alkemy.disney.repositories.PersonajeRepository;
import java.util.List;
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
    
    
    
}
