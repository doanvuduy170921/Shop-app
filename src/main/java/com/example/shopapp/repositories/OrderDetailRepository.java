package com.example.shopapp.repositories;

import com.example.shopapp.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    List<OrderDetail> findByOrderId(Long orderId);
}
