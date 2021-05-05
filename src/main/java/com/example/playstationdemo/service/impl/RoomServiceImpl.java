package com.example.playstationdemo.service.impl;

import com.example.playstationdemo.entity.Room;
import com.example.playstationdemo.entity.enums.State;
import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.RoomDto;
import com.example.playstationdemo.repository.RoomRepository;
import com.example.playstationdemo.service.RoomService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final EntityManager em;

    public RoomServiceImpl(@Lazy RoomRepository roomRepository, EntityManager em) {
        this.roomRepository = roomRepository;
        this.em = em;
    }

    @Override
    public ApiResponse save(RoomDto dto) {
        ApiResponse result = new ApiResponse();
        try {
            roomRepository.save(dto.mapToEntity());
            result.setMessage("Successfully saved room");
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Error on saving Room");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse edit(RoomDto dto) {
        ApiResponse result = new ApiResponse();
        try {
            Room room = roomRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Room with this " + dto.getId() + " id not found"));
            roomRepository.save(dto.mapToEntity(room));
            result.setMessage("Successfully edited room");
            result.setSuccess(true);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Room not found");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Something is wrong, please try again!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse getRoom(Integer page, Integer size) {
        ApiResponse result = new ApiResponse();
        try {
            List<Room> rooms = roomRepository.findAll();
            result.setMessage("Successfully came rooms");
            result.setSuccess(true);
            result.setData(rooms
                    .stream()
                    .map(Room::mapToDto)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Error in getting Rooms");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse getById(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("product not found with id = " + id));
            result.setSuccess(true);
            result.setMessage("Successfully came room");
            result.setData(room.mapToDto());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Room not found");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Something is wrong on coming rooms");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse remove(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("room not found"));
            room.setDeleted(true);
            roomRepository.save(room);
            result.setMessage("Successfully deleted");
            result.setSuccess(true);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Room not found");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Could not remove room");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse filter(String name, Long typeId, State state, Integer page, Integer size, Long currentPage) {
        ApiResponse result = new ApiResponse();
        try {
            StringBuilder sqlCondition = new StringBuilder();
            if (name != null) {
                sqlCondition.append("r.name like CONCAT(:name, '%') and ");
            }
            if (typeId != null) {
                sqlCondition.append("r.type.id = :typeId and ");
            }
            if (state == State.ON_PROCESS || state == State.ON_VACATE) {
                sqlCondition.append("r.state = :state and ");
            }

            sqlCondition.append("r.isDeleted = false");

            String sql = "select r from Room r where " + sqlCondition;
            Query typedQuery = em.createQuery(sql, Room.class);
            String sqlCount = "select count(r) from Room r where ";
            Query query = em.createQuery(sqlCount + sqlCondition);
            if (name != null) {
                typedQuery.setParameter("name", name);
                query.setParameter("name", name);
            }
            if (typeId != null) {
                typedQuery.setParameter("typeId", typeId);
                query.setParameter("typeId", typeId);
            }
            if (state == State.ON_PROCESS || state == State.ON_VACATE){
            typedQuery.setParameter("state", state);
            query.setParameter("state", state);
            }

            Long count = (Long)query.getSingleResult();

            currentPage = currentPage != null ? currentPage : 0;

            typedQuery.setFirstResult((int) (currentPage * size));

            if (currentPage * size > count) {
                typedQuery.setMaxResults((int) ((currentPage * size) - count));
            } else {
                typedQuery.setMaxResults(size);
            }

            List<Room> resultList = typedQuery.getResultList();

            result.setMessage("Successfully filtered");
            result.setSuccess(true);
            result.setData(resultList
                    .stream()
                    .map(Room::mapToDto)
                    .collect(Collectors.toList()));
            result.setTotalPages(count / size + 1);
            result.setTotalElements(count);
            result.setCurrentPage(currentPage);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Error in filter");
            result.setSuccess(false);
        }
        return result;
    }
}
