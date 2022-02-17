package com.alkemy.disney.repositories;

import com.alkemy.disney.entities.Personaje;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, String> {
    
    public abstract ArrayList<Personaje> findByNombre(String nombre);
    
    public abstract ArrayList<Personaje> findByEdad(Integer edad);
    
    public abstract ArrayList<Personaje> findByPelicula(String id); //ARREGLAR
}
