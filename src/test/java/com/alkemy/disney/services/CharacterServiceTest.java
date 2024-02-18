package com.alkemy.disney.services;

import com.alkemy.disney.entities.Figure;
import com.alkemy.disney.excepciones.ServiceException;
import com.alkemy.disney.repositories.CharacterRepository;
import com.alkemy.disney.services.CharacterService;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterService characterService;

    @Test
    public void testFindAllCharacters_Success() throws ServiceException {
        // Simular el comportamiento del repositorio
        Figure figure = new Figure();
        figure.setAge(27);
        List<Figure> mockCharacters = Arrays.asList(new Figure(), figure);
        when(characterRepository.findAll()).thenReturn(mockCharacters);

        // Llamar al método del servicio y verificar el resultado
        List<Figure> result = characterService.findAllCharacters();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindAllCharacters_Exception() {
        // Simular una excepción lanzada por el repositorio
        when(characterRepository.findAll()).thenThrow(new RuntimeException("Error"));

        // Llamar al método del servicio y verificar que se lance una ServiceException
        assertThrows(ServiceException.class, () -> {
            characterService.findAllCharacters();
        });
    }

    @Test
    public void testDeleteCharacter_Success() throws ServiceException {
        // Configurar el comportamiento del repositorio para que no haga nada al llamar a deleteById
        doNothing().when(characterRepository).deleteById("1");

        // Llamar al método del servicio
        boolean result = characterService.deleteCharacter("1");

        // Verificar que el método devuelve true
        assertTrue(result);

        // Verificar que se llamó a deleteById con el ID correcto
        verify(characterRepository).deleteById("1");
    }

    @Test
    public void testDeleteCharacter_Exception() throws ServiceException {
        doThrow(new RuntimeException("Error al eliminar")).when(characterRepository).deleteById("id");

        // Llamar al método del servicio y verificar que se lance una excepción
        assertThrows(ServiceException.class, () -> {
            characterService.deleteCharacter("id");
        });

        // Verificar que se llamó a deleteById con el ID correcto
        verify(characterRepository).deleteById("id");
    }
}

