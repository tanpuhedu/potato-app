package com.ktpm.potatoapi.controller;

import com.ktpm.potatoapi.dto.request.CategoryRequest;
import com.ktpm.potatoapi.dto.response.ApiResponse;
import com.ktpm.potatoapi.dto.response.CategoryResponse;
import com.ktpm.potatoapi.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that provides APIs for managing categories.
 * <p>
 * This controller exposes endpoints under {@code /merchant/categories} for:
 * <ul>
 *     <li>Create a new category (Merchant Admin only)</li>
 *     <li>Retrieve a list of all categories (Merchant Admin only)</li>
 *     <li>Update an existing category by ID (Merchant Admin only)</li>
 *     <li>Delete a category by ID (Merchant Admin only)</li>
 * </ul>
 *
 * It delegates category-related business logic to the {@link CategoryService}.
 * @author Hieu, Thanh
 */
@RestController
@RequestMapping("/merchant/categories")
@RequiredArgsConstructor
@Tag(name = "Category APIs", description = "APIs for category")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "*")
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create a new category",
            description = "API for Merchant Admin to create a new category")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Create category successful")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Show all categories in system",
            description = "API for Merchant Admin to retrieve a list of all categories")
    public ResponseEntity<?> listCategory() {
        ApiResponse<List<CategoryResponse>> response = ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.listCategory())
                .message("List category successful")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category by id", description = "API for Merchant Admin to update category")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        categoryService.update(categoryRequest, id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Update category successful")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id", description = "API for Merchant Admin to delete category")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Delete category successful")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
