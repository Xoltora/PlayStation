package com.example.playstationdemo.repository;

import com.example.playstationdemo.entity.OrderResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderResultRepository extends JpaRepository<OrderResult, Long> {

    @Query("select sum(p.price * od.count) from OrderDetail od join Product p on " +
            "p.id = od.product.id join Order o on od.order.id = o.id where o.id = :id")
    Double getProductSum(@Param("id") Long id);
}
