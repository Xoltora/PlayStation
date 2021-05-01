package com.example.playstationdemo.entity;

import com.example.playstationdemo.payload.CategoryDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbsEntity {
    @Column(nullable = false)
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public Category(Long id) {
        super.setId(id);
    }

    public CategoryDto mapToDto(){
        CategoryDto dto = new CategoryDto();
        dto.setId(this.getId());
        dto.setName(this.name);
        return dto;
    }
}
