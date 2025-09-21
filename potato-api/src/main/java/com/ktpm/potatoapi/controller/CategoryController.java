package com.ktpm.potatoapi.controller;

import com.ktpm.potatoapi.payload.request.CreateCategoryRequest;
import com.ktpm.potatoapi.payload.request.UpdateCategoryRequest;
import com.ktpm.potatoapi.payload.response.ApiResponse;
import com.ktpm.potatoapi.payload.response.CategoryResponse;
import com.ktpm.potatoapi.service.CategoryService;
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
    public ResponseEntity<?> listCategory(){
        ApiResponse<List<CategoryResponse>> response = ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.listCategory())
                .message("List category successful")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    @Operation(summary = "Update category by id", description = "API for all ADMIN to update category")
    public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest) {
        categoryService.update(updateCategoryRequest);
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
