package com.sample.productservices.controllers;

import com.sample.productservices.dtos.ExceptionDto;
import com.sample.productservices.dtos.ProductDto;
import com.sample.productservices.exception.ProductNotFoundException;
import com.sample.productservices.models.Product;
import com.sample.productservices.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        //return "This product is having id : "+id;
        return this.productService.fetchProductThroughId(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }

    @PostMapping("/create")
    public Product createProduct(@Valid @RequestBody ProductDto productDto) {
        return this.productService.createNewProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public Product deleteProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        return this.productService.deleteProductById(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable("id") Long id) {
        return this.productService.updateProduct(productDto, id);
    }
}
