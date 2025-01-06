package com.example.bmshopapi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomErrorResponse {
    private String errorCode;
    private String message;
}
