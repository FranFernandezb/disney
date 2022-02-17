package com.alkemy.disney.services;

import com.alkemy.disney.entities.Genero;
import com.alkemy.disney.repositories.GeneroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneroService {
    
    @Autowired
    private GeneroRepository generoRepository;
    
    public Genero guardarGenero(Genero genero){
        return generoRepository.save(genero);
    }
    
    public List<Genero> listarGeneros(){
        return generoRepository.findAll();
    }
    
    public boolean eliminarGenero (String id){
        try{
            generoRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
    
    public Optional buscarPorId(String id){
        return generoRepository.findById(id);
        
    }
}
