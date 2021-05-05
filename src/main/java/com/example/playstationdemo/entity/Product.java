package com.example.playstationdemo.entity;

import com.example.playstationdemo.entity.audit.UserDateAudit;
import com.example.playstationdemo.payload.dto.ProductDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends UserDateAudit {
    @Column(nullable = false)
    private String name;

    private Long price;

    @ManyToOne
    private Category category;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public void setCategory(Long id) {
        this.category = new Category(id);
    }

    public Product(Long id){
        super.setId(id);
    }

    public ProductDto mapToDto(){
        ProductDto dto = new ProductDto();
        dto.setId(this.getId());
        dto.setName(this.name);
        dto.setPrice(this.price);
        dto.setCategory(this.category.mapToDto());
        //TODO name soxranit qilish kerak
        dto.setCreatedBy(this.getCreatedBy());
        dto.setCreatedAt(this.getCreatedAt());
        return dto;
    }

}
