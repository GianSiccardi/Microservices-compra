package com.giansiccardi.product.controllers;

import com.giansiccardi.product.dto.ProductPurchaseRequest;
import com.giansiccardi.product.dto.ProductPurchaseResponse;
import com.giansiccardi.product.dto.ProductRequest;
import com.giansiccardi.product.dto.ProductResponse;
import com.giansiccardi.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

private final ProductService productService;

@PostMapping
    public ResponseEntity<Integer>createProduct(
            @RequestBody @Valid ProductRequest request
){
return ResponseEntity.ok(productService.createProduct(request));
}

@PostMapping("/purchase")
public ResponseEntity<List<ProductPurchaseResponse>>purchseProducts(
        @RequestBody List<ProductPurchaseRequest>request
        ){
    return ResponseEntity.ok(productService.purchaseProducts(request));
}

@GetMapping("/{id}")
    public ResponseEntity<ProductResponse>findById(@PathVariable("id") Integer id ){
    return ResponseEntity.ok(productService.findById(id));
}


    @GetMapping()
    public ResponseEntity<List<ProductResponse>>findAll( ){
        return ResponseEntity.ok(productService.findAll());
    }

}
