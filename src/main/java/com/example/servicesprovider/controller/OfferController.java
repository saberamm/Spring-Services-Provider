package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.OfferRequestDto;
import com.example.servicesprovider.dto.OfferResponseDto;
import com.example.servicesprovider.dto.ResponseMessage;
import com.example.servicesprovider.mapper.OfferMapper;
import com.example.servicesprovider.model.Offer;
import com.example.servicesprovider.service.OfferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/offer")
@AllArgsConstructor
public class OfferController {
    OfferService offerService;
    OfferMapper offerMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{offerId}")
    public ResponseEntity<OfferResponseDto> getOffer(@PathVariable Long offerId) {
        Offer offer = offerService.findById(offerId);
        OfferResponseDto offerResponseDto = offerMapper.map(offer);
        return new ResponseEntity<>(offerResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<OfferResponseDto> addOffer(@RequestBody @Valid OfferRequestDto offerRequestDto) {
        Offer offer = offerMapper.map(offerRequestDto);
        Offer savedOffer = offerService.save(offer);
        OfferResponseDto offerResponseDto = offerMapper.map(savedOffer);
        return new ResponseEntity<>(offerResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{offerId}")
    public ResponseEntity<ResponseMessage> deleteOffer(@PathVariable Long offerId) {
        offerService.deleteById(offerId);
        ResponseMessage responseMessage = new ResponseMessage(LocalDateTime.now(), "offer deleted successfully");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<OfferResponseDto> updateOffer(@RequestBody @Valid OfferRequestDto offerRequestDto) {
        Offer offer = offerService.findById(offerRequestDto.getId());
        offerMapper.map(offerRequestDto, offer);
        Offer updatedOffer = offerService.update(offer);
        OfferResponseDto offerResponseDto = offerMapper.map(updatedOffer);
        return new ResponseEntity<>(offerResponseDto, HttpStatus.CREATED);
    }
}
