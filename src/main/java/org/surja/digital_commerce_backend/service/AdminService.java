package org.surja.digital_commerce_backend.service;


import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.surja.digital_commerce_backend.dto.CreateCompanyReqDTO;
import org.surja.digital_commerce_backend.dto.CreateResponseDTO;
import org.surja.digital_commerce_backend.dto.SellerDTO;
import org.surja.digital_commerce_backend.entity.Company;
import org.surja.digital_commerce_backend.entity.Role;
import org.surja.digital_commerce_backend.entity.User;
import org.surja.digital_commerce_backend.repo.CompanyRepo;

@Service
public class AdminService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CompanyRepo companyRepo;


    @Transactional
    public CreateResponseDTO createCompany(CreateCompanyReqDTO companyReqDTO){
        Company company = new Company();
        company.setName(companyReqDTO.getName());
        company.setActive(true);
        // if the entity is simple with few prop. then we can use this
        // , instead of creating repo
        entityManager.persist(company);

        CreateResponseDTO responseDTO = new CreateResponseDTO();
        responseDTO.setId(company.getId());
        return  responseDTO;
    }

    @Transactional
    public CreateResponseDTO createSeller(SellerDTO sellerDTO){

        Company company = companyRepo.findById(sellerDTO.getCompanyId()).get();
        if(company == null){
            throw  new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Company doesn't exits !");
        }
        User seller = new User();
        seller.setName(sellerDTO.getName());
        seller.setEmail(sellerDTO.getEmail());
        seller.setRole(Role.SELLER);
        seller.setCompany(company);
        entityManager.persist(seller);
        CreateResponseDTO responseDTO = new CreateResponseDTO();
        responseDTO.setId(seller.getId());

        return responseDTO;

    }



}
