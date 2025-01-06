package com.example.bmshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderListDto {
    private String username;
    private String detail;
    private String time;
}
