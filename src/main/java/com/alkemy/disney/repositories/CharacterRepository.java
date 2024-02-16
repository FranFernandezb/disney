package com.alkemy.disney.repositories;

import java.util.ArrayList;

import com.alkemy.disney.entities.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Figure, String> {
    
    public abstract ArrayList<Figure> findByName(String name);
    
    public abstract ArrayList<Figure> findByage(Integer age);
    
    @Query("SELECT p FROM Figure p WHERE p.movie.id = :id")
    public ArrayList<Figure> findByMovie(@Param("id")String id);
}
