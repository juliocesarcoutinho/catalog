package br.com.topone.backend.repositories;

import br.com.topone.backend.entities.Product;
import br.com.topone.backend.services.ProductService;
import br.com.topone.backend.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DataJpaTest /*Carrega somente a parte do jpa sem carregar contexto*/
public class ProductRepositoryTest {


    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void deleteShoudDeleteObjectWhenIdExists() {
        //Arrange
        long existingId = 1L;
        
        repository.deleteById(existingId);
        System.out.println("Deletado com sucesso o id: " + existingId);
        
        //Act
        repository.existsById(existingId);        
        //Assert
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
        
    }


    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Long nonExistingId = 1000L;

        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
    }
    
}
