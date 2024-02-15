package com.alkemy.disney.repositories;

import com.alkemy.disney.entities.Movie;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    List<Movie> findBytittle(String tittle);

    List<Movie> findAllByOrderByCreationDateAsc();

    List<Movie> findAllByOrderByCreationDateDesc();

    @Query("SELECT p FROM Movie p WHERE p.gender.id = :id")
    ArrayList<Movie> findByGender(@Param("id") String id);
}
