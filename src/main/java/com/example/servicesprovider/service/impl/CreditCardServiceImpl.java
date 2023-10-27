package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.CreditCard;
import com.example.servicesprovider.repository.CreditCardRepository;
import com.example.servicesprovider.service.CreditCardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl extends BaseServiceImpl<CreditCard, Long, CreditCardRepository> implements CreditCardService {
    public CreditCardServiceImpl(CreditCardRepository repository) {
        super(repository);
    }

    @Override
    public CreditCard findByCreditCardNumber(String creditCardNumber) {
        CreditCard creditCard = repository.findByCreditCardNumber(creditCardNumber);
        if (creditCard == null) throw new EntityNotFoundException("Model not exist");
        return creditCard;
    }
}
