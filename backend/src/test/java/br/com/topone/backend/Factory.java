package br.com.topone.backend;

import br.com.topone.backend.dtos.ProductDTO;
import br.com.topone.backend.entities.Category;
import br.com.topone.backend.entities.Product;

import java.time.Instant;

public class Factory {
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "img.jpg", Instant.parse("2021-10-20T03:00:00Z"));
        product.getCategories().add(new Category(2L, "Electronics", Instant.parse("2021-10-20T03:00:00Z")));
        return product;
    }
    
    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
