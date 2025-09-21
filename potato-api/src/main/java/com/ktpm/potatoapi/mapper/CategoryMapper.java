package com.ktpm.potatoapi.mapper;

import com.ktpm.potatoapi.entity.Category;
import com.ktpm.potatoapi.payload.request.CreateCategoryRequest;
import com.ktpm.potatoapi.payload.request.UpdateCategoryRequest;
import com.ktpm.potatoapi.payload.response.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // Map UpdateCategoryRequest -> Category
    Category updateToEntity(UpdateCategoryRequest updateCategoryRequest);

    // Map CreateCategoryRequest -> Category
    Category toEntity(CreateCategoryRequest createCategoryRequest);

    // Map List<Category> -> List<CategoryResponse>
    List<CategoryResponse> toDtoList(List<Category> categoryList);
}
