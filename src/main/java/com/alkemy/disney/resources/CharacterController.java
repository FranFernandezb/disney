package com.alkemy.disney.resources;

import com.alkemy.disney.dto.CharacterJsonResponse;
import com.alkemy.disney.entities.Figure;
import com.alkemy.disney.entities.Movie;
import com.alkemy.disney.excepciones.ServiceException;
import com.alkemy.disney.services.MovieService;
import com.alkemy.disney.services.CharacterService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alkemy.disney.utils.Logger.Object;
import com.alkemy.disney.utils.constants.Constants;
import com.alkemy.disney.utils.messages.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/characters")
public class CharacterController extends Object {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private MovieService movieService;


    /***
     * this method shows all characters
     * @return characters name and image
     */
    @GetMapping()
    public List<CharacterJsonResponse> getAllCharacters() {
        this.log.info("'/characters': STARTING OPERATION TO DISPLAY ALL CHARACTERS");
        List<Figure> charactersList;
        List<CharacterJsonResponse> characters = new ArrayList<>();
        try {
            charactersList = this.characterService.findAllCharacters();
            characters = charactersList.stream()
                    .map(character -> new CharacterJsonResponse(character.getImage(), character.getName()))
                    .collect(Collectors.toList());
            this.log.info("Response: " + mapper.writeValueAsString(characters));
        } catch (Exception e) {
            this.log.error(e.getMessage(), e.getCause());
        }
        return characters;
    }

    @GetMapping("/details")
    public List<Figure> displayCharacterDetails() {
        List<Figure> characterList = new ArrayList<>();
        try {
            characterList = this.characterService.findAllCharacters();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characterList;
    }

    @GetMapping("/name")
    public ArrayList<Figure> findByName(@RequestParam("name") String name) throws ServiceException {
        ArrayList<Figure> characterArrayList = new ArrayList<>();
        try {
            characterArrayList = this.characterService.findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characterArrayList;
    }

    @GetMapping("/age")
    public ArrayList<Figure> findByAge(@RequestParam("age") Integer age) throws ServiceException {
        ArrayList<Figure> characterArrayList = new ArrayList<>();
        try {
            characterArrayList = this.characterService.findByAge(age);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characterArrayList;
    }

    @GetMapping("/movies")
    public ArrayList<Figure> findByMovie(@RequestParam("id") String id) throws ServiceException {
        ArrayList<Figure> characters = new ArrayList<>();
        try {
            characters = this.characterService.findByMovie(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characters;
    }

    @PostMapping("/create")
    public Figure buildCharacter(@RequestParam MultipartFile image,
                                    @RequestParam String name,
                                    @RequestParam Integer age,
                                    @RequestParam Double weight,
                                    @RequestParam String history,
                                    @RequestParam String movieId) throws ServiceException {

        Figure character = new Figure();

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
        boolean ok;
        String response = null;
        try {
            ok = this.characterService.deleteCharacter(id);
            response = ok
                    ? String.format(Messages.ENTITY_DELETED_WITH_SUCCESS, Constants.CHARACTER, id)
                    : String.format(Messages.ENTITY_UNABLE_TO_BE_DELETED, Constants.CHARACTER, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @PutMapping(path = "/{id}")
    public Figure updateCharacter(@PathVariable("id") String id,
                                     @RequestParam MultipartFile image,
                                     @RequestParam String name,
                                     @RequestParam Integer age,
                                     @RequestParam Double weight,
                                     @RequestParam String history,
                                     @RequestParam String movieId) throws ServiceException {

        Figure character = new Figure();
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