package com.alkemy.disney.repositories;

import com.alkemy.disney.entities.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, String> {
    
}
