package com.example.playstationdemo.payload.dto;

import com.example.playstationdemo.entity.OrderDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDto {
    private OrderDto order;
    private ProductDto product;
    private Integer count;

    public OrderDetail mapToEntity(){
        OrderDetail detail = new OrderDetail();
        detail.setOrder(this.order.getId());
        detail.setProduct(this.product.getId());
        detail.setCount(this.count);
        return detail;
    }
}
