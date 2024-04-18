package br.com.topone.backend.services;

import br.com.topone.backend.dtos.CategoryDTO;
import br.com.topone.backend.entities.Category;
import br.com.topone.backend.repositories.CategoryRepository;
import br.com.topone.backend.services.exceptions.DataBaseException;
import br.com.topone.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServices {
    
    private final CategoryRepository repository;

    public CategoryServices(CategoryRepository repository) { /*Injeção via construtor nas versões mais nova não precisa usar o AutoWired*/
        this.repository = repository;
    }
    
    /**
     * Listar todas as categorias
     * */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> list = repository.findAll(pageable);
        return list.map(CategoryDTO::new);
    }
    
    /**
     * Lista caregoria por id
     * */
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Entidade não encontrada"));
        return new CategoryDTO(category);
    }
    
    /**
     * Inserir categoria
     * */
    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.name());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }
    
    /**
     * Atualizar categoria
     * */
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id: " + id + ", não encontrado"));
        BeanUtils.copyProperties(dto, category, "id");
        category = repository.save(category);
        return new CategoryDTO(category);
    }
    
    /**
     * Deletar categoria
     * */
    public void delete(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id: " + id + ", não encontrado"));
        try {
            repository.delete(category);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade de banco de dados");
        }
    } 
    
}
