package com.enzo.ecommerce.product;

import com.enzo.ecommerce.product.dto.ProductResponse;
import com.enzo.ecommerce.product.dto.ProductSummaryResponse;
import com.enzo.ecommerce.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductSummaryResponse> findAllSummary(Pageable pageable) {

        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(
                            Sort.Order.desc("createdAt"),
                            Sort.Order.desc("id")
                    )
            );
        }

        return productRepository.findAllSummary(pageable);
    }

    public ProductResponse findById(String id) throws Exception {

        UUID productId;

        try {
            productId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid product ID: " + id);
        }

        Product product = productRepository
                .findByIdWithDetails(productId)
                .orElseThrow(() ->
                        new Exception(String.valueOf(productId))
                );

        return ProductResponse.from(product);
    }
}