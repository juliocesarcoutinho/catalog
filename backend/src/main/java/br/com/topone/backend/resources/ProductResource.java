package br.com.topone.backend.resources;

import br.com.topone.backend.dtos.ProductDTO;
import br.com.topone.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    public final ProductService productService;
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }
        
    //Metodo sem o Pageable
//    @GetMapping
//    public ResponseEntity<Page<ProductDTO>> findAll(
//            @RequestParam(value = "page", defaultValue = "0") Integer page,
//            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
//            @RequestParam(value = "direction", defaultValue = "ASC") String direction,          
//            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
//            
//    ) {
//        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
//        
//        Page<ProductDTO> list = productService.findAllPaged(pageRequest);
//        return ResponseEntity.ok().body(list);
//    }

    //Metodo usando o Pageable
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> list = productService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id) {
        ProductDTO dto = productService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

//    @PostMapping
//    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
//        dto = productService.insert(dto);
//
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(dto.getId()).toUri(); 
//        
//        return ResponseEntity.ok().body(dto);
//    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        var updatedDto = productService.insert(dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(updatedDto.id())
                                .toUri())
                .body(updatedDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") Long id,
                                             @RequestBody ProductDTO dto) {
        dto = productService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
