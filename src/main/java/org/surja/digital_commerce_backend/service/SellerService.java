package org.surja.digital_commerce_backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.surja.digital_commerce_backend.dto.CreateResponseDTO;
import org.surja.digital_commerce_backend.dto.ProductDTO;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.entity.Category;
import org.surja.digital_commerce_backend.entity.Company;
import org.surja.digital_commerce_backend.entity.Product;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.repo.CategoryRepo;
import org.surja.digital_commerce_backend.repo.CompanyRepo;
import org.surja.digital_commerce_backend.repo.ProductRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private ProductRepo productRepo;

    @Transactional
    public CreateResponseDTO createProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setActive(true);
        product.setStocks(productDTO.getStocks());
        product.setImageUrl(productDTO.getImageUrl());

        Company company = companyRepo.findById(productDTO.getCompanyId()).get();
        product.setCompany(company);

        Category category = categoryRepo.findById(productDTO.getCategoryId()).get();
        product.setCategory(category);

        productRepo.save(product);

        CreateResponseDTO responseDTO = new CreateResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setMessage("product added succssfully");

        return  responseDTO;
    }

    public List<ProductDTO> getAllProduct(){
        List<Product> productList = productRepo.findAll();
        List<ProductDTO> result = new ArrayList<>();
        for(Product product : productList){
            ProductDTO productDTO = ProductDTO.buildProductDTOFromProduct(product);
            result.add(productDTO);
        }
        return result;
    }

    @Transactional
    public ResponseDTO updateProduct(Long id, ProductDTO productDTO) throws NotFoundException {
        Product product = productRepo.findById(id).orElseThrow(
                ()-> new NotFoundException("Product Doesn't exist with id : "+id));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setStocks(productDTO.getStocks());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(categoryRepo.findById(productDTO.getCategoryId()).get());
        product.setCompany(companyRepo.findById(productDTO.getCompanyId()).get());


        ResponseDTO responseDTO = ResponseDTO.builder()
                .id(product.getId())
                .message("product updated")
                .code("124 -update")
                .build();

        return responseDTO;
    }


    @Transactional
    public ResponseDTO deleteProduct(Long id) throws NotFoundException {
        Product product = productRepo.findById(id).orElseThrow(
                ()-> new NotFoundException("Product doesn't exists with id : "+id));

        productRepo.deleteById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setMessage("Product Deleted");
        responseDTO.setCode("123-D");

        return responseDTO;
    }

}
