package com.alkemy.disney.services;

import com.alkemy.disney.entities.Gender;
import com.alkemy.disney.excepciones.ErrorServicio;
import com.alkemy.disney.repositories.GenderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GenderService {

    @Autowired
    private GenderRepository genderRepository;

    public Gender saveGender(Gender gender) {
        return genderRepository.save(gender);
    }

    public List<Gender> listAllGenders() {
        return genderRepository.findAll();
    }

    public boolean deleteGender(String id) {
        try {
            genderRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public Optional findById(String id) throws ErrorServicio {

        if (genderRepository.findById(id).isPresent()) {
            return genderRepository.findById(id);
        } else {
            throw new ErrorServicio("No se ha encontrado un género con ese ID.");
        }

    }

    public void validate(String name, MultipartFile image) throws ErrorServicio {

        if (name == null || name.isEmpty()) {
            throw new ErrorServicio("El género debe tener un name.");
        }

        if (image == null || image.isEmpty()) {
            throw new ErrorServicio("El género debe tener una image.");

        }
    }
}
