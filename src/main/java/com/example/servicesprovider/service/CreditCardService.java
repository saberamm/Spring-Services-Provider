package com.example.servicesprovider.service;

import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.model.CreditCard;
import org.springframework.stereotype.Service;

@Service
public interface CreditCardService extends BaseService<CreditCard, Long> {
}
