package com.example.servicesprovider.mapper.impl;

import com.example.servicesprovider.dto.OfferRequestDto;
import com.example.servicesprovider.dto.OfferResponseDto;
import com.example.servicesprovider.dto.OrderResponseDto;
import com.example.servicesprovider.dto.TechnicianResponseDto;
import com.example.servicesprovider.mapper.OfferMapper;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.service.OrderService;
import com.example.servicesprovider.service.TechnicianService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OfferMapperImpl implements OfferMapper {
    TechnicianService technicianService;
    OrderService orderService;
    ModelMapper modelMapper;

    @Override
    public Offer requestToOffer(OfferRequestDto offerRequestDto) {
        if (offerRequestDto == null) return null;
        Offer offer = new Offer();
        offer.setTimeForEndWorking(offerRequestDto.getTimeForEndWorking());
        offer.setTimeForStartWorking(offerRequestDto.getTimeForStartWorking());
        offer.setSuggestionPrice(offerRequestDto.getSuggestionPrice());
        offer.setTechnician(technicianService.findById(offerRequestDto.getTechnicianId()));
        offer.setOrder(orderService.findById(offerRequestDto.getOrderId()));
        return offer;
    }

    @Override
    public OfferResponseDto offerToResponse(Offer offer) {
        if (offer == null) return null;
        OfferResponseDto offerResponseDto = new OfferResponseDto();
        offerResponseDto.setTimeForEndWorking(offer.getTimeForEndWorking());
        offerResponseDto.setTimeForStartWorking(offer.getTimeForStartWorking());
        offerResponseDto.setSuggestionPrice(offer.getSuggestionPrice());
        offerResponseDto.setTechnicianResponseDto(modelMapper.map(offer.getTechnician(), TechnicianResponseDto.class));
        offerResponseDto.setOrderResponseDto(modelMapper.map(offer.getOrder(), OrderResponseDto.class));
        return offerResponseDto;
    }
}
