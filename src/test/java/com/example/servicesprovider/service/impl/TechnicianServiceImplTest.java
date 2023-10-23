package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.TechnicianRepository;
import com.example.servicesprovider.service.OfferService;
import com.example.servicesprovider.service.OrderService;
import com.example.servicesprovider.service.SubService_Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class TechnicianServiceImplTest {

    @Mock
    private TechnicianRepository technicianRepository;

    @Mock
    private OfferService offerService;

    @Mock
    private OrderService orderService;

    @Mock
    private SubService_Service subServiceService;

    @InjectMocks
    private TechnicianServiceImpl technicianService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUserName() {

        Technician technician = new Technician();
        technician.setUserName("TestTechnician");


        Mockito.when(technicianRepository.findByUserName("TestTechnician"))
                .thenReturn(technician);


        Technician result = technicianService.findByUserName("TestTechnician");


        assertNotNull(result);
        assertEquals("TestTechnician", result.getUserName());
    }

    @Test
    public void testDeleteByUserName() {

        assertDoesNotThrow(() -> technicianService.deleteByUserName("TestTechnician"));

        Mockito.verify(technicianRepository, Mockito.times(1)).deleteByUserName("TestTechnician");
    }

    @Test
    public void testAddOffer() {

        Technician technician = new Technician();
        technician.setTechnicianStatus(TechnicianStatus.CONFIRMED);


        Order order = new Order();
        order.setWorkTime(LocalDateTime.now());

        Offer offer = new Offer();
        offer.setOrder(order);
        offer.setTimeForStartWorking(LocalDateTime.now().plusHours(1));


        Mockito.when(offerService.save(any(Offer.class))).thenReturn(offer);

        Mockito.when(orderService.update(any(Order.class))).thenReturn(order);


        Offer result = technicianService.addOffer(offer, technician);


        assertNotNull(result);
        assertEquals(OrderStatus.WAITING_FOR_CHOOSING_TECHNICIAN, order.getOrderStatus());
    }


    @Test
    public void testOrdersThatTechnicianCanOffer() {

        Technician technician = new Technician();

        SubService subService = new SubService();


        Order order1 = new Order();
        order1.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_OFFER);
        order1.setSubService(subService);

        Order order2 = new Order();
        order2.setOrderStatus(OrderStatus.WAITING_FOR_CHOOSING_TECHNICIAN);
        order2.setSubService(subService);


        Mockito.when(subServiceService.findSubServicesByTechnicianId(eq(technician.getId())))
                .thenReturn(Arrays.asList(subService));


        Mockito.when(orderService.findBySubService(eq(subService)))
                .thenReturn(Arrays.asList(order1, order2));

        List<Order> result = technicianService.OrdersThatTechnicianCanOffer(technician);


        assertNotNull(result);
        assertEquals(2, result.size());
    }


    @Test
    public void testNotConfirmedYet() {

        List<Technician> techniciansNew = new ArrayList<>();
        techniciansNew.add(new Technician());
        techniciansNew.add(new Technician());

        List<Technician> techniciansPending = new ArrayList<>();
        techniciansPending.add(new Technician());


        Mockito.when(technicianRepository.findAllByTechnicianStatus(TechnicianStatus.NEW))
                .thenReturn(techniciansNew);
        Mockito.when(technicianRepository.findAllByTechnicianStatus(TechnicianStatus.PENDING_CONFIRMATION))
                .thenReturn(techniciansPending);


        List<Technician> result = technicianService.notConfirmedYet();


        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testAddOfferWithOfferTimeBeforeOrderTimeException() {

        Technician technician = new Technician();
        technician.setTechnicianStatus(TechnicianStatus.CONFIRMED);


        Order order = new Order();
        order.setWorkTime(LocalDateTime.now());


        Offer offer = new Offer();
        offer.setOrder(order);
        offer.setTimeForStartWorking(LocalDateTime.now().minusHours(1));


        assertThrows(Exception.class, () -> technicianService.addOffer(offer, technician));
    }

    @Test
    public void testAddOfferWithTechnicianNotConfirmedYetException() {

        Technician technician = new Technician();
        technician.setTechnicianStatus(TechnicianStatus.PENDING_CONFIRMATION);


        Order order = new Order();
        order.setWorkTime(LocalDateTime.now());


        Offer offer = new Offer();
        offer.setOrder(order);
        offer.setTimeForStartWorking(LocalDateTime.now().plusHours(1));


        assertThrows(Exception.class, () -> technicianService.addOffer(offer, technician));
    }

    @Test
    public void testFindByUserNameWithTechnicianNotFoundException() {

        String username = "nonexistent_username";


        Mockito.when(technicianRepository.findByUserName(username))
                .thenReturn(null);


        assertThrows(Exception.class, () -> technicianService.findByUserName(username));
    }
}
