package com.example.shopapp.responses;

import com.example.shopapp.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse { // làm giống bên login
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;

}
