package org.surja.digital_commerce_backend.service;


import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.surja.digital_commerce_backend.dto.CreateCompanyReqDto;
import org.surja.digital_commerce_backend.dto.CreateResponseDto;
import org.surja.digital_commerce_backend.dto.SellerDto;
import org.surja.digital_commerce_backend.entity.Company;
import org.surja.digital_commerce_backend.entity.Role;
import org.surja.digital_commerce_backend.entity.User;
import org.surja.digital_commerce_backend.repo.CompanyRepo;

@Service
public class AdmineService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CompanyRepo companyRepo;


    @Transactional
    public CreateResponseDto createCompany(CreateCompanyReqDto companyReqDto){
        Company company = new Company();
        company.setName(companyReqDto.getName());
        company.setActive(true);
        // if the entity is simple with few prop. then we can use this
        // , instead of creating repo
        entityManager.persist(company);

        CreateResponseDto responseDto = new CreateResponseDto();
        responseDto.setId(company.getId());
        return  responseDto;
    }

    @Transactional
    public CreateResponseDto createSeller(SellerDto sellerDto){

        Company company = companyRepo.findById(sellerDto.getCompanyId()).get();
        if(company == null){
            throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Company doesn't exits !");
        }
        User seller = new User();
        seller.setName(sellerDto.getName());
        seller.setEmail(sellerDto.getEmail());
        seller.setRole(Role.SELLER);
        seller.setCompany(company);
        entityManager.persist(seller);
        CreateResponseDto responseDto = new CreateResponseDto();
        responseDto.setId(seller.getId());

        return responseDto;

    }



}
