package com.example.shopapp.controller;

import com.example.shopapp.dtos.UserDto;
import com.example.shopapp.dtos.UserLoginDto;
import com.example.shopapp.services.IUserService;
import com.example.shopapp.services.impl.UserService;
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

    @PostMapping("/register")
    public ResponseEntity<?> createdUser(@Valid  @RequestBody UserDto userDto,
                                         BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
            List<String> errorsMessage = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
            if(!userDto.getPassword().equals(userDto.getRetypePassword())){
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
            userService.createUser(userDto);
            return ResponseEntity.ok().body("Registered Successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto){
        // kiểm tra thông tin đăng nhập và sinh token
        // Trả về token trong response
        String token = userService.login(userLoginDto.getPhoneNumber(), userLoginDto.getPassword());

        return ResponseEntity.ok().body(token);
    }
}
