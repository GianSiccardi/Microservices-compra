package com.giansiccardi.product.service;

import com.giansiccardi.product.dto.ProductPurchaseResponse;
import com.giansiccardi.product.dto.ProductRequest;
import com.giansiccardi.product.dto.ProductResponse;
import com.giansiccardi.product.models.Category;
import com.giansiccardi.product.models.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
    return Product.builder().
        id(request.id())
                .name(request.name())
                        .description(request.description())
                                .stock(request.stock())
                                        .price(request.price())
                                                .category(Category.builder().id(request.id()).build()).
            build();
    }

    public ProductResponse toProductResponse(Product product) {
   return new ProductResponse(
           product.getId(),
           product.getName(),
           product.getDescription(),
           product.getStock(),
           product.getPrice(),
           product.getCategory().getId(),
           product.getCategory().getName(),
           product.getCategory().getDescription()
   );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, Double quantity) {
   return new ProductPurchaseResponse(
              product.getId(),
              product.getName(),
              product.getDescription(),
           quantity,
           product.getPrice()

      );
    }
}
