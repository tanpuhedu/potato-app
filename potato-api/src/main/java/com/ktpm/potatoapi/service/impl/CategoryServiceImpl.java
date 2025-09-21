package com.ktpm.potatoapi.service.impl;

import com.ktpm.potatoapi.entity.Category;
import com.ktpm.potatoapi.exception.LogicCustomException;
import com.ktpm.potatoapi.mapper.CategoryMapper;
import com.ktpm.potatoapi.payload.request.CreateCategoryRequest;
import com.ktpm.potatoapi.payload.request.UpdateCategoryRequest;
import com.ktpm.potatoapi.payload.response.CategoryResponse;
import com.ktpm.potatoapi.repository.CategoryRepository;
import com.ktpm.potatoapi.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    @Override
    public void createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = categoryMapper.toEntity(createCategoryRequest);
        categoryRepository.save(category);
        log.info("Category created: {}", category.getName());
    }

    @Override
    public void update(UpdateCategoryRequest updateCategoryRequest) {
        Optional<Category> categoryOptional = categoryRepository.findById(updateCategoryRequest.getId());
        if(categoryOptional.isEmpty()) {
            log.error("Category id does not exists: {}", updateCategoryRequest.getId());
            LogicCustomException logicCustomException = new LogicCustomException();
            logicCustomException.setMessage("Category does not exists");
            logicCustomException.setCode(404);
            throw logicCustomException;
        }
        Category category = categoryMapper.updateToEntity(updateCategoryRequest);
        categoryRepository.save(category);
        log.info("Category updated: {} {}", category.getId(), category.getName());
    }

    @Override
    public void delete(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()) {
            log.error("Category id does not exists: {}", id);
            LogicCustomException logicCustomException = new LogicCustomException();
            logicCustomException.setMessage("Category does not exists");
            logicCustomException.setCode(404);
            throw logicCustomException;
        }
        Category category = categoryOptional.get();
        categoryRepository.delete(category);
        log.info("Category deleted: {} {}", category.getId(), category.getName());
    }

    @Override
    public List<CategoryResponse> listCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        log.info("List category successful");
        return categoryMapper.toDtoList(categoryList);
    }
}
