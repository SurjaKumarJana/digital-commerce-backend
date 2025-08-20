package org.surja.digital_commerce_backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.surja.digital_commerce_backend.dto.CreateResponseDTO;
import org.surja.digital_commerce_backend.dto.ProductDTO;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.entity.Category;
import org.surja.digital_commerce_backend.entity.Company;
import org.surja.digital_commerce_backend.entity.Product;
import org.surja.digital_commerce_backend.entity.User;
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

        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setActive(true);
        product.setStocks(productDTO.getStocks());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCompany(user.getCompany());

        Category category = categoryRepo.findById(productDTO.getCategoryId()).get();
        product.setCategory(category);

        productRepo.save(product);


        return  CreateResponseDTO.builder()
                .id(product.getId())
                .errorCode("success - 123")
                .message("product added succssfully")
                .build();
    }

    public List<ProductDTO> getAllProduct(){
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Product> productList = productRepo.findByCompany(user.getCompany());
        List<ProductDTO> result = new ArrayList<>();
        for(Product product : productList){
            ProductDTO productDTO = ProductDTO.buildProductDTOFromProduct(product);
            result.add(productDTO);
        }
        return result;
    }

    @Transactional
    public ResponseDTO updateProduct(Long id, ProductDTO productDTO) throws NotFoundException {
        Product product = productRepo.findById(id).get();

        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(product == null || ! product.getCompany().equals(user.getCompany())){
            throw new NotFoundException(" Company having id : "+user.getCompany().getId()+" does not have the product");

        }

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setStocks(productDTO.getStocks());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(categoryRepo.findById(productDTO.getCategoryId()).get());
        product.setCompany(user.getCompany());


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
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(product == null || ! product.getCompany().equals(user.getCompany())){
            throw new NotFoundException(" Company having id : "+user.getCompany().getId()+" does not have the product");

        }

        productRepo.deleteById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setMessage("Product Deleted");
        responseDTO.setCode("123-D");

        return responseDTO;
    }

}
