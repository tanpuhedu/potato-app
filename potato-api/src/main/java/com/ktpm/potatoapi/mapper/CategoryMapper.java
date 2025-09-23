package com.ktpm.potatoapi.mapper;

import com.ktpm.potatoapi.dto.request.CategoryRequest;
import com.ktpm.potatoapi.entity.Category;
import com.ktpm.potatoapi.dto.response.CategoryResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest categoryRequest);
    CategoryResponse toResponse(Category entity);
}
