package com.example.playstationdemo.entity;

import com.example.playstationdemo.payload.RoomTypeDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomType extends AbsEntity{
    @Column(nullable = false)
    private String name;

    private Double price;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public RoomType(Long id){
        super.setId(id);
    }

    public RoomTypeDto mapToDto(){
        RoomTypeDto dto = new RoomTypeDto();
        dto.setId(this.getId());
        dto.setName(this.name);
        dto.setPrice(this.price);
        dto.setCreatedBy(this.getCreatedBy());
        dto.setCreatedAt(this.getCreatedAt());
        return dto;
    }
}
