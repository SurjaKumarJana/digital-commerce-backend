package org.surja.digital_commerce_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.surja.digital_commerce_backend.entity.Role;
import org.surja.digital_commerce_backend.entity.User;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    List<User>  findByRole(Role role);
    User findByEmail(String email);
}
