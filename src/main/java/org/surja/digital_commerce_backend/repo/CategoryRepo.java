package org.surja.digital_commerce_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.surja.digital_commerce_backend.entity.Category;

public interface CategoryRepo extends JpaRepository <Category, Long>{
}
