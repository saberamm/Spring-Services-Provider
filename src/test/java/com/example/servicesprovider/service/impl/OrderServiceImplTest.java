package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetOrderById() {

        Order sampleOrder = createSampleOrder();


        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));


        Order retrievedOrder = orderService.findById(1L);


        assertEquals(sampleOrder, retrievedOrder);
    }

    @Test
    public void testCreateOrder() {

        Order newOrder = createSampleOrder();


        when(orderRepository.save(newOrder)).thenReturn(newOrder);


        Order createdOrder = orderService.save(newOrder);


        assertEquals(newOrder, createdOrder);
    }

    @Test
    public void testUpdateOrder() {

        Order updatedOrder = createSampleOrder();
        updatedOrder.setOrderDescription("Updated Description");


        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);


        Order resultOrder = orderService.update(updatedOrder);


        assertEquals(updatedOrder, resultOrder);
    }

    private Order createSampleOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderPrice(100.0);
        order.setOrderDescription("Sample Order");
        order.setOrderAddress("123 Main St");

        return order;
    }

    private SubService createSampleSubService() {
        SubService subService = new SubService();
        subService.setId(1L);

        return subService;
    }

    private List<Order> createSampleOrders(SubService subService) {
        List<Order> orders = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Order order = new Order();
            order.setId((long) i);
            order.setSubService(subService);

            orders.add(order);
        }
        return orders;
    }
    @Test
    public void testFindBySubService() {

        SubService subService = createSampleSubService();


        List<Order> sampleOrders = createSampleOrders(subService);

        when(orderRepository.findAllBySubServiceId(subService.getId())).thenReturn(sampleOrders);


        List<Order> retrievedOrders = orderService.findBySubService(subService);


        assertEquals(sampleOrders, retrievedOrders);
    }


    @Test
    public void testFindBySubServiceWithException() {

        SubService subService = createSampleSubService();


        when(orderRepository.findAllBySubServiceId(subService.getId())).thenThrow(RuntimeException.class);


        assertThrows(RuntimeException.class, () -> orderService.findBySubService(subService));
    }

}
