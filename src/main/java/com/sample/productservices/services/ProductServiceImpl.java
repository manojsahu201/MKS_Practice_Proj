package com.sample.productservices.services;

import com.sample.productservices.dtos.FakeProductDto;
import com.sample.productservices.dtos.ProductDto;
import com.sample.productservices.exception.ProductNotFoundException;
import com.sample.productservices.models.Category;
import com.sample.productservices.models.Product;
import com.sample.productservices.thirdpartyclient.FakeStoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String FAKESTORE_BASE_URI = "https://fakestoreapi.com/products";
    //private static final String CREATE_PRODUCT = "https://fakestoreapi.com/products";

    private final FakeStoreClient fakeStoreClient;

    @Autowired
    public ProductServiceImpl(final FakeStoreClient fakeStoreClient) {
        this.fakeStoreClient = fakeStoreClient;
    }

    @Override
    public Product fetchProductThroughId(Long id) throws ProductNotFoundException {
        FakeProductDto fakeProductDto = fakeStoreClient.getProductById(id);
        if (fakeProductDto == null) {
            throw new ProductNotFoundException("No product available with id "+id);
        }
        return getProductFromFakeProductDto(fakeProductDto);
    }

    @Override
    public Product createNewProduct(ProductDto productDto) {
        FakeProductDto fakeProductDtoRequest = getFakeProductDtoFromProductDto(productDto);
        FakeProductDto fakeProductDtoResponse = fakeStoreClient.addProduct(fakeProductDtoRequest);
        return getProductFromFakeProductDto(fakeProductDtoResponse);
    }

    @Override
    public Product updateProduct(ProductDto productDto, Long id) {
        return this.getProductFromFakeProductDto(this.fakeStoreClient.updateProduct(this.getFakeProductDtoFromProductDto(productDto),id));
    }

    @Override
    public Product deleteProductById(Long id) throws ProductNotFoundException {
        return this.getProductFromFakeProductDto(this.fakeStoreClient.deleteProductById(id));
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        List<FakeProductDto> fakeProductDtos = fakeStoreClient.getAllProduct();
        fakeProductDtos.forEach(fakeProductDto ->
                productList.add(getProductFromFakeProductDto(fakeProductDto)));
        return productList;
    }

    private URI getUri(String uri) {
        return URI.create(uri);
    }

    private Product getProductFromFakeProductDto(FakeProductDto fakeProductDto) {
        Product product =  Product.builder()
                .name(fakeProductDto.getTitle())
                .title(fakeProductDto.getTitle())
                .price(fakeProductDto.getPrice())
                .description(fakeProductDto.getDescription())
                .category(Category.builder().name(fakeProductDto.getCategory()).build())
                .build();
        product.setId(fakeProductDto.getId());
        return product;
    }

    private FakeProductDto getFakeProductDtoFromProductDto(ProductDto productDto) {
        return FakeProductDto.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .description(productDto.getDesc())
                .category(productDto.getCategoryDto().getName())
                .build();
    }
}
