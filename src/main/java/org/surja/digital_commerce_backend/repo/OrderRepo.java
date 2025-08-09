package org.surja.digital_commerce_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.surja.digital_commerce_backend.entity.Order;
import org.surja.digital_commerce_backend.entity.OrderStatus;
import org.surja.digital_commerce_backend.entity.User;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByOrderStatusAndUser(OrderStatus orderStatus, User user);

}
