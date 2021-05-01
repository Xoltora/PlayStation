package com.example.playstationdemo.repository;

import com.example.playstationdemo.entity.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    @Modifying
    @Override
    @Query("UPDATE RoomType r SET r.isDeleted = true WHERE r.id = :id")
    void deleteById(@Param("id") Long id);

    @Override
    @Query("SELECT r FROM RoomType r WHERE r.isDeleted = false")
    List<RoomType> findAll();
}
