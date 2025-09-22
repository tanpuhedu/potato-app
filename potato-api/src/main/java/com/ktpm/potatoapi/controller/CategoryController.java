package com.ktpm.potatoapi.controller;

import com.ktpm.potatoapi.payload.request.CreateCategoryRequest;
import com.ktpm.potatoapi.payload.response.ApiResponse;
import com.ktpm.potatoapi.payload.response.CategoryResponse;
import com.ktpm.potatoapi.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
 * This controller exposes endpoints under {@code /api/categories} for:
 * <ul>
 *     <li>Create a new category (ADMIN only)</li>
 *     <li>Retrieve a list of all categories (accessible by all users)</li>
 *     <li>Update an existing category by ID (ADMIN only)</li>
 *     <li>Delete a category by ID (ADMIN only)</li>
 * </ul>
 *
 * It delegates category-related business logic to the {@link CategoryService}.
 * @author Hieu
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category APIs", description = "APIs for category")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "*")
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create a new category", description = "API for role ADMIN to create a new category")
    public ResponseEntity<?> createLocation(@RequestBody @Valid CreateCategoryRequest createLocationRequest, HttpServletRequest requestHttp) {
        categoryService.createCategory(createLocationRequest);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Create category successful")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Show all categories in system", description = "API for all user")
    public ResponseEntity<?> listCategory() {
        ApiResponse<List<CategoryResponse>> response = ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.listCategory())
                .message("List category successful")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category by id", description = "API for all ADMIN to update category")
    public ResponseEntity<?> updateCategory(@RequestBody CreateCategoryRequest createCategoryRequest, @PathVariable Long id) {
        categoryService.update(createCategoryRequest, id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Update category successful")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id", description = "API for all ADMIN to delete category")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Delete category successful")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
