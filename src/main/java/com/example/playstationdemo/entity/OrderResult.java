package com.example.playstationdemo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderResult extends AbsEntity{
    @ManyToOne
    private Order order;

    private Long TotalSum;

    private Long productSum;

    private Long roomSum;

    public void setOrder(Long id){
        this.order = new Order(id);
    }
}
