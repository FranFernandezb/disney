package com.alkemy.disney.resources;

import com.alkemy.disney.entities.Gender;
import com.alkemy.disney.excepciones.ServiceException;
import com.alkemy.disney.services.GenderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.alkemy.disney.utils.constants.Constants;
import com.alkemy.disney.utils.messages.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gender")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @GetMapping()
    public List<Gender> listAllGenders() {
        return this.genderService.listAllGenders();
    }

    @PostMapping("/create")
    public Gender createGender(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) throws ServiceException {

        Gender gender = new Gender();
        try {
            this.genderService.validate(name, image);
            gender.setName(name);
            if (!image.isEmpty()) {
                Path imageRelativePath = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
                String absolutePath = imageRelativePath.toFile().getAbsolutePath();

                byte[] bytesImg = image.getBytes();
                Path completePath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
                Files.write(completePath, bytesImg);

                gender.setImage(image.getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.genderService.saveGender(gender);
    }

    @DeleteMapping(path = "/{id}")
    public String delete(@PathVariable("id") String id) {
        String response = null;
        try {
            boolean ok = this.genderService.deleteGender(id);
            response = ok
                    ? String.format(Messages.ENTITY_DELETED_WITH_SUCCESS, Constants.GENDER, id)
                    : String.format(Messages.ENTITY_UNABLE_TO_BE_DELETED, Constants.GENDER, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @PutMapping(path = "/{id}")
    public Gender updateGender(@PathVariable("id") String id, @RequestParam String name, @RequestParam MultipartFile image) throws ServiceException {

        Gender gender = null;
        try {
            gender = (Gender) this.genderService.findById(id).get();

            gender.setName(name);
            Path directorioimagees = Paths.get("src//main//resources//static/images"); //RUTA RELATIVA HACIA EL FOLDER IMAGES DE RECURSOS ESTATICOS
            String rutaAbsoluta = directorioimagees.toFile().getAbsolutePath();

            byte[] bytesImg = image.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + image.getOriginalFilename());
            Files.write(rutaCompleta, bytesImg);

            gender.setImage(image.getOriginalFilename());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.genderService.saveGender(gender);
    }
}
