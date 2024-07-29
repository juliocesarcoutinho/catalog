package br.com.topone.backend.dtos;

import br.com.topone.backend.entities.Category;
import br.com.topone.backend.entities.Role;

import java.io.Serializable;

public record CategoryDTO(Long id, String name) implements Serializable {
    
    public CategoryDTO(Category entity) {
        this(entity.getId(), entity.getName());
    }
    
}
