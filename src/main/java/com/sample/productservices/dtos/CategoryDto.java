package com.sample.productservices.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class CategoryDto {
    private Long id;
    private String name;
}
