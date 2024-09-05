package com.example.shopapp.controller;

import com.example.shopapp.dtos.UserDto;
import com.example.shopapp.dtos.UserLoginDto;
import com.example.shopapp.model.User;
import com.example.shopapp.responses.LoginResponse;
import com.example.shopapp.services.IUserService;

import com.example.shopapp.components.LocalizationUtils;
import com.example.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {


    private final IUserService userService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("/register")
    public ResponseEntity<?> createdUser(@Valid @RequestBody UserDto userDto,
                                         BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorsMessage = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorsMessage);
            }
            if (!userDto.getPassword().equals(userDto.getRetypePassword())) {
                return ResponseEntity.badRequest().body(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_MISMATCH));
            }
                User user = userService.createUser(userDto);
                return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDto userLoginDto
            ) {
        // kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.login(userLoginDto.getPhoneNumber(), userLoginDto.getPassword());
        // Trả về token trong response
              return ResponseEntity.ok(LoginResponse.builder()
                              .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                              .token(token)
                      .build());
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(LoginResponse.builder()
                           .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED,e.getMessage()))
                   .build());
        }
    }
}

