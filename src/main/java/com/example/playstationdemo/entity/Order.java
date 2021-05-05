package com.example.playstationdemo.entity;

import com.example.playstationdemo.entity.audit.UserDateAudit;
import com.example.playstationdemo.entity.enums.State;
import com.example.playstationdemo.payload.dto.OrderDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends UserDateAudit {
    @ManyToOne
    private Room room;

    private Date finishedAt;

    private Date startAt;

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
        this.setFinishedAt(new Date());
    }

    public Order start(Long id){
        this.setStartAt(new Date());
        this.setRoom(id);
        this.setState(State.ON_PROCESS);
        return this;
    }
}
