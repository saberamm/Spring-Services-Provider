package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    private OfferServiceImpl offerService;

    @BeforeEach
    public void setUp() {
        offerService = new OfferServiceImpl(offerRepository, null);
    }
    @Test
    public void testGetOffersSortedByTechnicianScore() {

        Order order = new Order();
        List<Offer> offers = createSampleOffers();
        order.setOfferList(offers);

        when(offerRepository.findAll()).thenReturn(offers);

        List<Offer> sortedOffers = offerService.getOffersSortedByTechnicianScore(order);

        assertEquals(4.5, sortedOffers.get(0).getTechnician().getOverallScore());
        assertEquals(4.0, sortedOffers.get(1).getTechnician().getOverallScore());
        assertEquals(3.5, sortedOffers.get(2).getTechnician().getOverallScore());
    }

    @Test
    public void testGetOffersSortedByPrice() {

        Order order = new Order();
        List<Offer> offers = createSampleOffers();
        order.setOfferList(offers);

        when(offerRepository.findAll()).thenReturn(offers);

        List<Offer> sortedOffers = offerService.getOffersSortedByPrice(order);


        assertEquals(80.0, sortedOffers.get(0).getSuggestionPrice());
        assertEquals(100.0, sortedOffers.get(1).getSuggestionPrice());
        assertEquals(120.0, sortedOffers.get(2).getSuggestionPrice());
    }

    private List<Offer> createSampleOffers() {
        List<Offer> offers = new ArrayList<>();

        Technician technician1 = new Technician();
        technician1.setOverallScore(4.5);

        Technician technician2 = new Technician();
        technician2.setOverallScore(4.0);

        Technician technician3 = new Technician();
        technician3.setOverallScore(3.5);

        Offer offer1 = new Offer();
        offer1.setSuggestionPrice(100.0);
        offer1.setTechnician(technician1);
        offers.add(offer1);

        Offer offer2 = new Offer();
        offer2.setSuggestionPrice(80.0);
        offer2.setTechnician(technician2);
        offers.add(offer2);

        Offer offer3 = new Offer();
        offer3.setSuggestionPrice(120.0);
        offer3.setTechnician(technician3);
        offers.add(offer3);

        return offers;
    }
}

