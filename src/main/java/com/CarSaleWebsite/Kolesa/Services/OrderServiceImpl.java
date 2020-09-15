package com.CarSaleWebsite.Kolesa.Services;

import com.CarSaleWebsite.Kolesa.Models.Order;
import com.CarSaleWebsite.Kolesa.Repositories.OrderRepository;
import com.CarSaleWebsite.Kolesa.Services.interfaces.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());

        return this.orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }

    @Override
    public List<Order> getMyOrder(String username) {
       return this.orderRepository.findAllByUser_Username(username);
    }

}