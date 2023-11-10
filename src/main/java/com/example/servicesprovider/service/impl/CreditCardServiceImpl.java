package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.CreditCard;
import com.example.servicesprovider.repository.CreditCardRepository;
import com.example.servicesprovider.service.CreditCardService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl extends BaseServiceImpl<CreditCard, Long, CreditCardRepository> implements CreditCardService {
    public CreditCardServiceImpl(CreditCardRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public CreditCard findByCreditCardNumber(String creditCardNumber) {
        CreditCard creditCard = repository.findByCreditCardNumber(creditCardNumber);
        if (creditCard == null) throw new EntityNotFoundException("Credit card Model not exist");
        return creditCard;
    }

    @Override
    @Transactional
    public void deleteByCreditCardNumber(String creditCardNumber) {
        CreditCard creditCard = repository.findByCreditCardNumber(creditCardNumber);
        if (creditCard != null) {
            repository.deleteByCreditCardNumber(creditCardNumber);
        }
    }
}
