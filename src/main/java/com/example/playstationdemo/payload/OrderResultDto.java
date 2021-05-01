package com.example.playstationdemo.payload;

import com.example.playstationdemo.entity.OrderResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResultDto {
    private OrderDto order;
    private Double TotalSum;
    private Double productSum;
    private Double roomSum;


    public OrderResult mapToEntity(){
        OrderResult result = new OrderResult();
        result.setOrder(this.order.getId());
        result.setProductSum(this.productSum);
        result.setRoomSum(this.roomSum);
        result.setTotalSum(this.TotalSum);
        return result;
    }

}
