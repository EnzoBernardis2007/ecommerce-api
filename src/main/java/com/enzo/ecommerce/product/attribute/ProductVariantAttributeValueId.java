package com.enzo.ecommerce.product.attribute;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductVariantAttributeValueId
    implements Serializable {

    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID variantId;

    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID attributeValueId;
}