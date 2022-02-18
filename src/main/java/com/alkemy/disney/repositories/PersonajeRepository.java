package com.alkemy.disney.repositories;

import com.alkemy.disney.entities.Personaje;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, String> {
    
    public abstract ArrayList<Personaje> findByNombre(String nombre);
    
    public abstract ArrayList<Personaje> findByEdad(Integer edad);
    
    @Query("SELECT p FROM Personaje p WHERE p.pelicula.id = :id")
    public ArrayList<Personaje> buscarPorPeli(@Param("id")String id);
}
