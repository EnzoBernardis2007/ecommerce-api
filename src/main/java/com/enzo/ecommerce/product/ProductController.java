package com.enzo.ecommerce.product;

import com.enzo.ecommerce.product.dto.ProductResponse;
import com.enzo.ecommerce.product.dto.ProductSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponse>> findAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.findAllSummary(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(productService.findById(id));
    }

}
