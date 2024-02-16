package com.alkemy.disney.services;

import com.alkemy.disney.entities.Figure;
import com.alkemy.disney.excepciones.ServiceException;
import com.alkemy.disney.repositories.CharacterRepository;

import java.util.ArrayList;
import java.util.List;

import com.alkemy.disney.utils.Logger.Object;
import com.alkemy.disney.utils.constants.Constants;
import com.alkemy.disney.utils.messages.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CharacterService extends Object {

    @Autowired
    private CharacterRepository characterRepository;

    public List<Figure> findAllCharacters() throws ServiceException {
        List<Figure> characterList;
        try {
            this.log.info(String.format(Messages.FINDING_ALL_ENTITIES, Constants.CHARACTER));
            characterList = this.characterRepository.findAll();
        } catch (Exception e) {
            this.log.error("");
            throw new ServiceException(
                    String.format(Messages.EXCEPTION_FINDING_ALL_ENTITIES, Constants.CHARACTER)
            );
        }
        return characterList;
    }

    public boolean deleteCharacter(String id) throws ServiceException {
        try {
            this.characterRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            throw new ServiceException(
                    String.format(Messages.EXCEPTION_FINDING_ALL_ENTITIES, Constants.CHARACTER)
            );
        }
    }

    public Figure saveCharacter(Figure figure) {
        return this.characterRepository.save(figure);
    }

    public Figure findCharacterById(String id) throws ServiceException {

        if (this.characterRepository.findById(id).isPresent()) {
            return this.characterRepository.findById(id).get();
        } else {
            throw new ServiceException(
                    String.format(Messages.THERE_IS_NO_ENTITY_WITH_THAT_ATTRIBUTE, Constants.CHARACTER, Constants.ID, id)
            );
        }

    }

    public ArrayList<Figure> findByName(String name) throws ServiceException {

        if (this.characterRepository.findByName(name).isEmpty()) {
            throw new ServiceException(
                    String.format(Messages.THERE_IS_NO_ENTITY_WITH_THAT_ATTRIBUTE, Constants.CHARACTER, Constants.NAME, name)
            );
        }
        return this.characterRepository.findByName(name);
    }

    public ArrayList<Figure> findByAge(Integer age) throws ServiceException {

        if (this.characterRepository.findByage(age).isEmpty()) {
            throw new ServiceException(String.format(Messages.THERE_IS_NO_ENTITY_WITH_THAT_ATTRIBUTE, Constants.CHARACTER, Constants.AGE, age.toString()));
        }
        return this.characterRepository.findByage(age);
    }

    public ArrayList<Figure> findByMovie(String id) throws ServiceException {

        if (this.characterRepository.findByMovie(id).isEmpty()) {
            throw new ServiceException(String.format(Messages.THERE_IS_NO_ENTITY_WITH_THAT_ATTRIBUTE, Constants.CHARACTER, Constants.MOVIE, id));
        }
        return this.characterRepository.findByMovie(id);
    }

    public void validate(MultipartFile image, String name, Integer age, Double weight, String history, String movieId) throws ServiceException {

        if (image == null || image.isEmpty()) {
            throw new ServiceException(Messages.CHARACTER_HAS_TO_HAVE_AN_IMAGE);
        }

        if (name == null || name.isEmpty()) {
            throw new ServiceException(Messages.CHARACTER_HAS_TO_HAVE_A_NAME);
        }

        if (age == null || age < 0) {
            throw new ServiceException(Messages.CHARACTER_HAS_TO_BE_MORE_THAN_ZERO);
        }

        if (weight == null || weight < 0) {
            throw new ServiceException(Messages.CHARACTER_HAS_TO_HAVE_A_WEIGHT);
        }

        if (history == null || history.isEmpty()) {
            throw new ServiceException(Messages.CHARACTER_HAS_TO_HAVE_A_HISTORY);
        }

        if (movieId == null || movieId.isEmpty()) {
            throw new ServiceException(Messages.CHARACTER_HAS_TO_HAVE_A_RELATED_MOVIE);
        }
    }
}
