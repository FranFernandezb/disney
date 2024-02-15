package com.alkemy.disney.services;

import com.alkemy.disney.entities.Character;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.repositories.CharacterRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public List<Character> findAllCharacters() {
        return this.characterRepository.findAll();
    }

    public boolean deleteCharacter(String id) {
        try {
            this.characterRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public Character saveCharacter(Character Character) {
        return this.characterRepository.save(Character);
    }

    public Character findCharacterById(String id) throws ErrorServicio {

        if (this.characterRepository.findById(id).isPresent()) {
            return this.characterRepository.findById(id).get();
        } else {
            throw new ErrorServicio("No se ha encontrado ningún Character con ese ID.");
        }

    }

    public ArrayList<Character> findByName(String name) throws ErrorServicio {

        if (this.characterRepository.findByName(name).isEmpty()) {
            throw new ErrorServicio("No se ha encontrado un Character con ese name.");
        }

        return this.characterRepository.findByName(name);
    }

    public ArrayList<Character> findByAge(Integer age) throws ErrorServicio {

        if (this.characterRepository.findByage(age).isEmpty()) {
            throw new ErrorServicio("No se ha encontrado un Character con esa age. ");
        }

        return this.characterRepository.findByage(age);
    }

    public ArrayList<Character> findByMovie(String id) throws ErrorServicio {

        if (this.characterRepository.findByMovie(id).isEmpty()) {
            throw new ErrorServicio("No se ha encontrado un Character para esa película.");
        }

        return this.characterRepository.findByMovie(id);
    }

    public void validate(MultipartFile image, String name, Integer age, Double weight, String history, String movieId) throws ErrorServicio {

        if (image == null || image.isEmpty()) {
            throw new ErrorServicio("El Character debe tener una image asociada.");
        }

        if (name == null || name.isEmpty()) {
            throw new ErrorServicio("El Character debe tener un name");
        }

        if (age == null || age < 0) {
            throw new ErrorServicio("El Character debe tener una age y debe ser mayor a 0.");
        }

        if (weight == null || weight < 0) {
            throw new ErrorServicio("El Character debe tener un weight y debe ser mayor a 0.");
        }

        if (history == null || history.isEmpty()) {
            throw new ErrorServicio("El Character debe tener una history.");
        }

        if (movieId == null || movieId.isEmpty()) {
            throw new ErrorServicio("El Character debe tener una película asociada.");
        }
    }
}
