package br.com.topone.backend.services;

import br.com.topone.backend.dtos.CategoryDTO;
import br.com.topone.backend.entities.Category;
import br.com.topone.backend.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServices {
    
    private CategoryRepository repository;

    public CategoryServices(CategoryRepository repository) { /*Injeção via construtor nas versões mais nova não precisa usar o AutoWired*/
        this.repository = repository;
    }
    
    /**
     * Listar todas as categorias
     * */
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(CategoryDTO::new).toList();
    }
    
}
