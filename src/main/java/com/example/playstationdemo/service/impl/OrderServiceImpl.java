package com.example.playstationdemo.service.impl;

import com.example.playstationdemo.entity.Order;
import com.example.playstationdemo.entity.OrderDetail;
import com.example.playstationdemo.entity.enums.State;
import com.example.playstationdemo.payload.ApiResponse;
import com.example.playstationdemo.payload.OrderDetailDto;
import com.example.playstationdemo.payload.OrderReport;
import com.example.playstationdemo.payload.OrderResultDto;
import com.example.playstationdemo.repository.OrderDetailRepository;
import com.example.playstationdemo.repository.OrderRepository;
import com.example.playstationdemo.repository.OrderResultRepository;
import com.example.playstationdemo.repository.RoomRepository;
import com.example.playstationdemo.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final OrderResultRepository orderResultRepository;

    private final RoomRepository roomRepository;

    private final EntityManager em;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, OrderResultRepository orderResultRepository, RoomRepository roomRepository, EntityManager em) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderResultRepository = orderResultRepository;
        this.roomRepository = roomRepository;
        this.em = em;
    }

    @Override
    @Transactional
    public ApiResponse save(Long id) {
        ApiResponse result = new ApiResponse();
        try {

            String onProcess = roomRepository.isOk(id);
                if (onProcess.equals("process")){
                    result.setMessage("Room is not Empty");
                    result.setSuccess(true);
                    return result;
                } else if (onProcess.equals("deleted")){
                    result.setMessage("Room is deleted");
                    result.setSuccess(true);
                    return result;
                }
            Order order = new Order();
            orderRepository.save(order.start(id));
            roomRepository.saveState(id, State.ON_PROCESS);
            result.setSuccess(true);
            result.setMessage("Order Successfully started");
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Could not save Order");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse saveProduct(List<OrderDetailDto> dtoList) {
        ApiResponse result = new ApiResponse();
        try {
            for (OrderDetailDto dto : dtoList) {
                OrderDetail detail = dto.mapToEntity();
                orderDetailRepository.save(detail);
            }
                result.setMessage("Products Successfully saved");
                result.setSuccess(true);
                return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Could not save product");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    @Transactional
    public ApiResponse finish(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            Order order = orderRepository.findById(id).orElse(null);
            if (order !=  null){
                if (order.getState() == State.ON_VACATE){
                    result.setMessage("This Order already finished");
                    result.setSuccess(true);
                    return result;
                }

                order.finish();
                System.out.println(order.getRoom().getId());


                OrderResultDto dto = new OrderResultDto();
                dto.setOrder(order.mapToDto());

                Long finish = order.getFinishedAt().atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();
                Long start = order.getStartAt().atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

                double divergence = (finish - start) / 1000.0;

                double roomSum = order.getRoom().getType().getPrice() * (divergence / 3600.0);

                Double productSum = orderResultRepository.getProductSum(id);

                productSum = productSum == null ? 0 : productSum;

                dto.setRoomSum(Math.round(roomSum));
                dto.setProductSum(Math.round(productSum));
                dto.setTotalSum(Math.round(productSum + roomSum));

                roomRepository.saveState(order.getRoom().getId(), State.ON_VACATE);
                orderResultRepository.save(dto.mapToEntity());

                result.setMessage("Successfully finished");
                result.setSuccess(true);
                result.setData(dto);
            } else {
                result.setMessage("Order not found");
                result.setSuccess(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Could not finish");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse result(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            Optional<Order> order = orderRepository.findById(id);

            if (order.isPresent()){
                if (order.get().getState() == State.ON_VACATE){
                    result.setMessage("This Order already finished");
                    result.setSuccess(true);
                    return result;
                }
                OrderResultDto dto = new OrderResultDto();

                dto.setOrder(order.get().mapToDto());

                Long now = LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

                Long start = order.get().getStartAt().atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli();

                double divergence = (now - start) / 1000.0;

                double roomSum = order.get().getRoom().getType().getPrice() * (divergence / 3600.0);

                Double productSum = orderResultRepository.getProductSum(id);

                System.out.println(productSum);

                productSum = productSum == null ? 0 : productSum;

                dto.setRoomSum(Math.round(roomSum));
                dto.setProductSum(Math.round(productSum));
                dto.setTotalSum(Math.round(productSum + roomSum));

                result.setMessage("Successfully came!");
                result.setSuccess(true);
                result.setData(dto);
            } else {
                result.setMessage("Order not found!");
                result.setSuccess(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Could not resolve");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse detail(Long id) {
        ApiResponse result = new ApiResponse();
        try {
            List<OrderDetail> details = orderDetailRepository.findDetails(id);

            result.setMessage("Successfully came!");
            result.setSuccess(true);
            result.setData(details
                    .stream()
                    .map(OrderDetail::mapToDto)
                    .collect(Collectors.toList()));
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on get details");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse report(Date fromDate, Date toDate) {
        ApiResponse result = new ApiResponse();
        try {
            String sql = "select cast(o.finishedAt as date) as sana, sum(ors.TotalSum) as summa, count(o) as countOrder from orders o\n" +
                    "join OrderResult ors on  ors.order.id = o.id\n" +
                    "where o.state = 'ON_VACATE' ";
            StringBuilder sqlCondition = new StringBuilder();
            if (fromDate == null && toDate == null){
                sqlCondition.append("and cast(o.finishedAt as date) = cast(NOW() as date)");
            }

            if (fromDate != null){
                sqlCondition.append(" and cast(o.finishedAt as date) >= :fromDate");
            }

            if (toDate != null){
                sqlCondition.append(" and cast(o.finishedAt as date) <= :toDate");
            }

            sqlCondition.append(" group by sana order by sana ASC");

            sql += sqlCondition;

            Query query = em.createQuery(sql);

            if (fromDate != null){
                query.setParameter("fromDate", fromDate);
            }

            if (toDate != null){
                query.setParameter("toDate", toDate);
            }

            List<Object[]> resultList = query.getResultList();

            List<OrderReport> reportList = new ArrayList<>();

            for (Object[] o : resultList) {
                OrderReport orderReport = new OrderReport();
                orderReport.setSana((Date) o[0]);
                orderReport.setSumma((Long) o[1]);
                orderReport.setCountOrder((Long) o[2]);
                reportList.add(orderReport);
            }

            result.setMessage("Successfully came reports");
            result.setSuccess(true);
            result.setData(reportList);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on coming orders");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ApiResponse reportDate(Date date) {
        ApiResponse result = new ApiResponse();
        try {
            List<Object[]> objects = orderRepository.getReport(date);
            List<Map> list = new ArrayList<>();
            Map map = new HashMap();
            for (Object[] object : objects) {
                map.put("id", object[0]);
                map.put("startAt", object[1]);
                map.put("finishAt", object[2]);
                map.put("productSum", object[3]);
                map.put("roomSum", object[4]);
                map.put("totalSum", object[5]);
                list.add(map);
            }
            result.setMessage("Successfully came!");
            result.setSuccess(true);
            result.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage("Error on coming result");
            result.setSuccess(false);
        }
        return result;
    }
}
