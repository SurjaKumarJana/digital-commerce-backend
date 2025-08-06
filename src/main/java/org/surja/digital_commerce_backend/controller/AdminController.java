package org.surja.digital_commerce_backend.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.surja.digital_commerce_backend.dto.CreateCompanyReqDto;
import org.surja.digital_commerce_backend.dto.CreateResponseDto;
import org.surja.digital_commerce_backend.dto.SellerDto;
import org.surja.digital_commerce_backend.service.AdmineService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdmineService adminService;

    @PostMapping("/company")
    public ResponseEntity<CreateResponseDto> createCompany(@RequestBody CreateCompanyReqDto companyReqDto){
        LOGGER.info("creating comapany");

        return ResponseEntity.ok(adminService.createCompany(companyReqDto)) ;
    }
    @PostMapping("/seller")
    public ResponseEntity<CreateResponseDto> createSeller(@RequestBody SellerDto sellerDTO){
        LOGGER.info("Creating seller");
        return ResponseEntity.ok(adminService.createSeller(sellerDTO));
    }


}
