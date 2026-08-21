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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public ProductResponse findById(UUID id) throws Exception {
        // 1. Loads base entity into Persistence Context (1st-level cache)
        Product product = productRepository.findByIdWithBaseDetails(id)
                .orElseThrow(() -> new Exception("Produto não encontrado"));

        // 2. Hydrates 'product.variants' in-memory; avoids Cartesian Product (requires @Transactional)
        productRepository.fetchVariantsWithDetailsByProductId(id);

        return ProductResponse.from(product);
    }
}