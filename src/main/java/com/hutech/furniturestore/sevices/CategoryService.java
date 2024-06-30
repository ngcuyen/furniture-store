package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.constants.CategoryResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.CategoryDto;
import com.hutech.furniturestore.exceptions.NoSuchElementFoundException;
import com.hutech.furniturestore.models.Category;
import com.hutech.furniturestore.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryDto categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setIsRemoved(categoryRequest.getIsRemoved());

        Category savedCategory= categoryRepository.save(category);
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(savedCategory.getId());
        categoryResponse.setName(savedCategory.getName());
        categoryResponse.setDescription(savedCategory.getDescription());
        return categoryResponse;
    }


    public CategoryResponse getCategoryById(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent() && !categoryOpt.get().getIsRemoved()) {
            Category category = categoryOpt.get();
            CategoryResponse categoryResponse = new CategoryResponse();
            category.setId(category.getId());
            category.setName(category.getName());
            category.setDescription(category.getDescription());

            return categoryResponse;
        } else {
            throw new NoSuchElementFoundException("Category not found or has been removed with ID");
        }
    }


    public void deleteCategoryById(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryRepository.existsById(id) && !categoryOpt.get().getIsRemoved()) {
            Category category = categoryOpt.get();
            category.setIsRemoved(true);
            category.setUpdatedAt(LocalDateTime.now());
            categoryRepository.save(category);
        } else {
            throw new NoSuchElementFoundException("Product not found or has been removed with ID");
        }
    }

    public CategoryResponse updateCategory(Long id, CategoryDto categoryUpdateRequest) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent() && !categoryOpt.get().getIsRemoved()) {
            Category category = categoryOpt.get();
            category.setName(categoryUpdateRequest.getName());
            category.setDescription(categoryUpdateRequest.getDescription());
            category.setIsRemoved(categoryUpdateRequest.getIsRemoved());
            category.setUpdatedAt(LocalDateTime.now());
            Category updatedCategory = categoryRepository.save(category);
            return convertToCategoryResponse(updatedCategory);
        } else {
            throw new NoSuchElementFoundException("Role not found or has been removed with ID");
        }
    }

    private CategoryResponse convertToCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        categoryResponse.setIsRemoved(category.getIsRemoved());
        return categoryResponse;
    }
    public PaginationResponse<CategoryResponse> getAllCategoriesPagination(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<CategoryResponse> categoryResponses = categoryPage.getContent().stream()
                .map(this::convertToCategoryResponse)
                .collect(Collectors.toList());

        PaginationResponse<CategoryResponse> paginatedResponse = new PaginationResponse<>();
        paginatedResponse.setItems(categoryResponses);
        paginatedResponse.setPage(categoryPage.getNumber() + 1); // Page index starts from 0, so add 1
        paginatedResponse.setPerPage(categoryPage.getSize());
        paginatedResponse.setTotalPages(categoryPage.getTotalPages());
        paginatedResponse.setTotalItems(categoryPage.getTotalElements());

        return paginatedResponse;
    }
}
