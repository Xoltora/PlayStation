package com.example.playstationdemo.entity;

import com.example.playstationdemo.payload.OrderDetailDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetail extends AbsEntity{
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    private Integer count;

    public void setOrder(Long id){
        this.order = new Order(id);
    }

    public void setProduct(Long id){
        this.product = new Product(id);
    }

    public OrderDetailDto mapToDto(){
        OrderDetailDto dto = new OrderDetailDto();
        dto.setOrder(this.order.mapToDto());
        dto.setProduct(this.product.mapToDto());
        dto.setCount(this.count);
        return dto;
    }
}
