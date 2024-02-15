package com.alkemy.disney.repositories;

import com.alkemy.disney.entities.Character;
import com.alkemy.disney.entities.Character;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, String> {
    
    public abstract ArrayList<Character> findByName(String name);
    
    public abstract ArrayList<Character> findByage(Integer age);
    
    @Query("SELECT p FROM Character p WHERE p.movie.id = :id")
    public ArrayList<Character> findByMovie(@Param("id")String id);
}
