package org.surja.digital_commerce_backend.controller;


import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.surja.digital_commerce_backend.dto.CreateResponseDTO;
import org.surja.digital_commerce_backend.dto.ProductDTO;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.service.SellerService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    private static Logger LOGGER =  LoggerFactory.getLogger(SellerController.class);

    @Value("${image.upload.home}")
    private String imageUploadHome;
    @Value("${static.domain.name}")
    private String staticDomainName;

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

    // api to upload image
    //in prod, we store them in file serves like AWS S3 , but here as a demo we store them locally
    @PostMapping("/product/image")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file){
        LOGGER.info("File received {}",file.getOriginalFilename());

        String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
        // in this case we are storing it locally so creating the path
        String uploadPath = imageUploadHome+fileName;
        String publicUrl = staticDomainName +"content/"+fileName;
        try {
            file.transferTo(new File(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  ResponseEntity.ok(publicUrl);
    }



}
