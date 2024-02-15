package com.alkemy.disney.security.service;

import com.alkemy.disney.security.entity.Usuario;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.security.enums.Roles;
import com.alkemy.disney.security.repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService{
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    @Transactional
    public void save(Usuario usuario) {
        //Usuario usuario = new Usuario();
        
//        if (username == null || username.isEmpty()) {
//            throw new ErrorServicio("El name de usuario no puede estar vacío.");
//        }
//        if (password == null || password.isEmpty()) {
//            throw new ErrorServicio("La contraseña no puede estar vacía.");
//        }
//        if (email == null || email.isEmpty()) {
//            throw new ErrorServicio("La contraseña no puede estar vacía.");
//        }
        usuario.setRol(Roles.USER);
        usuarioRepository.save(usuario);
    }
    
    public Usuario findByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }
    
    


}
