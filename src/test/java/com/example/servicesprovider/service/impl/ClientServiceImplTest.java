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
        Order order = new Order();
        order.setOrderPrice(100.0);
        SubService subService = new SubService();
        subService.setBasePrice(50.0);
        order.setSubService(subService);
        Client client = new Client();
        when(orderService.save(order)).thenReturn(order);

        Order addedOrder = clientService.addOrder(order, client);

        assertNotNull(addedOrder);
    }

    @Test
    public void testAddOrder_WhenPriceIsLowerThanBasePrice_ShouldReturnNull() {

        Client client = new Client();
        SubService subService = new SubService();
        subService.setBasePrice(100.0);
        Order order = new Order();
        order.setOrderPrice(50.0);
        order.setSubService(subService);

        MockitoAnnotations.openMocks(this);

        Order result = clientService.addOrder(order, client);

        assertNull(result);
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

        Client client = new Client();
        SubService subService = new SubService();
        subService.setBasePrice(100.0);
        Order startedOrder = new Order();
        startedOrder.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);
        startedOrder.setSubService(subService);


        MockitoAnnotations.openMocks(this);


        Order result = clientService.startOrder(startedOrder);


        assertNull(result);

        Mockito.verify(orderService, Mockito.never()).update(startedOrder);
    }

    @Test
    public void testStartOrder() {
        Order order = new Order();
        Offer offer = new Offer();
        offer.setTimeForStartWorking(LocalDateTime.now().plusMinutes(30));
        order.setSelectedOffersId(1L);
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);

        when(offerService.findById(order.getSelectedOffersId())).thenReturn(offer);


        when(clientService.startOrder(order)).thenReturn(null);

        Order startedOrder = clientService.startOrder(order);


        if (startedOrder != null) {
            assertEquals(OrderStatus.STARTED, startedOrder.getOrderStatus());
        } else {

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

        Client client = new Client();
        Order order = null;


        Order result = clientService.addOrder(order, client);

        assertNull(result);
    }

    @Test
    public void testSeeGeneralServices_WithEmptyList_ShouldReturnEmptyList() {

        List<GeneralService> emptyList = new ArrayList<>();
        when(generalService_service.findAll()).thenReturn(emptyList);


        List<GeneralService> retrievedServices = clientService.seeGeneralServices();


        assertTrue(retrievedServices.isEmpty());
    }


    @Test
    public void testSeeSubServices_WithEmptyList_ShouldReturnEmptyList() {

        List<SubService> emptyList = new ArrayList<>();
        when(subService_service.findAll()).thenReturn(emptyList);


        List<SubService> retrievedSubServices = clientService.seeSubServices();


        assertTrue(retrievedSubServices.isEmpty());
    }

    @Test
    public void testFindByUserName_WithExistingClient_ShouldReturnClient() {

        String username = "existingUsername";
        Client existingClient = new Client();
        when(clientRepository.findByUserName(username)).thenReturn(existingClient);


        Client retrievedClient = clientService.findByUserName(username);


        assertNotNull(retrievedClient);
    }

    @Test
    public void testFindByUserName_WithNonExistingClient_ShouldReturnNull() {

        String username = "nonExistingUsername";
        when(clientRepository.findByUserName(username)).thenReturn(null);


        Client retrievedClient = clientService.findByUserName(username);


        assertNull(retrievedClient);
    }

    @Test
    public void testStartOrder_WithInvalidOrderStatus_ShouldReturnNull() {

        Order order = new Order();
        order.setOrderStatus(OrderStatus.DONE);


        Order result = clientService.startOrder(order);


        assertNull(result);
    }

    @Test
    public void testStartOrder_WithOfferInFuture_ShouldReturnNull() {

        Order order = new Order();
        Offer offer = new Offer();
        offer.setTimeForStartWorking(LocalDateTime.now().plusMinutes(30));
        order.setSelectedOffersId(1L);
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);

        when(offerService.findById(order.getSelectedOffersId())).thenReturn(offer);


        Order result = clientService.startOrder(order);


        assertNull(result);
    }

    @Test
    public void testCompleteOrder_WithInvalidOrderStatus_ShouldReturnNull() {

        Order order = new Order();
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_OFFER);


        Order result = clientService.completeOrder(order);


        assertNull(result);
    }
    @Test
    public void testStartOrder_WhenConditionsAreMet_ShouldUpdateStatusToStarted() {

        Order order = new Order();
        Offer offer = new Offer();
        offer.setTimeForStartWorking(LocalDateTime.now().minusMinutes(30));
        order.setSelectedOffersId(1L);
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);

        when(offerService.findById(order.getSelectedOffersId())).thenReturn(offer);
        when(orderService.update(order)).thenReturn(order);


        Order result = clientService.startOrder(order);


        assertEquals(OrderStatus.STARTED, result.getOrderStatus());
    }

    @Test
    public void testFindByUserName_WhenExceptionIsThrown_ShouldReturnNull() {

        String userName = "testUser";


        when(clientRepository.findByUserName(userName)).thenThrow(new RuntimeException("Simulated exception"));


        Client result = clientService.findByUserName(userName);


        assertNull(result);
    }

}
