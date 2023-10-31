package com.example.servicesprovider.mapper;

import com.example.servicesprovider.dto.OfferRequestDto;
import com.example.servicesprovider.dto.OfferResponseDto;
import com.example.servicesprovider.model.Offer;

public interface OfferMapper {
    Offer map(OfferRequestDto offerRequestDto);

    OfferResponseDto map(Offer offer);

    void map(OfferRequestDto offerRequestDto, Offer offer);
}
