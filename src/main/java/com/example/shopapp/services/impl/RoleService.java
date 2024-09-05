package com.example.shopapp.services.impl;

import com.example.shopapp.model.Role;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
