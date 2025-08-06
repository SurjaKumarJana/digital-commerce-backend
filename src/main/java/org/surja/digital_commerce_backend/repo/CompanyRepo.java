package org.surja.digital_commerce_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.surja.digital_commerce_backend.entity.Company;

public interface CompanyRepo extends JpaRepository <Company,Long>{
}
