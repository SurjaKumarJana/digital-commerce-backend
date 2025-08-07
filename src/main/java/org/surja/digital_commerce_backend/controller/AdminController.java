package org.surja.digital_commerce_backend.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.surja.digital_commerce_backend.dto.CreateCompanyReqDTO;
import org.surja.digital_commerce_backend.dto.CreateResponseDTO;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.dto.SellerDTO;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.service.AdminService;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PostMapping("/company")
    public ResponseEntity<CreateResponseDTO> createCompany(@RequestBody CreateCompanyReqDTO companyReqDto){
        LOGGER.info("creating comapany");

        return ResponseEntity.ok(adminService.createCompany(companyReqDto)) ;
    }
    @PutMapping("/company/{id}")
    public ResponseEntity<ResponseDTO> updateCompany(@PathVariable Long id, @RequestBody CreateCompanyReqDTO companyReqDto) throws NotFoundException {
        LOGGER.info("updating comapany having Id : "+ id);
        return ResponseEntity.ok(adminService.updateCompany(id,companyReqDto)) ;
    }
    @DeleteMapping("/company/{id}")
    public ResponseEntity<ResponseDTO> deleteCompany(@PathVariable Long id) throws NotFoundException {
        LOGGER.info("deleting comapany having Id : "+ id);
        return ResponseEntity.ok(adminService.deleteCompany(id)) ;
    }





    @PostMapping("/seller")
    public ResponseEntity<CreateResponseDTO> createSeller(@RequestBody SellerDTO sellerDTO){
        LOGGER.info("Creating seller");
        return ResponseEntity.ok(adminService.createSeller(sellerDTO));
    }
    @GetMapping("/seller")
    public ResponseEntity<List<SellerDTO>> getAllSellers(){

        LOGGER.info("Getting the seller list ");
        return ResponseEntity.ok(adminService.getAllSellers());

    }

    @DeleteMapping("/seller/{id}")
    private ResponseEntity<ResponseDTO> deleteSeller(@PathVariable Long id) throws NotFoundException {
        LOGGER.info("deleting seller  Id : "+id);
        return ResponseEntity.ok(adminService.deleteSeller(id));
    }



}
