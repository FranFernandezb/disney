package com.alkemy.disney.security.controller;

import com.alkemy.disney.security.dto.JwtDTO;
import com.alkemy.disney.security.dto.LoginUsuario;
import com.alkemy.disney.security.dto.NuevoUsuario;
import com.alkemy.disney.security.entity.Usuario;
import com.alkemy.disney.security.enums.Roles;
import com.alkemy.disney.security.jwt.JwtProvider;
import com.alkemy.disney.security.service.UsuarioService;
import com.alkemy.disney.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<NuevoUsuario> registrar(@RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                //return new ResponseEntity("Ha ocurrido un error", HttpStatus.BAD_REQUEST);
            }

            Usuario usuario = new Usuario();


            usuario.setUsername(nuevoUsuario.getUsername());
            usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
            usuario.setEmail(nuevoUsuario.getEmail());
            Roles rol = nuevoUsuario.getEmail().equalsIgnoreCase("franciscofernandezb@outlook.com") ? Roles.ADMIN : Roles.USER;
            usuario.setRol(rol);
            usuarioService.save(usuario);
            // emailService.enviarEmail(nuevoUsuario.getEmail());

        } catch (Exception e) {
            e.printStackTrace();
            // return new ResponseEntity("Ocurrió un error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        JwtDTO jwtDto;
        try {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity("No se ha podido iniciar sesión, datos incorrectos.", HttpStatus.BAD_REQUEST);
            }

            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(),
                            loginUsuario.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            jwtDto = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

}
