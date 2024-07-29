package br.com.topone.backend.repositories;

import br.com.topone.backend.Factory;
import br.com.topone.backend.entities.Product;
import br.com.topone.backend.services.ProductService;
import br.com.topone.backend.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DataJpaTest /*Carrega somente a parte do jpa sem carregar contexto*/
@Disabled
public class ProductRepositoryTest {
    
    private long existingId;
    private long nonExistingId;
    
    private long countTotalProducts;

    @Autowired
    private ProductRepository repository;
    
    @BeforeEach
    public void setup() {
        existingId = 1000L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }


    @Test
    public void deleteShoudDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);
        System.out.println("Deletado com sucesso o id: " + existingId);

        //Act
        repository.existsById(existingId);
        //Assert
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());

    }
    
    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }
    
    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);
        product = repository.save(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

}
