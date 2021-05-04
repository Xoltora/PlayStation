package com.example.playstationdemo.repository;

import com.example.playstationdemo.entity.Order;
import com.example.playstationdemo.payload.OrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o.id, o.startAt, o.finishedAt, ors.productSum, ors.roomSum, ors.TotalSum \n" +
            "from orders o join OrderResult ors on ors.order.id = o.id where cast(o.finishedAt as date) = :date1")
    List<Object[]> getReport(Date date1);
}
