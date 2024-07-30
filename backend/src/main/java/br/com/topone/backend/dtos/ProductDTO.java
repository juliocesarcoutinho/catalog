package br.com.topone.backend.dtos;

import br.com.topone.backend.entities.Category;
import br.com.topone.backend.entities.Product;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public record ProductDTO(
        Long id,

        @NotBlank(message = "Name is required")
        @Size(min = 5, max = 160, message = "Name must be between 3 and 160 characters")
        String name,
        
        @Positive(message = "Price must be positive")
        Double price,
        String description,
        String imgUrl,
        @PastOrPresent(message = "The date cannot be in the future")
        Instant date,
        Set<CategoryDTO> categories
) implements Serializable {

    public ProductDTO(Product entity) {
        this(entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getImgUrl(),
                entity.getDate(),
                new HashSet<>());
    }

    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity);
        categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
    }
}
