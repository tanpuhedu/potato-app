package com.ktpm.potatoapi.service.category;

import com.ktpm.potatoapi.dto.request.CategoryRequest;
import com.ktpm.potatoapi.exception.AppException;
import com.ktpm.potatoapi.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    /**
     * Creates a new category based on the given request.
     *
     * @param categoryRequest the request containing the details
     *                              of the category to be created.
     * @throws AppException if the request data is invalid.
     */
    void createCategory(CategoryRequest categoryRequest);

    /**
     * Updates an existing category identified by the given ID.
     *
     * @param categoryRequest the request containing the updated
     *                              category details.
     * @param id the ID of the category to update.
     * @throws AppException if the request data is invalid and if no category exists with the given ID.
     */
    void update(CategoryRequest categoryRequest, Long id);

    /**
     * Deletes the category identified by the given ID.
     *
     * @param id the ID of the category to delete.
     * @throws AppException if no category exists with the given ID.
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
