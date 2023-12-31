package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.repository.OfferRepository;
import com.example.servicesprovider.service.OfferService;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl extends BaseServiceImpl<Offer, Long, OfferRepository> implements OfferService {

    public OfferServiceImpl(OfferRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public List<Offer> getOffersSortedByTechnicianScore(Order order) {
        List<Offer> offers = order.getOfferList();

        return offers.stream()
                .sorted(Comparator.comparing(offer -> offer.getTechnician().getOverallScore(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Offer> getOffersSortedByPrice(Order order) {
        List<Offer> offers = order.getOfferList();

        return offers.stream()
                .sorted(Comparator.comparing(Offer::getSuggestionPrice))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Offer> filterOffersByCriteria(Long orderId, String storeBy) {
        return repository.findOffersByOrderAndStoreBy(orderId, storeBy);
    }
}