package com.alkemy.disney.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioSeguridad implements UserDetails {

    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioSeguridad(String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }
    
    public static UsuarioSeguridad build(Usuario usuario){
        List<GrantedAuthority> authorities = new ArrayList<>();
            
            authorities.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
        
        return new UsuarioSeguridad(usuario.getUsername(),usuario.getPassword(),usuario.getEmail(),authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    
    
    
}
