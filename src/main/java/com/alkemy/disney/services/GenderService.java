package com.alkemy.disney.services;

import com.alkemy.disney.entities.Gender;
import com.alkemy.disney.excepciones.ServiceException;
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
            this.genderRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public Optional findById(String id) throws ServiceException {

        if (this.genderRepository.findById(id).isPresent()) {
            return this.genderRepository.findById(id);
        } else {
            throw new ServiceException("No se ha encontrado un género con ese ID.");
        }
    }

    public void validate(String name, MultipartFile image) throws ServiceException {

        if (name == null || name.isEmpty()) {
            throw new ServiceException("El género debe tener un name.");
        }

        if (image == null || image.isEmpty()) {
            throw new ServiceException("El género debe tener una image.");

        }
    }
}
