package com.ktpm.potatoapi.service.category;

import com.ktpm.potatoapi.exception.LogicCustomException;
import com.ktpm.potatoapi.payload.request.CreateCategoryRequest;
import com.ktpm.potatoapi.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    /**
     * Creates a new category based on the given request.
     *
     * @param createCategoryRequest the request containing the details
     *                              of the category to be created.
     * @throws LogicCustomException if the request data is invalid.
     */
    void createCategory(CreateCategoryRequest createCategoryRequest);

    /**
     * Updates an existing category identified by the given ID.
     *
     * @param createCategoryRequest the request containing the updated
     *                              category details.
     * @param id the ID of the category to update.
     * @throws LogicCustomException if the request data is invalid and if no category exists with the given ID.
     */
    void update(CreateCategoryRequest createCategoryRequest, Long id);

    /**
     * Deletes the category identified by the given ID.
     *
     * @param id the ID of the category to delete.
     * @throws LogicCustomException if no category exists with the given ID.
     */
    void delete(Long id);

    /**
     * Retrieves all available categories.
     *
     * @return a list of category response objects containing
     *         the details of each category.
     */
    List<CategoryResponse> listCategory();
}
