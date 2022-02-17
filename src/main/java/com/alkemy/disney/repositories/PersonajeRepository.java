package com.alkemy.disney.repositories;

import com.alkemy.disney.entities.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, String> {
    
}
