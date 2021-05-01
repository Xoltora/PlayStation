package com.example.playstationdemo.entity;

import com.example.playstationdemo.entity.enums.State;
import com.example.playstationdemo.payload.OrderDto;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends AbsEntity{
    @ManyToOne
    private Room room;

    private LocalDateTime finishedAt;

    private LocalDateTime startAt;

    @Enumerated(EnumType.STRING)
    private State state;

    public void setRoom(Long id){
        this.room = new Room(id);
    }

    public Order(Long id){
        super.setId(id);
    }

    public OrderDto mapToDto(){
        OrderDto dto = new OrderDto();
        dto.setId(this.getId());
        dto.setRoom(this.room.mapToDto());
        dto.setStartAt(this.startAt);
        dto.setFinishAt(this.finishedAt);
        dto.setState(this.state);
        return dto;
    }

    public void finish(){
        this.setState(State.ON_VACATE);
        this.setFinishedAt(LocalDateTime.now());
    }

    public Order start(Long id){
        this.setStartAt(LocalDateTime.now());
        this.setRoom(id);
        this.setState(State.ON_PROCESS);
        return this;
    }
}
