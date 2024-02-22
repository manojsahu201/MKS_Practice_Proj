package com.sample.productservices.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionDto {

    private String status;
    private String message;
}
