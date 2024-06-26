package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.constants.ProductResponse;
import com.hutech.furniturestore.constants.RoleResponse;
import com.hutech.furniturestore.dtos.ProductDto.*;
import com.hutech.furniturestore.models.Product;
import com.hutech.furniturestore.models.Role;
import com.hutech.furniturestore.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(CreateProductDto productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setThumbnail(productRequest.getThumbnail());
        product.setIsRemoved(productRequest.getIsRemoved());
        product.setQuantity(productRequest.getQuantity());
        product.setIsAvailable(productRequest.getIsAvailable());
        product.setIsBestSeller(productRequest.getIsBestSeller());
        Product savedProduct = productRepository.save(product);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setDescription(savedProduct.getDescription());
        return productResponse;
    }

    public Optional<Product> getProductById(Long id) {return productRepository.findById(id);}

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {productRepository.deleteById(id);}

    public Optional<Product> updateProduct(Long id, Product product){return productRepository.findById(id);}

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
