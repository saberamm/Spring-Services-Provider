package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.model.CreditCard;

public interface CreditCardRepository extends BaseRepository<CreditCard, Long> {
    CreditCard findByCreditCardNumber(String creditCardNumber);
    void deleteByCreditCardNumber(String creditCardNumber);
}
