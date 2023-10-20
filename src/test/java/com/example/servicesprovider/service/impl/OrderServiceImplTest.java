package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

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
    private Validator validator;  // Initialize the validator

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetOrderById() {
        // Create a sample order
        Order sampleOrder = createSampleOrder();

        // Mock the repository to return the sample order
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        // Retrieve the order by ID
        Order retrievedOrder = orderService.findById(1L);

        // Verify that the retrieved order matches the sample order
        assertEquals(sampleOrder, retrievedOrder);
    }

    @Test
    public void testCreateOrder() {
        // Create a sample order to be saved
        Order newOrder = createSampleOrder();

        // Mock the repository to return the saved order
        when(orderRepository.save(newOrder)).thenReturn(newOrder);

        // Create the order
        Order createdOrder = orderService.save(newOrder);

        // Verify that the created order matches the sample order
        assertEquals(newOrder, createdOrder);
    }

    @Test
    public void testUpdateOrder() {
        // Create a sample order with an updated description
        Order updatedOrder = createSampleOrder();
        updatedOrder.setOrderDescription("Updated Description");

        // Mock the repository to return the updated order
        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        // Update the order
        Order resultOrder = orderService.update(updatedOrder);

        // Verify that the returned order matches the updated order
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
        // Set other properties as needed
        return subService;
    }

    private List<Order> createSampleOrders(SubService subService) {
        List<Order> orders = new ArrayList<>();
        // Create multiple sample orders with the same SubService
        for (int i = 1; i <= 3; i++) {
            Order order = new Order();
            order.setId((long) i);
            order.setSubService(subService);
            // Set other properties as needed
            orders.add(order);
        }
        return orders;
    }
    @Test
    public void testFindBySubService() {
        // Create a sample SubService
        SubService subService = createSampleSubService();

        // Create a list of sample orders
        List<Order> sampleOrders = createSampleOrders(subService);

        // Mock the repository to return the list of orders for the SubService
        when(orderRepository.findAllBySubServiceId(subService.getId())).thenReturn(sampleOrders);

        // Retrieve orders by SubService
        List<Order> retrievedOrders = orderService.findBySubService(subService);

        // Verify that the retrieved orders match the sample orders
        assertEquals(sampleOrders, retrievedOrders);
    }


    @Test
    public void testFindBySubServiceWithException() {
        // Create a sample SubService
        SubService subService = createSampleSubService();

        // Mock the repository to throw a DataAccessException
        when(orderRepository.findAllBySubServiceId(subService.getId())).thenThrow(RuntimeException.class);

        // Use assertThrows to verify that a DataAccessException is thrown
        assertThrows(RuntimeException.class, () -> orderService.findBySubService(subService));
    }

}
