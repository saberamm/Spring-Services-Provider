package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.CreditCard;
import com.example.servicesprovider.repository.CreditCardRepository;
import com.example.servicesprovider.service.CreditCardService;

import javax.validation.Validator;

public class CreditCardServiceImpl extends BaseServiceImpl<CreditCard, Long, CreditCardRepository> implements CreditCardService {
    public CreditCardServiceImpl(CreditCardRepository repository, Validator validator) {
        super(repository, validator);
    }
}
