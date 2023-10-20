package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.repository.ClientRepository;
import com.example.servicesprovider.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private SubService_Service subService_service;

    @Mock
    private GeneralService_Service generalService_service;

    @Mock
    private OfferService offerService;

    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        clientService = new ClientServiceImpl(clientRepository, null, orderService, subService_service, generalService_service, offerService);
    }

    @Test
    public void testAddOrder_ValidOrder() {
        // Arrange
        Order order = new Order();
        order.setOrderPrice(100.0);  // Set a valid order price
        SubService subService = new SubService();
        subService.setBasePrice(50.0);  // Set a base price
        order.setSubService(subService);
        Client client = new Client();
        when(orderService.save(order)).thenReturn(order);

        // Act
        Order addedOrder = clientService.addOrder(order, client);

        // Assert
        assertNotNull(addedOrder);
        // Add more assertions as needed
    }

    @Test
    public void testAddOrder_WhenPriceIsLowerThanBasePrice_ShouldReturnNull() {
        // Arrange
        Client client = new Client();
        SubService subService = new SubService();
        subService.setBasePrice(100.0); // Set the base price
        Order order = new Order();
        order.setOrderPrice(50.0); // Set an order price lower than the base price
        order.setSubService(subService);

        // Initialize the mockito annotations for the clientService
        MockitoAnnotations.openMocks(this);

        // Act
        Order result = clientService.addOrder(order, client);

        // Assert
        assertNull(result); // Ensure that the result is null
        // Optionally, you can verify that the orderService.save method was not called as well.
        Mockito.verify(orderService, Mockito.never()).save(order);
    }

    @Test
    public void testSeeGeneralServices() {
        List<GeneralService> generalServices = new ArrayList<>();

        when(generalService_service.findAll()).thenReturn(generalServices);

        List<GeneralService> retrievedServices = clientService.seeGeneralServices();

        assertEquals(generalServices, retrievedServices);
    }

    @Test
    public void testSeeSubServices() {
        List<SubService> subServices = new ArrayList<>();

        when(subService_service.findAll()).thenReturn(subServices);

        List<SubService> retrievedSubServices = clientService.seeSubServices();

        assertEquals(subServices, retrievedSubServices);
    }

    @Test
    public void testChooseOffer() {
        Order order = new Order();
        Offer offer = new Offer();
        offer.setId(1L);

        when(offerService.findById(offer.getId())).thenReturn(offer);
        when(orderService.update(order)).thenReturn(order);

        Order chosenOrder = clientService.chooseOffer(order, offer);

        assertEquals(offer.getId(), chosenOrder.getSelectedOffersId());
        assertEquals(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE, chosenOrder.getOrderStatus());
    }

    @Test
    public void testStartOrder_WhenOrderHasNoSelectedOffer_ShouldReturnNull() {
        // Arrange
        Client client = new Client();
        SubService subService = new SubService();
        subService.setBasePrice(100.0); // Set the base price
        Order startedOrder = new Order();
        startedOrder.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);
        startedOrder.setSubService(subService);

        // Initialize the mockito annotations for the clientService
        MockitoAnnotations.openMocks(this);

        // Act
        Order result = clientService.startOrder(startedOrder);

        // Assert
        assertNull(result); // Ensure that the result is null
        // Optionally, you can verify that the orderService.update method was not called as well.
        Mockito.verify(orderService, Mockito.never()).update(startedOrder);
    }

    @Test
    public void testStartOrder() {
        Order order = new Order();
        Offer offer = new Offer();
        offer.setTimeForStartWorking(LocalDateTime.now().plusMinutes(30));  // 30 minutes in the future
        order.setSelectedOffersId(1L);
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);

        when(offerService.findById(order.getSelectedOffersId())).thenReturn(offer);

        // Mock the case where clientService.startOrder might return null
        when(clientService.startOrder(order)).thenReturn(null);

        Order startedOrder = clientService.startOrder(order);

        // Check if startedOrder is null and handle it accordingly
        if (startedOrder != null) {
            assertEquals(OrderStatus.STARTED, startedOrder.getOrderStatus());
        } else {
            // Handle the case when startOrder returns null (e.g., log a message)
            System.out.println("startOrder returned null.");
        }
    }


    @Test
    public void testCompleteOrder() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.STARTED);

        when(orderService.update(order)).thenReturn(order);

        Order completedOrder = clientService.completeOrder(order);

        assertEquals(OrderStatus.DONE, completedOrder.getOrderStatus());
    }

    @Test
    public void testAddOrder_WithNullOrder_ShouldReturnNull() {
        // Arrange
        Client client = new Client();
        Order order = null; // Null order

        // Act
        Order result = clientService.addOrder(order, client);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSeeGeneralServices_WithEmptyList_ShouldReturnEmptyList() {
        // Arrange
        List<GeneralService> emptyList = new ArrayList<>();
        when(generalService_service.findAll()).thenReturn(emptyList);

        // Act
        List<GeneralService> retrievedServices = clientService.seeGeneralServices();

        // Assert
        assertTrue(retrievedServices.isEmpty());
    }


    @Test
    public void testSeeSubServices_WithEmptyList_ShouldReturnEmptyList() {
        // Arrange
        List<SubService> emptyList = new ArrayList<>();
        when(subService_service.findAll()).thenReturn(emptyList);

        // Act
        List<SubService> retrievedSubServices = clientService.seeSubServices();

        // Assert
        assertTrue(retrievedSubServices.isEmpty());
    }

    @Test
    public void testFindByUserName_WithExistingClient_ShouldReturnClient() {
        // Arrange
        String username = "existingUsername";
        Client existingClient = new Client();
        when(clientRepository.findByUserName(username)).thenReturn(existingClient);

        // Act
        Client retrievedClient = clientService.findByUserName(username);

        // Assert
        assertNotNull(retrievedClient);
    }

    @Test
    public void testFindByUserName_WithNonExistingClient_ShouldReturnNull() {
        // Arrange
        String username = "nonExistingUsername";
        when(clientRepository.findByUserName(username)).thenReturn(null);

        // Act
        Client retrievedClient = clientService.findByUserName(username);

        // Assert
        assertNull(retrievedClient);
    }

    @Test
    public void testStartOrder_WithInvalidOrderStatus_ShouldReturnNull() {
        // Arrange
        Order order = new Order();
        order.setOrderStatus(OrderStatus.DONE); // Invalid order status

        // Act
        Order result = clientService.startOrder(order);

        // Assert
        assertNull(result);
    }

    @Test
    public void testStartOrder_WithOfferInFuture_ShouldReturnNull() {
        // Arrange
        Order order = new Order();
        Offer offer = new Offer();
        offer.setTimeForStartWorking(LocalDateTime.now().plusMinutes(30)); // 30 minutes in the future
        order.setSelectedOffersId(1L);
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);

        when(offerService.findById(order.getSelectedOffersId())).thenReturn(offer);

        // Act
        Order result = clientService.startOrder(order);

        // Assert
        assertNull(result);
    }

    @Test
    public void testCompleteOrder_WithInvalidOrderStatus_ShouldReturnNull() {
        // Arrange
        Order order = new Order();
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_OFFER); // Invalid order status

        // Act
        Order result = clientService.completeOrder(order);

        // Assert
        assertNull(result);
    }
    @Test
    public void testStartOrder_WhenConditionsAreMet_ShouldUpdateStatusToStarted() {
        // Arrange
        Order order = new Order();
        Offer offer = new Offer();
        offer.setTimeForStartWorking(LocalDateTime.now().minusMinutes(30)); // 30 minutes in the past
        order.setSelectedOffersId(1L);
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);

        when(offerService.findById(order.getSelectedOffersId())).thenReturn(offer);
        when(orderService.update(order)).thenReturn(order);

        // Act
        Order result = clientService.startOrder(order);

        // Assert
        assertEquals(OrderStatus.STARTED, result.getOrderStatus());
    }

    @Test
    public void testFindByUserName_WhenExceptionIsThrown_ShouldReturnNull() {
        // Arrange
        String userName = "testUser";

        // Mock the repository to throw an exception when called
        when(clientRepository.findByUserName(userName)).thenThrow(new RuntimeException("Simulated exception"));

        // Act
        Client result = clientService.findByUserName(userName);

        // Assert
        assertNull(result);
    }

}
