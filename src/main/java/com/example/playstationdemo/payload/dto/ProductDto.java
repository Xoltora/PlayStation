package com.example.playstationdemo.payload.dto;

import com.example.playstationdemo.entity.Product;
import com.example.playstationdemo.payload.dto.CategoryDto;
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
public class ProductDto {
    private Long id;

//    @NotBlank(message = "name is required")
    private String name;

//    @NotBlank(message = "price is required")
    private Long price;

//    @NotBlank(message = "category is required")
    private CategoryDto category;

    private String createdBy;

    private Date createdAt;

    public Product mapToEntity(){
        Product product = new Product();
        product.setName(this.name);
        product.setCategory(this.category.getId());
        product.setPrice(this.price);
        return product;
    }

    public Product mapToEntity(Product product){
        product.setName(this.name);
        product.setCategory(this.category.getId());
        product.setPrice(this.price);
        return product;
    }

}
