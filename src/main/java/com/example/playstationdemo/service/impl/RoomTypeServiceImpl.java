package com.example.playstationdemo.service.impl;

import com.example.playstationdemo.entity.RoomType;
import com.example.playstationdemo.payload.ApiResponse;
import com.example.playstationdemo.payload.RoomTypeDto;
import com.example.playstationdemo.repository.RoomTypeRepository;
import com.example.playstationdemo.service.RoomTypeService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    @PersistenceContext
    private final EntityManager em;

    public RoomTypeServiceImpl(@Lazy RoomTypeRepository roomTypeRepository, EntityManager em) {
        this.roomTypeRepository = roomTypeRepository;
        this.em = em;
    }

    @Override
    public ApiResponse save(RoomTypeDto dto) {
        ApiResponse result = new ApiResponse();
        try {
            RoomType roomType = new RoomType();
            roomType.setName(dto.getName());
            roomType.setPrice(dto.getPrice());
            roomTypeRepository.save(roomType);
            result.setMessage("RoomType Successfully saved!");
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on saving roomType, please try again");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse edit(RoomTypeDto dto) {
        ApiResponse result = new ApiResponse();
        try {
            RoomType roomType = roomTypeRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("room type not found"));
            roomType.setPrice(dto.getPrice());
            roomType.setName(dto.getName());
            roomTypeRepository.save(roomType);
            result.setMessage("Successfully edited!");
            result.setSuccess(true);
        }catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("roomType not found");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on Editing roomType, please try again!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse remove(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            RoomType type = roomTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("room type not found"));
            type.setDeleted(true);
            roomTypeRepository.save(type);
            result.setMessage("Deleted");
            result.setSuccess(true);
        }catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("room type not found");
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse("Error on removing element", false);
        }
        return result;
    }

    @Override
    public ApiResponse getAll() {
        ApiResponse result = new ApiResponse();
        try {
            List<RoomType> types = roomTypeRepository.findAll();
            result.setMessage("Ok");
            result.setSuccess(true);
            result.setData(types.stream().map(RoomType::mapToDto).collect(Collectors.toList()));
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Could not come room type");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse filter(String name, Double price, Integer page, Integer size, Long currentPage) {
        ApiResponse result = new ApiResponse();
        try {
            String sql = "select r from RoomType r where ";
            StringBuilder sqlCondition = new StringBuilder();
            if (name != null){
                sqlCondition.append("r.name like CONCAT(:name, '%') ");
            }
            if (sqlCondition.length() != 0){
                sqlCondition.append(" and ");
            }
            if (price != null){
                sqlCondition.append("r.price = :price and ");
            }
            sqlCondition.append("r.isDeleted = false");
            sql += sqlCondition;
            Query query = em.createQuery(sql, RoomType.class);
            String sqlCount = "select count(r) from RoomType r where ";
            Query query1 = em.createQuery(sqlCount + sqlCondition);
            if (name != null){
                query.setParameter("name", name);
                query1.setParameter("name", name);
            }
            if (price != null){
                query.setParameter("price", price);
                query1.setParameter("price", price);
            }

            Long count = (Long) query1.getSingleResult();

            currentPage = currentPage != null ? currentPage : 0;

            query.setFirstResult((int) (currentPage * size));

            if (currentPage * size > count) {
                query.setMaxResults((int) ((currentPage * size) - count));
            } else {
                query.setMaxResults(size);
            }

            List<RoomType> resultList = query.getResultList();
            result.setMessage("Successfully filtered");
            result.setSuccess(true);
            result.setData(resultList
                            .stream()
                            .map(RoomType::mapToDto)
                            .collect(Collectors.toList()));
            result.setTotalPages(count / size + 1);
            result.setTotalElements(count);
            result.setCurrentPage(currentPage);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error in filter");
            result.setSuccess(false);
        }
        return result;
    }
}
