package org.surja.digital_commerce_backend.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.surja.digital_commerce_backend.dto.ProductDTO;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    private static Logger LOGGER = LoggerFactory.getLogger(PublicController.class);
    @Autowired
    private CustomerService customerService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsByKeywords(@RequestParam String keyword, @RequestParam Integer pageSize, @RequestParam Integer pageNo) throws NotFoundException {
        LOGGER.info("request for all product with keyword : "+keyword);
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        return ResponseEntity.ok(customerService.getProdcutsByKeyword(keyword,pageable));
    }
}
