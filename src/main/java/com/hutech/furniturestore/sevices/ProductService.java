package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.constants.ProductResponse;
import com.hutech.furniturestore.dtos.ProductDto;
import com.hutech.furniturestore.exceptions.NoSuchElementFoundException;
import com.hutech.furniturestore.models.Category;
import com.hutech.furniturestore.models.Product;
import com.hutech.furniturestore.repositories.CategoryRepository;
import com.hutech.furniturestore.repositories.ProductRepository;
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
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductDto productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setThumbnail(productRequest.getThumbnail());
        product.setIsRemoved(productRequest.getIsRemoved());
        product.setQuantity(productRequest.getQuantity());
        product.setIsAvailable(productRequest.getIsAvailable());
        product.setIsBestSeller(productRequest.getIsBestSeller());
        product.setSold(productRequest.getSold());
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NoSuchElementFoundException("Category not found with id: " + productRequest.getCategoryId()));
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setDescription(savedProduct.getDescription());
        return productResponse;
    }


    public ProductResponse getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent() && !productOpt.get().getIsRemoved()) {
            Product product = productOpt.get();
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setThumbnail(product.getThumbnail());
            productResponse.setIsBestSeller(product.getIsBestSeller());
            productResponse.setIsAvailable(product.getIsAvailable());
            productResponse.setSold(product.getSold());
            productResponse.setCategoryId(product.getCategory().getId());
            productResponse.setQuantity(product.getQuantity());
            return productResponse;
        } else {
            throw new NoSuchElementFoundException("Product not found or has been removed with ID");
        }
    }


    public void deleteProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productRepository.existsById(id) && !productOpt.get().getIsRemoved()) {
            Product product = productOpt.get();
            product.setIsRemoved(true);
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
        } else {
            throw new NoSuchElementFoundException("Product not found or has been removed with ID");
        }
    }

    public ProductResponse updateProduct(Long id, ProductDto productUpdateRequest) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent() && !productOpt.get().getIsRemoved()) {
            Product product = productOpt.get();
            product.setName(productUpdateRequest.getName());
            product.setDescription(productUpdateRequest.getDescription());
            product.setPrice(productUpdateRequest.getPrice());
            product.setThumbnail(productUpdateRequest.getThumbnail());
            product.setIsBestSeller(productUpdateRequest.getIsBestSeller());
            product.setIsAvailable(productUpdateRequest.getIsAvailable());
            product.setSold(productUpdateRequest.getSold());
            if (productUpdateRequest.getCategoryId() != null) {
                Category category = categoryRepository.findById(productUpdateRequest.getCategoryId())
                        .orElseThrow(() -> new NoSuchElementFoundException("Category not found with ID: " + productUpdateRequest.getCategoryId()));
                product.setCategory(category);
            }
            product.setQuantity(productUpdateRequest.getQuantity());
            product.setIsRemoved(productUpdateRequest.getIsRemoved());
            product.setUpdatedAt(LocalDateTime.now());
            Product updatedProduct = productRepository.save(product);
            return convertToProductResponse(updatedProduct);
        } else {
            throw new NoSuchElementFoundException("Product not found or has been removed with ID");
        }
    }

    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setThumbnail(product.getThumbnail());
        productResponse.setIsRemoved(product.getIsRemoved());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setIsAvailable(product.getIsAvailable());
        productResponse.setIsBestSeller(product.getIsBestSeller());
        return productResponse;
    }
    public PaginationResponse<ProductResponse> getAllProductsPagination(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());

        PaginationResponse<ProductResponse> paginatedResponse = new PaginationResponse<>();
        paginatedResponse.setItems(productResponses);
        paginatedResponse.setPage(productPage.getNumber() + 1); // Page index starts from 0, so add 1
        paginatedResponse.setPerPage(productPage.getSize());
        paginatedResponse.setTotalPages(productPage.getTotalPages());
        paginatedResponse.setTotalItems(productPage.getTotalElements());

        return paginatedResponse;
    }
}
