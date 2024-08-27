package com.example.shopapp.services;

import com.example.shopapp.dtos.CategoryDto;
import com.example.shopapp.dtos.UserDto;
import com.example.shopapp.model.Category;
import com.example.shopapp.model.User;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDto categoryDto);

    Category getCategoryById(long id);

    List<Category> getAllCategories();

    Category updateCategory(long categoryId,CategoryDto categoryDto);

    void deleteCategory(long id);


}