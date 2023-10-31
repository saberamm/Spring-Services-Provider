package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.GeneralServiceRequestDto;
import com.example.servicesprovider.dto.GeneralServiceResponseDto;
import com.example.servicesprovider.dto.OfferRequestDto;
import com.example.servicesprovider.dto.OfferResponseDto;
import com.example.servicesprovider.mapper.OfferMapper;
import com.example.servicesprovider.model.GeneralService;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.service.OfferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offer")
@AllArgsConstructor
public class OfferController {
    OfferService offerService;
    OfferMapper offerMapper;

    @GetMapping("/find/{offerId}")
    public ResponseEntity<OfferResponseDto> getOffer(@PathVariable Long offerId) {
        Offer offer = offerService.findById(offerId);
        OfferResponseDto offerResponseDto = offerMapper.offerToResponse(offer);
        return new ResponseEntity<>(offerResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<OfferResponseDto> addOffer(@RequestBody @Valid OfferRequestDto offerRequestDto) {
        Offer offer = offerMapper.requestToOffer(offerRequestDto);
        Offer savedOffer = offerService.save(offer);
        OfferResponseDto offerResponseDto = offerMapper.offerToResponse(savedOffer);
        return new ResponseEntity<>(offerResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{offerId}")
    public void deleteOffer(@PathVariable Long offerId) {
        offerService.deleteById(offerId);
    }

    @PutMapping("/update")
    public ResponseEntity<OfferResponseDto> updateOffer(@RequestBody @Valid OfferRequestDto offerRequestDto) {
        Offer offer = offerService.findById(offerRequestDto.getId());
        modelMapper.map(generalServiceRequestDto, generalService);
        GeneralService updatedGeneralService = generalService_service.update(generalService);
        GeneralServiceResponseDto generalServiceResponseDto = modelMapper.map(updatedGeneralService, GeneralServiceResponseDto.class);
        return new ResponseEntity<>(generalServiceResponseDto, HttpStatus.CREATED);
    }
}
