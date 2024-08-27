package com.example.shopapp.services.impl;

import com.example.shopapp.dtos.UserDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.model.Role;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private  UserRepository userRepository;
    private  RoleRepository roleRepository;

    @Override
    public User createUser(UserDto userDto) throws DataNotFoundException {
        String phoneNumber = userDto.getPhoneNumber();
        // Kiểm tra xem số điện thoại đã tồn tại hay chưa
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        // Convert from userDto --> user
        User newUser = User.builder()
                .fullName(userDto.getFullName())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .dateOfBirth(userDto.getDateOfBirth())
                .facebookAccountId(userDto.getFacebookAccountId())
                .googleAccountId(userDto.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDto.getRoleId()).orElseThrow(
                () -> new DataNotFoundException("Role not found")
        );
        newUser.setRole(role);
        // Kiểm tra nếu có account Id , không yêu cầu password
        if(userDto.getFacebookAccountId() ==0 && userDto.getGoogleAccountId() == 0){
            //làm trong phần security
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return "";
    }
}
