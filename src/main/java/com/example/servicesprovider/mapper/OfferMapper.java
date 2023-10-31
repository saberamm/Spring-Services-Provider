package com.example.servicesprovider.mapper;

import com.example.servicesprovider.dto.OfferRequestDto;
import com.example.servicesprovider.dto.OfferResponseDto;
import com.example.servicesprovider.model.Offer;

public interface OfferMapper {
    Offer requestToOffer(OfferRequestDto offerRequestDto);

    OfferResponseDto offerToResponse(Offer offer);
}
