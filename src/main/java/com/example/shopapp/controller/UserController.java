package com.example.shopapp.controller;

import com.example.shopapp.dtos.UserDto;
import com.example.shopapp.dtos.UserLoginDto;
import com.example.shopapp.dtos.UserRes;
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
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@CrossOrigin("*")
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
    public UserRes login(
            @Valid @RequestBody UserLoginDto userLoginDto
            ) throws Exception {
       return this.userService.login(userLoginDto.getPhoneNumber(),userLoginDto.getPassword());
    }
}

