package com.example.shopapp.services;

import com.example.shopapp.dtos.UserDto;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.model.User;

public interface IUserService {
        User createUser(UserDto userDto) throws DataNotFoundException;
        String login(String phoneNumber, String password) throws Exception;

}
