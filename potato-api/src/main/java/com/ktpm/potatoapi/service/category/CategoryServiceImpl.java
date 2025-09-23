package com.ktpm.potatoapi.service.category;

import com.ktpm.potatoapi.dto.request.CategoryRequest;
import com.ktpm.potatoapi.entity.Category;
import com.ktpm.potatoapi.exception.AppException;
import com.ktpm.potatoapi.exception.ErrorCode;
import com.ktpm.potatoapi.mapper.CategoryMapper;
import com.ktpm.potatoapi.dto.response.CategoryResponse;
import com.ktpm.potatoapi.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);

        try {
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        log.info("Category created: {}", category.getName());
    }

    @Override
    public void update(CategoryRequest categoryRequest, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        log.info("Category updated: {} {}", category.getId(), category.getName());
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryRepository.delete(category);
        log.info("Category deleted: {} {}", category.getId(), category.getName());
    }

    @Override
    public List<CategoryResponse> listCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
