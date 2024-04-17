package br.com.topone.backend.dtos;

import br.com.topone.backend.entities.Category;

public record CategoryDTO(Long id, String name) {
    
    public CategoryDTO(Category entity) {
        this(entity.getId(), entity.getName());
    }
    
}
