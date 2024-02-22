package com.sample.productservices.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Data
public class Category extends BaseModel {
    private String name;
}
