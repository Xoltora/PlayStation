package com.example.playstationdemo.entity;

import com.example.playstationdemo.entity.enums.State;
import com.example.playstationdemo.payload.RoomDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room extends AbsEntity{
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private RoomType type;

    @Enumerated(EnumType.STRING)
    private State state = State.ON_VACATE;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public Room(Long id){
        super.setId(id);
    }

    public void setType(Long id){
        this.type = new RoomType(id);
    }

    public RoomDto mapToDto(){
        RoomDto dto = new RoomDto();
        dto.setId(this.getId());
        dto.setName(this.name);
        dto.setType(type.mapToDto());
        dto.setState(this.state);
        dto.setCreatedAt(this.getCreatedAt());
        dto.setCreatedBy(this.getCreatedBy());
        return dto;
    }
}
