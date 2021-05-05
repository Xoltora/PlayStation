package com.example.playstationdemo.payload.dto;

import com.example.playstationdemo.entity.OrderResult;
import com.example.playstationdemo.payload.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResultDto {
    private OrderDto order;
    private Long TotalSum;
    private Long productSum;
    private Long roomSum;


    public OrderResult mapToEntity(){
        OrderResult result = new OrderResult();
        result.setOrder(this.order.getId());
        result.setProductSum(this.productSum);
        result.setRoomSum(this.roomSum);
        result.setTotalSum(this.TotalSum);
        return result;
    }

}
