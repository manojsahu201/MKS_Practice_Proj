package com.sample.productservices.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class FakeProductDto {
    private Long id;
    private String title;
    private Long price;
    private String description;
    private String category;

}
