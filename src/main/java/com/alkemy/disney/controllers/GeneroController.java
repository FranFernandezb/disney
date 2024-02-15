package com.alkemy.disney.controllers;

import com.alkemy.disney.entities.Genero;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.services.GeneroService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/genero")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping()
    public List<Genero> listarGeneros() {
        return generoService.listarGeneros();
    }

    @PostMapping("/create")
    public Genero crearGenero(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) throws ErrorServicio {

        Genero genero = new Genero();

        generoService.validate(name, image);
        genero.setname(name);
        if (!image.isEmpty()) {
            Path directorioimagees = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioimagees.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = image.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + image.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                genero.setimage(image.getOriginalFilename());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return generoService.guardarGenero(genero);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminar(@PathVariable("id") String id) {
        boolean ok = this.generoService.eliminarGenero(id);

        if (ok) {
            return "Se eliminó el género";
        } else {
            return "No se pudo eliminar al género con ese Id";
        }
    }

    @PutMapping(path = "/{id}")
    public Genero modificarGenero(@PathVariable("id") String id, @RequestParam String name, @RequestParam MultipartFile image) throws ErrorServicio {

        Genero genero = null;
        try {
            genero = (Genero) generoService.buscarPorId(id).get();

            genero.setname(name);

            Path directorioimagees = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioimagees.toFile().getAbsolutePath();


            byte[] bytesImg = image.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + image.getOriginalFilename());
            Files.write(rutaCompleta, bytesImg);

            genero.setimage(image.getOriginalFilename());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return generoService.guardarGenero(genero);
    }

}
