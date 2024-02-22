package com.sample.productservices.thirdpartyclient;

import com.sample.productservices.dtos.FakeProductDto;
import com.sample.productservices.dtos.ProductDto;
import com.sample.productservices.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreClient {

    private String specificUrl = "https://fakestoreapi.com/products/{id}";
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${fakestore.api.baseurl}")
    private String baseUrl;

    @Autowired
    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeProductDto getProductById(Long id) throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeProductDto> fakeProductDtoResponse = restTemplate.getForEntity(baseUrl+"/{id}", FakeProductDto.class, id);
        if (fakeProductDtoResponse.getBody() == null) {
            throw new ProductNotFoundException("No product available with id "+id);
        }
        return fakeProductDtoResponse.getBody();
    }

    public FakeProductDto deleteProductById(Long id) throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeProductDto.class);
        ResponseExtractor<ResponseEntity<FakeProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeProductDto.class);

        ResponseEntity<FakeProductDto>  responseEntity = restTemplate.execute(specificUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);
        if (responseEntity == null) {
            throw new ProductNotFoundException("No product is there with id "+id+" to delete.");
        }
        return responseEntity.getBody();
    }

    public FakeProductDto updateProduct(FakeProductDto fakeProductDto, Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeProductDto,FakeProductDto.class);
        ResponseExtractor<ResponseEntity<FakeProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeProductDto.class);
        ResponseEntity<FakeProductDto>  responseEntity = restTemplate.execute(specificUrl, HttpMethod.PUT, requestCallback, responseExtractor, id);
        return responseEntity.getBody();
    }

    public List<FakeProductDto> getAllProduct() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeProductDto[]> response = restTemplate.getForEntity(baseUrl, FakeProductDto[].class);
        if (response.getBody() != null){
            return Arrays.asList(response.getBody());
        }
        return new ArrayList<FakeProductDto>();
    }

    public FakeProductDto addProduct(FakeProductDto fakeProductDto) {
        return restTemplateBuilder.build().postForObject(URI.create(baseUrl),fakeProductDto, FakeProductDto.class);
    }

}
