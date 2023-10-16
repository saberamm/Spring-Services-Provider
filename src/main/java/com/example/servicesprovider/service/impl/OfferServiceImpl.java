package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.repository.OfferRepository;
import com.example.servicesprovider.service.OfferService;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
@Service
public class OfferServiceImpl extends BaseServiceImpl<Offer, Long, OfferRepository> implements OfferService {

    public OfferServiceImpl(OfferRepository repository, Validator validator) {
        super(repository, validator);
    }
}