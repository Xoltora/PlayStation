package com.example.playstationdemo.payload;

import com.example.playstationdemo.entity.Category;
import com.example.playstationdemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    public Category mapToEntity(User currentUser){
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }
}
