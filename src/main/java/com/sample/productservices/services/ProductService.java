package com.sample.productservices.services;

import com.sample.productservices.dtos.ProductDto;
import com.sample.productservices.exception.ProductNotFoundException;
import com.sample.productservices.models.Product;

import java.util.List;

public interface ProductService {
    Product fetchProductThroughId(Long id) throws ProductNotFoundException;

    Product createNewProduct(ProductDto productDto);

    Product updateProduct(ProductDto productDto, Long id);

    Product deleteProductById(Long id) throws ProductNotFoundException;

    List<Product> getAllProduct();
}
