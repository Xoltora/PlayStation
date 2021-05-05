package com.example.playstationdemo.service.impl;

import com.example.playstationdemo.entity.Category;
import com.example.playstationdemo.entity.User;
import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.CategoryDto;
import com.example.playstationdemo.repository.CategoryRepository;
import com.example.playstationdemo.service.CategoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final EntityManager em;

    public CategoryServiceImpl(@Lazy CategoryRepository categoryRepository, EntityManager em) {
        this.categoryRepository = categoryRepository;
        this.em = em;
    }

    @Override
    public ApiResponse edit(CategoryDto dto, User currentUser) {
        ApiResponse result = new ApiResponse();
        try {
            Category category = categoryRepository.findById(dto.getId()).orElseThrow(() -> new IllegalStateException("category not found"));
            category.setName(dto.getName());
            categoryRepository.save(category);
            result.setMessage("Successfully edited!");
            result.setSuccess(true);
        }catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Category not found id = " + dto.getId());
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Something wrong on editing!!!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse save(CategoryDto dto, User currentUser) {
        ApiResponse result = new ApiResponse();
        try {
            categoryRepository.save(dto.mapToEntity(currentUser));
            result.setMessage("Successfully saved!");
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Something wrong on saving!!!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse all(Integer page, Integer size) {
        ApiResponse result = new ApiResponse();
        try {
            List<Category> categories = categoryRepository.findAll();
            result.setMessage("Ok");
            result.setSuccess(true);
            result.setData(categories
                    .stream()
                    .map(Category::mapToDto)
                    .collect(Collectors.toList()));
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on coming categories!!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse getById(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("category with " + id + " id not found"));
            result.setMessage("Ok");
            result.setSuccess(true);
            result.setData(category.mapToDto());
        }catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Category not found");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on coming categories!!!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse remove(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category not found"));
            category.setDeleted(true);
            categoryRepository.save(category);
            result.setMessage("Deleted");
            result.setSuccess(true);
        }catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Category not found");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on deleting category ID = " + id);
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse filter(String name, Integer page, Integer size, Long currentPage) {
        ApiResponse result = new ApiResponse();
        try {
            String sql = "select c from Category c where c.isDeleted = false ";
            StringBuilder sqlCondition = new StringBuilder();
            if (name != null){
                sqlCondition.append("and c.name like CONCAT(:name, '%')");
            }

            Query typedQuery = em.createQuery(sql + sqlCondition, Category.class);

            String sqlCount = "select count (c) FROM Category c WHERE c.isDeleted = false ";

            Query query = em.createQuery(sqlCount + sqlCondition);

            if (name != null){
                typedQuery.setParameter("name", name);
                query.setParameter("name", name);
            }
            Long count = (Long)query.getSingleResult();

            currentPage = currentPage != null ? currentPage : 0;

            typedQuery.setFirstResult((int) (currentPage * size));

            if (currentPage * size > count) {
                typedQuery.setMaxResults((int) ((currentPage * size) - count));
            } else {
                typedQuery.setMaxResults(size);
            }

            List<Category> resultList = typedQuery.getResultList();

            result.setMessage("Successfully filtered");
            result.setSuccess(true);
            result.setData(resultList
                            .stream()
                            .map(Category::mapToDto)
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
