package com.ktpm.potatoapi.service;

import com.ktpm.potatoapi.payload.request.CreateCategoryRequest;
import com.ktpm.potatoapi.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void createCategory(CreateCategoryRequest createCategoryRequest);

    void update(CreateCategoryRequest createCategoryRequest, Long id);

    void delete(Long id);

    List<CategoryResponse> listCategory();
}
