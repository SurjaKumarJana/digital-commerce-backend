package org.surja.digital_commerce_backend.controller;


import com.fasterxml.jackson.core.util.BufferRecycler;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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


    @PostMapping("/product/uploadCSV")
    public ResponseEntity<List<CreateResponseDTO>> uploadCsv (@RequestParam MultipartFile file) throws IOException {
        LOGGER.info("File received : {}", file.getOriginalFilename());

        //first we have to read the file
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        // then we have to parse each row ,so we need a parser
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
        );
        //CSVRecord represent each row in the csv file
        List<CSVRecord> records = csvParser.getRecords();

        //we need a list of CreateResponseDTO
        List<CreateResponseDTO> responseDTOS = new ArrayList<>();
        //now we have to convert this CSVRecord object into productDTO and call the createProduct methods
        for(CSVRecord record : records){
            ProductDTO productDTO = new ProductDTO();

            // setting the values of productDTO
            productDTO.setId(Long.valueOf(record.get("Id")));
            productDTO.setName(record.get("name"));
            productDTO.setDescription(record.get("description"));
            productDTO.setPrice(Double.valueOf(record.get("price")));
            productDTO.setStocks(Integer.valueOf(record.get("stocks")));
            productDTO.setImageUrl(record.get("imageUrl"));
            productDTO.setCompanyId(Long.valueOf(record.get("companyId")));
            productDTO.setCategoryId(Long.valueOf(record.get("categoryId")));

            //calling sellerService
            CreateResponseDTO response = sellerService.createProduct(productDTO);
            //capturing the responses
            responseDTOS.add(response);
        }


        return  ResponseEntity.ok(responseDTOS);
    }

}
