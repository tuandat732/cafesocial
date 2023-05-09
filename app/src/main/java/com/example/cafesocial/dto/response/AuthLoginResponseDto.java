package com.example.cafesocial.dto.response;

import com.example.cafesocial.core.model.ResponseData;
import com.example.cafesocial.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuthLoginResponseDto {
    private User user;
    private String token;
}
