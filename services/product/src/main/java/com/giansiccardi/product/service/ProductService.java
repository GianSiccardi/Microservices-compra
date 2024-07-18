package com.giansiccardi.product.service;

import com.giansiccardi.product.dto.ProductPurchaseRequest;
import com.giansiccardi.product.dto.ProductPurchaseResponse;
import com.giansiccardi.product.dto.ProductRequest;
import com.giansiccardi.product.dto.ProductResponse;
import com.giansiccardi.product.exception.ProductPurchaseException;
import com.giansiccardi.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Integer createProduct(ProductRequest request) {
        var product= productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
   var productsIds=request
           .stream()
           .map(ProductPurchaseRequest::productId)
           .toList();
   var storedProducts=productRepository.findAllByIdInOrderById(productsIds);


   if(productsIds.size()!=storedProducts.size()){
          throw new ProductPurchaseException("Algun producto no exite");
   }
   var storedRequest= request
           .stream()
           .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
           .toList();
   var purchasedProducts= new ArrayList<ProductPurchaseResponse>();
   for(int i=0;i<storedProducts.size();i++){
       var product =storedProducts.get(i);
       var productRequest=storedRequest.get(i);
       if(product.getStock()<productRequest.quantity()){
           throw new ProductPurchaseException("Stock insuficiente para el producto con el id " + productRequest.productId());
       }

       var newStock =product.getStock()-productRequest.quantity();
       product.setStock(newStock);
       productRepository.save(product);
       purchasedProducts.add(productMapper.toProductPurchaseResponse(product,productRequest.quantity()));
   }

        return purchasedProducts;
    }

    public ProductResponse findById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(()->new EntityNotFoundException("Producto no encontrado con el id " + id));
    }

    public List<ProductResponse> findAll() {
    return productRepository.findAll()
            .stream()
            .map(productMapper::toProductResponse)
            .collect(Collectors.toList());
    }
}
