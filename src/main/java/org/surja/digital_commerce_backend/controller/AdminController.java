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
import org.surja.digital_commerce_backend.dto.SellerDTO;
import org.surja.digital_commerce_backend.service.AdminService;


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
    @PostMapping("/seller")
    public ResponseEntity<CreateResponseDTO> createSeller(@RequestBody SellerDTO sellerDTO){
        LOGGER.info("Creating seller");
        return ResponseEntity.ok(adminService.createSeller(sellerDTO));
    }


}
