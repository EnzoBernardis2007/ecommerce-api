package com.enzo.ecommerce.product;

import com.enzo.ecommerce.product.dto.ProductSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductSummaryResponse> findAllSummary(Pageable pageable) {
        return productRepository.findAllSummary(pageable);
    }
}
