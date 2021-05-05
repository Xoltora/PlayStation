package com.example.playstationdemo.service.impl;

import com.example.playstationdemo.entity.Product;
import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.ProductDto;
import com.example.playstationdemo.repository.ProductRepository;
import com.example.playstationdemo.service.ProductService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final EntityManager em;

    public ProductServiceImpl(ProductRepository productRepository, EntityManager em) {
        this.productRepository = productRepository;
        this.em = em;
    }

    @Override
    public ApiResponse save(ProductDto dto) {
        ApiResponse result = new ApiResponse();
        try {
            productRepository.save(dto.mapToEntity());
            result.setMessage("Product successfully saved");
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Cannot saving new product, please try again");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse edit(ProductDto dto) {
        ApiResponse result = new ApiResponse();
        try {
            Product product = productRepository.findById(dto.getId()).orElseThrow(() -> new IllegalStateException("product not found id = " + dto.getId()));
            productRepository.save(dto.mapToEntity(product));
            result.setMessage("Product successfully edited");
            result.setSuccess(true);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Product not found");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Cannot edit product");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse remove(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("product not found"));
            product.setDeleted(true);
            productRepository.save(product);
            result.setMessage("Product Successfully deleted");
            result.setSuccess(true);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Product not found");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Could not removed product, ERROR");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse getAll(Integer page, Integer size) {
        ApiResponse result = new ApiResponse();
        try {
            List<Product> products = productRepository.findAll();
            result.setMessage("Product successfully came");
            result.setSuccess(true);
            result.setData(products
                    .stream()
                    .map(Product::mapToDto)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Cannot get products!!!");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse getById(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Could not find product"));
            result.setMessage("Product Came!");
            result.setSuccess(true);
            result.setData(product.mapToDto());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Product not found");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("Cannot get product with thid id = " + id);
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse filter(String name, Double fromPrice, Double toPrice, Long categoryId, Integer page, Integer size, Long currentPage) {
        ApiResponse result = new ApiResponse();
        try {

            String sql = "select p from Product p where ";
            StringBuilder sqlCondition = new StringBuilder();
            if (name != null) {
                sqlCondition.append("p.name like CONCAT(:name, '%') and ");
            }
            if (fromPrice != null) {
                sqlCondition.append("p.price >= :fromPrice and ");
            }
            if (toPrice != null) {
                sqlCondition.append("p.price <= :toPrice and ");
            }

            if (categoryId != null) {
                sqlCondition.append("p.category.id = :categoryId and ");
            }

            sqlCondition.append("p.isDeleted = false");
            sql += sqlCondition;

            Query typedQuery = em.createQuery(sql, Product.class);

            String sqlCount = "select count (p) from Product p where " + sqlCondition;

            Query query = em.createQuery(sqlCount);
            if (name != null) {
                typedQuery.setParameter("name", name);
                query.setParameter("name", name);
            }
            if (fromPrice != null) {
                typedQuery.setParameter("fromPrice", fromPrice);
                query.setParameter("fromPrice", fromPrice);
            }
            if (toPrice != null) {
                typedQuery.setParameter("toPrice", toPrice);
                query.setParameter("toPrice", toPrice);
            }
            if (categoryId != null) {
                typedQuery.setParameter("categoryId", categoryId);
                query.setParameter("categoryId", categoryId);
            }

            Long count = (Long) query.getSingleResult();

            currentPage = currentPage != null ? currentPage : 0;

            typedQuery.setFirstResult((int) (currentPage * size));

            if (currentPage * size > count) {
                typedQuery.setMaxResults((int) ((currentPage * size) - count));
            } else {
                typedQuery.setMaxResults(size);
            }

            List<Product> resultList = typedQuery.getResultList();

            result.setMessage("Successfully filtered");
            result.setSuccess(true);
            result.setData(resultList
                    .stream()
                    .map(Product::mapToDto)
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
