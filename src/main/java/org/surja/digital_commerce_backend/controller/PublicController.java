package org.surja.digital_commerce_backend.controller;


import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.surja.digital_commerce_backend.dto.*;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.service.CustomerService;
import org.surja.digital_commerce_backend.service.PublicService;

import java.awt.image.ReplicateScaleFilter;
import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    private static Logger LOGGER = LoggerFactory.getLogger(PublicController.class);
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PublicService publicService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsByKeywords(@RequestParam String keyword, @RequestParam Integer pageSize, @RequestParam Integer pageNo) throws NotFoundException {
        LOGGER.info("request for all product with keyword : "+keyword);
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        return ResponseEntity.ok(customerService.getProdcutsByKeyword(keyword,pageable));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDTO> signUpUser(@RequestBody SignUpDTO signUpDTO) throws NotFoundException {
        SignUpResponseDTO response = publicService.signUpRequest(signUpDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> creatVerifiedUser(@PathVariable String token, @RequestBody UserDTO userDTO) throws NotFoundException {
        ResponseDTO response  = publicService.createUser(token , userDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public  ResponseEntity<SignUpResponseDTO> requestPasswordReset(@RequestBody SignUpDTO signUpDTO) throws NotFoundException {
        SignUpResponseDTO response = publicService.requestPasswordReset(signUpDTO);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/reset-password/{token}")
    public ResponseEntity<ResponseDTO> resetPassword(@PathVariable String token, @RequestBody UserDTO userDTO) throws NotFoundException {
        return ResponseEntity.ok(publicService.resetPassword(token,userDTO));
    }






}
