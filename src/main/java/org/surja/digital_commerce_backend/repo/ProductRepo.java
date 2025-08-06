package org.surja.digital_commerce_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.surja.digital_commerce_backend.entity.Product;
@Repository
public interface ProductRepo extends JpaRepository <Product,Long>{
}
