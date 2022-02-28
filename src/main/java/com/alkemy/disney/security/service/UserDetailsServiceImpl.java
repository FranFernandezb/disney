package com.alkemy.disney.security.service;

import com.alkemy.disney.security.entity.Usuario;
import com.alkemy.disney.security.entity.UsuarioSeguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioService usuarioService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioService.findByUsername(username);
        
        return UsuarioSeguridad.build(usuario);
        
        
    }
    
    
}
