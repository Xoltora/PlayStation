package com.example.playstationdemo.payload;

import com.example.playstationdemo.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeDto {
    private Long id;

//    @NotBlank(message = "name is required")
    private String name;

//    @NotBlank(message = "price is required")
    private Double price;

    private String createdBy;

    private LocalDateTime createdAt;

    public RoomType mapToEntity(){
        RoomType type = new RoomType();
        type.setId(this.id);
        type.setName(this.name);
        type.setPrice(this.price);
        return type;
    }
}
