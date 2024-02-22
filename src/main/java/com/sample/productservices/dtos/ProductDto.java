package com.sample.productservices.dtos;

import com.sample.productservices.models.Category;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String title;
    private Long price;
    private String desc;
    private CategoryDto categoryDto;
}
