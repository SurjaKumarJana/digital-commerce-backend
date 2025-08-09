package org.surja.digital_commerce_backend.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.surja.digital_commerce_backend.dto.AddToOrderDto;
import org.surja.digital_commerce_backend.dto.OrderDetailDto;
import org.surja.digital_commerce_backend.dto.ProductDTO;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.service.CustomerService;


import java.util.List;

@RestController
@RequestMapping("/api/customer")

public class CustomerController {
    private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;


    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsByKeywords(@RequestParam String keyword, @RequestParam Integer pageSize, @RequestParam Integer pageNo) throws NotFoundException {
        LOGGER.info("request for all product with keyword : "+keyword);
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        return ResponseEntity.ok(customerService.getProdcutsByKeyword(keyword,pageable));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws NotFoundException {
        LOGGER.info("request for product having id : "+id);
        return ResponseEntity.ok(customerService.getById(id));
    }

    @PostMapping("/order-item")
    public ResponseEntity<OrderDetailDto> addToOrder(@RequestBody AddToOrderDto addToOrderDto) throws NotFoundException {
        LOGGER.info("request for add to order with id : "+addToOrderDto.getUserId());
        OrderDetailDto orderDetailDto = customerService.addToOrder(addToOrderDto);
        return ResponseEntity.ok(orderDetailDto);
    }

    @PutMapping("/order/{id}/submit")
    public ResponseEntity<ResponseDTO> submitOrder(@PathVariable Long id) throws NotFoundException {
        LOGGER.info("request to submit order having id : "+id);
        return ResponseEntity.ok(customerService.submitOrder(id));
    }

}
