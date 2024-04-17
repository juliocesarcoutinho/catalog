package br.com.topone.backend.resources;

import br.com.topone.backend.entities.Category;
import br.com.topone.backend.services.CategoryServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    
    private CategoryServices service;   
    public CategoryResource(CategoryServices service) { /*Injeção de dependencia via construtor*/
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    
}
