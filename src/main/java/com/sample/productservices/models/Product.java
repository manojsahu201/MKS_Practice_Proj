package com.sample.productservices.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Data
public class Product extends BaseModel {
    private String name;
    private String title;
    private Long price;
    private String description;
    private Category category;
}
