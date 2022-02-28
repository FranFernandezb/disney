package com.alkemy.disney.security.repository;

import com.alkemy.disney.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    public abstract Usuario findByUsername(String username);
}
