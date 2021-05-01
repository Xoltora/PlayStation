package com.example.playstationdemo.repository;

import com.example.playstationdemo.entity.Room;
import com.example.playstationdemo.entity.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Modifying
    @Override
    @Query("UPDATE Room r SET r.isDeleted = true WHERE r.id = :id")
    void deleteById(@Param("id") Long id);


    @Override
    @Query("SELECT r FROM Room r WHERE r.isDeleted = false")
    List<Room> findAll();

    @Query("select case \n" +
            "when r.state = 'ON_PROCESS' then 'process'\n" +
            "when r.isDeleted = true then 'deleted' else 'ok 'end from Room r where r.id = :id")
    String isOk(@Param("id") Long id);

    @Modifying
    @Query("update Room r set r.state = :state where r.id = :id")
    void saveState(@Param("id") Long id, @Param("state")State state);
}
