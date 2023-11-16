package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.CreditCardRequestDto;
import com.example.servicesprovider.dto.CreditCardResponseDto;
import com.example.servicesprovider.model.CreditCard;
import com.example.servicesprovider.service.CreditCardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/creditCard")
@AllArgsConstructor
public class CreditCardController {
    CreditCardService creditCardService;
    ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('TECHNICIAN','CLIENT','ADMIN')")
    @GetMapping("/find/{creditCardNumber}")
    public ResponseEntity<CreditCardResponseDto> getCreditCard(@PathVariable String creditCardNumber) {
        CreditCard creditCard = creditCardService.findByCreditCardNumber(creditCardNumber);
        CreditCardResponseDto creditCardResponseDto = modelMapper.map(creditCard, CreditCardResponseDto.class);
        return new ResponseEntity<>(creditCardResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TECHNICIAN','CLIENT','ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<CreditCardResponseDto> addCreditCard(@RequestBody @Valid CreditCardRequestDto creditCardRequestDto) {
        CreditCard creditCard = modelMapper.map(creditCardRequestDto, CreditCard.class);
        CreditCard savedCreditCard = creditCardService.save(creditCard);
        CreditCardResponseDto creditCardResponseDto = modelMapper.map(savedCreditCard, CreditCardResponseDto.class);
        return new ResponseEntity<>(creditCardResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('TECHNICIAN','CLIENT','ADMIN')")
    @DeleteMapping("/delete/{creditCardNumber}")
    public String deleteCreditCard(@PathVariable String creditCardNumber) {
        creditCardService.deleteByCreditCardNumber(creditCardNumber);
        return "credit card deleted successfully";
    }

    @PreAuthorize("hasAnyRole('TECHNICIAN','CLIENT','ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<CreditCardResponseDto> updateCreditCard(@RequestBody @Valid CreditCardRequestDto creditCardRequestDto) {
        CreditCard creditCard = creditCardService.findByCreditCardNumber(creditCardRequestDto.getCreditCardNumber());
        modelMapper.map(creditCardRequestDto, creditCard);
        CreditCard updatedCreditCard = creditCardService.update(creditCard);
        CreditCardResponseDto creditCardResponseDto = modelMapper.map(updatedCreditCard, CreditCardResponseDto.class);
        return new ResponseEntity<>(creditCardResponseDto, HttpStatus.CREATED);
    }
}
