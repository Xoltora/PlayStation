package com.example.playstationdemo.payload;

import com.example.playstationdemo.entity.Room;
import com.example.playstationdemo.entity.enums.State;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class RoomDto {
    private Long id;

//    @NotBlank(message = "name is required")
    private String name;

//    @NotBlank(message = "room type is required")
    private RoomTypeDto type;

    private State state;

    private String createdBy;

    private LocalDateTime createdAt;

    public Room mapToEntity(){
        Room room = new Room();
        room.setName(this.name);
        room.setType(this.type.getId());
        return room;
    }

    public Room mapToEntity(Room room){
        room.setName(this.name);
        room.setState(this.state);
        room.setType(this.type.getId());
        return room;
    }
}
