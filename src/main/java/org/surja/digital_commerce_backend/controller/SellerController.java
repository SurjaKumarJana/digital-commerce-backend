package org.surja.digital_commerce_backend.controller;


import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.surja.digital_commerce_backend.dto.CreateResponseDTO;
import org.surja.digital_commerce_backend.dto.ProductDTO;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.service.SellerService;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    private static Logger LOGGER =  LoggerFactory.getLogger(SellerController.class);

    @Autowired
    private SellerService sellerService;

    @PostMapping("/product")
    public ResponseEntity<CreateResponseDTO> createProduct(@RequestBody ProductDTO productDTO){
        LOGGER.info("Creating a product ");
        return ResponseEntity.ok(sellerService.createProduct(productDTO));
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        LOGGER.info("Request for all the product ");
        return ResponseEntity.ok(sellerService.getAllProduct());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws NotFoundException {
        return ResponseEntity.ok(sellerService.updateProduct(id,productDTO));
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseDTO> deleteProductById(@PathVariable Long id) throws NotFoundException {
        LOGGER.info("request to delete product id : "+id);
        return ResponseEntity.ok(sellerService.deleteProduct(id));
    }
}
