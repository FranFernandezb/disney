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
    private MovieService movieService;

    /***
     * this method shows all characters
     * @return characters name and image
     */
    @GetMapping()
    public List<CharacterJsonResponse> displayAllCharacters() {
        List<Character> charactersList;
        List<CharacterJsonResponse> characters = new ArrayList<>();
        try {
            charactersList = this.characterService.findAllCharacters();

            charactersList.forEach((character) -> {
                CharacterJsonResponse characterJsonResponse = new CharacterJsonResponse(
                        character.getImage(), character.getName()
                );
                characters.add(characterJsonResponse);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characters;
    }

    @GetMapping("/details")
    public List<Character> displayCharacterDetails() {
        List<Character> characterList = new ArrayList<>();
        try {
            characterList = this.characterService.findAllCharacters();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characterList;
    }

    @GetMapping("/name")
    public ArrayList<Character> findByName(@RequestParam("name") String name) throws ErrorServicio {
        ArrayList<Character> characterArrayList = new ArrayList<>();
        try {
            characterArrayList = this.characterService.findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characterArrayList;
    }

    @GetMapping("/age")
    public ArrayList<Character> findByAge(@RequestParam("age") Integer age) throws ErrorServicio {
        ArrayList<Character> characterArrayList = new ArrayList<>();
        try {
            characterArrayList = this.characterService.findByAge(age);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characterArrayList;
    }

    @GetMapping("/movies")
    public ArrayList<Character> findByMovie(@RequestParam("id") String id) throws ErrorServicio {
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = this.characterService.findByMovie(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characters;
    }

    @PostMapping("/create")
    public Character buildCharacter(@RequestParam MultipartFile image,
                                    @RequestParam String name,
                                    @RequestParam Integer age,
                                    @RequestParam Double weight,
                                    @RequestParam String history,
                                    @RequestParam String movieId) throws ErrorServicio {

        Character character = new Character();

        try {
            this.characterService.validate(image, name, age, weight, history, movieId);

            character.setName(name);
            character.setAge(age);
            character.setWeight(weight);
            character.setHistory(history);
            Optional optional = this.movieService.findMovieById(movieId);
            Movie movie = optional.isPresent() ? (Movie) optional.get() : null;
            character.setMovie(movie);

            if (!image.isEmpty()) {
                Path imagesDirectoryRelativePath = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
                String imageAbsolutePath = imagesDirectoryRelativePath.toFile().getAbsolutePath();
                byte[] bytesImg = image.getBytes();
                Path completePath = Paths.get(imageAbsolutePath + "//" + image.getOriginalFilename());
                Files.write(completePath, bytesImg);
                character.setImage(image.getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.characterService.saveCharacter(character);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteCharacter(@PathVariable("id") String id) {
        boolean ok = Boolean.TRUE;
        String response = null;
        try {
            ok = this.characterService.deleteCharacter(id);
            response = ok ? "Se eliminó el Character correctamente." : "No se pudo eliminar al Character con ese Id.";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @PutMapping(path = "/{id}")
    public Character updateCharacter(@PathVariable("id") String id,
                                     @RequestParam MultipartFile image,
                                     @RequestParam String name,
                                     @RequestParam Integer age,
                                     @RequestParam Double weight,
                                     @RequestParam String history,
                                     @RequestParam String movieId) throws ErrorServicio {

        Character character = new Character();
        try {
            character = this.characterService.findCharacterById(id);
            character.setName(name);
            character.setAge(age);
            character.setWeight(weight);
            character.setHistory(history);
            character.setMovie((Movie) this.movieService.findMovieById(movieId).get());

            if (!image.isEmpty()) {
                Path imageRelativePath = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
                String absolutePath = imageRelativePath.toFile().getAbsolutePath();

                byte[] bytesImg = image.getBytes();
                Path completePath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
                Files.write(completePath, bytesImg);

                character.setImage(image.getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.characterService.saveCharacter(character);
    }
}
