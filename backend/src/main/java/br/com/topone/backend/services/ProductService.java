package br.com.topone.backend.services;

import br.com.topone.backend.dtos.CategoryDTO;
import br.com.topone.backend.dtos.ProductDTO;
import br.com.topone.backend.entities.Category;
import br.com.topone.backend.entities.Product;
import br.com.topone.backend.repositories.CategoryRepository;
import br.com.topone.backend.repositories.ProductRepository;
import br.com.topone.backend.services.exceptions.DataBaseException;
import br.com.topone.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    public final CategoryRepository categoryRepository;
    public final ProductRepository repository;
    public ProductService(CategoryRepository categoryRepository, ProductRepository repository) {
        this.categoryRepository = categoryRepository;
        this.repository = repository;
    }
    //    @Transactional(readOnly = true) // Menos eficiente
//    public List<ProductDTO> findAll() {
//       List<Product> list = ProductRepository.findAll();
//       
//       List<ProductDTO> listDTO = new ArrayList<>();
//       for (Product Product : list) {
//           listDTO.add(new ProductDTO(Product));
//       }
//       return listDTO;
//    } /*Mais verbosa e menos eficiente*/Método antigo
    @Transactional(readOnly = true) /*Mais concisa e mais eficiente*/
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not Found " + id));
        product.getCategories().clear();
        copyDtoToEntity(dto, product);
        product = repository.save(product);
        return new ProductDTO(product);
    }

    public void delete(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not Found " + id));
        try {
            repository.delete(product);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    /*Meotod mais eficiente*/
    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        BeanUtils.copyProperties(dto, entity, "id", "categories");
        entity.getCategories().clear();
        for (CategoryDTO catDto : dto.categories()) {
            Category category = categoryRepository.findById(catDto.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + catDto.id()));
            entity.getCategories().add(category);
        }
    }

//    private void copyDtoToEntity(ProductDTO dto, Product entity) {
//        entity.setName(dto.getName());
//        entity.setPrice(dto.getPrice());
//        entity.setImgUrl(dto.getImgUrl());
//        entity.setDescription(dto.getDescription());
//        entity.setDate(dto.getDate());
//        entity.getCategories().clear();
//        for (CategoryDTO catDto : dto.getCategories()) {
//            Category category = new Category();
//            category.setId(catDto.getId());
//            entity.getCategories().add(category);
//        }
//    } /*Menos eficiente*/
    
}
