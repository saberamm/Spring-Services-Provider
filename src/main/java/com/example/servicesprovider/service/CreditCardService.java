package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.CreditCard;

public interface CreditCardService extends BaseService<CreditCard, Long> {
    CreditCard findByCreditCardNumber(String creditCardNumber);

    void deleteByCreditCardNumber(String creditCardNumber);
}
