package com.alkemy.disney.repositories;

import com.alkemy.disney.entities.Pelicula;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, String> {
    
    public abstract ArrayList<Pelicula> findByTitulo(String titulo);
    
    @Query("SELECT p FROM Pelicula p ORDER BY p.fechaCreacion ASC")
    public ArrayList<Pelicula> ordenarASC();
    
    @Query("SELECT p FROM Pelicula p ORDER BY p.fechaCreacion DESC")
    public ArrayList<Pelicula> ordenarDESC();
    
    @Query("SELECT p FROM Pelicula p WHERE p.genero.id = :id")
    public ArrayList<Pelicula> buscarPorGenero(@Param("id")String id);
}
