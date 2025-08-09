package org.surja.digital_commerce_backend.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.surja.digital_commerce_backend.entity.Product;


import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository <Product,Long>{

    List<Product> findByNameContaining(String keywords, Pageable pageable);
}
