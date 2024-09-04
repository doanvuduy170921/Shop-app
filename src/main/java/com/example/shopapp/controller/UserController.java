package com.example.shopapp.controller;

import com.example.shopapp.dtos.UserDto;
import com.example.shopapp.dtos.UserLoginDto;
import com.example.shopapp.model.User;
import com.example.shopapp.responses.JwtRes;
import com.example.shopapp.responses.LoginResponse;
import com.example.shopapp.services.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;


import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final MessageSource messageSource;
    private final IUserService userService;
    private final LocaleResolver localeResolver;

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
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
                User user = userService.createUser(userDto);
                return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDto userLoginDto,
            HttpServletRequest request) {
        // kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.login(userLoginDto.getPhoneNumber(), userLoginDto.getPassword());
            Locale locale = localeResolver.resolveLocale(request);
        // Trả về token trong response
              return ResponseEntity.ok(LoginResponse.builder()
                              .message(messageSource.getMessage("user.login.login_successfully",null,locale))
                              .token(token)
                      .build());
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(LoginResponse.builder()
                           .message(e.getMessage())
                   .build());
        }
    }
}

