package org.surja.digital_commerce_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.surja.digital_commerce_backend.dto.ProductDTO;
import org.surja.digital_commerce_backend.entity.Product;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.repo.ProductRepo;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductDTO> getProdcutsByKeyword(String keyword) throws NotFoundException {
        List<Product> productList = productRepo.findByNameContaining(keyword);
        if (productList.isEmpty()) {
            throw new NotFoundException("No products found for keyword: " + keyword);
        }
        List<ProductDTO> result = new ArrayList<>();
        for(Product product: productList){
            ProductDTO productDTO = ProductDTO.buildProductDTOFromProduct(product);
            result.add(productDTO);
        }

        return result;
    }

}
