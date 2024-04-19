package br.com.topone.backend.dtos;

import br.com.topone.backend.entities.Category;
import br.com.topone.backend.entities.Product;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
public record ProductDTO(
    Long id,
    String name,
    Double price,
    String description,
    String imgUrl,
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
