package org.surja.digital_commerce_backend.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.surja.digital_commerce_backend.dto.CreateCompanyReqDTO;
import org.surja.digital_commerce_backend.dto.CreateResponseDTO;
import org.surja.digital_commerce_backend.dto.ProductDTO;
import org.surja.digital_commerce_backend.service.SellerService;

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
}
