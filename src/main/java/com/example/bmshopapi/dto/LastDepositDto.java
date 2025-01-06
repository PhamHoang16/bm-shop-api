package com.example.bmshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LastDepositDto {
    private String username;
    private String detail;
    private String time;
}
