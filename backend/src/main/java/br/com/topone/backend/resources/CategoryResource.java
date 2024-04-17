package br.com.topone.backend.resources;

import br.com.topone.backend.dtos.CategoryDTO;
import br.com.topone.backend.entities.Category;
import br.com.topone.backend.services.CategoryServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    
    private final CategoryServices service;  
    public CategoryResource(CategoryServices service) { /*Injeção de dependencia via construtor*/
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {
        Page<CategoryDTO> list = service.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id) {
        CategoryDTO entity = service.findById(id);
        return ResponseEntity.ok().body(entity);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
        var updatedDto = service.insert(dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(updatedDto.id())
                                .toUri())
                .body(updatedDto);
    }
    
}
