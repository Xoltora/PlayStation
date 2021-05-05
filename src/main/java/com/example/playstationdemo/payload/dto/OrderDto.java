package com.example.playstationdemo.payload.dto;

import com.example.playstationdemo.entity.Order;
import com.example.playstationdemo.entity.enums.State;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private Long id;
    private RoomDto room;
    private Date startAt;
    private Date finishAt;
    private State state;

    public OrderDto(Long id){
        this.id = id;
    }
    public Order mapToEntity() {
        Order order = new Order();
        order.setRoom(this.room.getId());
        order.setState(State.ON_PROCESS);
        order.setStartAt(new Date());
        return order;
    }
}