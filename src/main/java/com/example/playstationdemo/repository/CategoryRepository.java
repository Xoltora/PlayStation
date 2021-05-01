package com.example.playstationdemo.repository;

import com.example.playstationdemo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Override
    @Query("UPDATE Category c SET c.isDeleted = true WHERE c.id = ?1")
    void deleteById(Long id);

    @Override
    @Query("SELECT c FROM Category c WHERE c.isDeleted = false")
    List<Category> findAll();

//    @Query("SELECT CASE WHEN ")
//    String isDeleted(@Param("id") Long id);
}
