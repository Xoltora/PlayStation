package com.example.playstationdemo.repository;

import com.example.playstationdemo.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o.id, o.startAt, o.finishedAt, ors.productSum, ors.roomSum, ors.TotalSum \n" +
            "from Order o join OrderResult ors on ors.order.id = o.id where cast(o.finishedAt as date) = :date1")
    Page<Object[]> getReport(Date date1, Pageable pageable);
}
