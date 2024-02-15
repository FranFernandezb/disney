package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.CharacterJsonResponse;
import com.alkemy.disney.entities.Movie;
import com.alkemy.disney.entities.Character;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.services.MovieService;
import com.alkemy.disney.services.CharacterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/characters")
public class CharacterController {
    
    @Autowired
    private CharacterService characterService;
    
    @Autowired
    private MovieService MovieService;

    /***
     * this method shows all characters
     * @return characters name and image
     */
    @GetMapping()
    public List<CharacterJsonResponse> displayAllCharacters() {
        List<Character> charactersList = this.characterService.findAllCharacters();
        List<CharacterJsonResponse> characters = new ArrayList<>();

        charactersList.forEach((character) -> {
            CharacterJsonResponse characterJsonResponse = new CharacterJsonResponse(
                    character.getImage(), character.getName()
            );
            characters.add(characterJsonResponse);
        });
        return characters;
    }

    @GetMapping("/details")
    public List<Character> displayCharacterDetails() {
        return this.characterService.findAllCharacters();
    }

    @GetMapping("/name")
    public ArrayList<Character> findByName(@RequestParam("name") String name) throws ErrorServicio {
        return this.characterService.findByName(name);
    }
    
    @GetMapping("/age")
    public ArrayList<Character> findByAge(@RequestParam("age") Integer age) throws ErrorServicio {
        return this.characterService.findByAge(age);
    }
    
    @GetMapping("/movies") 
    public ArrayList<Character> findByMovie(@RequestParam("id") String id) throws ErrorServicio {
        return this.characterService.findByMovie(id);
    }
    
    @PostMapping("/create")
    public Character buildCharacter(@RequestParam MultipartFile image,
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam Double weight,
            @RequestParam String history,
            @RequestParam String movieId) throws ErrorServicio {
        
        Character character = new Character();

        this.characterService.validate(image, name, age, weight, history, movieId);

        character.setName(name);
        character.setAge(age);
        character.setWeight(weight);
        character.setHistory(history);
        Optional optional = this.MovieService.buscarMoviePorId(movieId);
        Movie movie = optional.isPresent() ? (Movie) optional.get() : null;
        character.setMovie(movie);

        if (!image.isEmpty()) {
            Path imagesDirectoryRelativePath = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String imageAbsolutePath = imagesDirectoryRelativePath.toFile().getAbsolutePath();
            
            try {
                byte[] bytesImg = image.getBytes();
                Path completePath = Paths.get(imageAbsolutePath + "//" + image.getOriginalFilename());
                Files.write(completePath, bytesImg);
                
                character.setImage(image.getOriginalFilename());
            } catch (IOException ex) {
            }
        }
        
        return this.characterService.saveCharacter(character);
    }
    
    @DeleteMapping(path = "/{id}")
    public String deleteCharacter(@PathVariable("id") String id) {
        boolean ok = this.characterService.deleteCharacter(id);
        
        if (ok) {
            return "Se eliminó el Character correctamente.";
        } else {
            return "No se pudo eliminar al Character con ese Id.";
        }
    }
    
    @PutMapping(path = "/{id}")
    public Character updateCharacter(@PathVariable("id") String id,
            @RequestParam MultipartFile image,
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam Double weight,
            @RequestParam String history,
            @RequestParam String movieId) throws ErrorServicio {
        
        Character character = this.characterService.listarCharacterPorId(id);
        character.setName(name);
        character.setAge(age);
        character.setWeight(weight);
        character.setHistory(history);
        character.setMovie((Movie) MovieService.buscarMoviePorId(movieId).get());
        
        if (!image.isEmpty()) {
            Path imageRelativePath = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String absolutePath = imageRelativePath.toFile().getAbsolutePath();
            
            try {
                byte[] bytesImg = image.getBytes();
                Path completePath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
                Files.write(completePath, bytesImg);
                
                character.setImage(image.getOriginalFilename());
            } catch (IOException ex) {
            }
        }
        
        return this.characterService.saveCharacter(character);
    }
    
}
