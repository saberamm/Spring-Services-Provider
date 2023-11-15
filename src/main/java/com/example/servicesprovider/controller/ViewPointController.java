package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.ViewPointRequestDto;
import com.example.servicesprovider.dto.ViewPointResponseDto;
import com.example.servicesprovider.mapper.ViewPointMapper;
import com.example.servicesprovider.model.ViewPoint;
import com.example.servicesprovider.service.ViewPointService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viewPoint")
@AllArgsConstructor
public class ViewPointController {
    ViewPointMapper viewPointMapper;
    ViewPointService viewPointService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{viewPointId}")
    public ResponseEntity<ViewPointResponseDto> getViewPoint(@PathVariable Long viewPointId) {
        ViewPoint viewPoint = viewPointService.findById(viewPointId);
        ViewPointResponseDto viewPointResponseDto = viewPointMapper.map(viewPoint);
        return new ResponseEntity<>(viewPointResponseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ViewPointResponseDto> addViewPoint(@RequestBody @Valid ViewPointRequestDto viewPointRequestDto) {
        ViewPoint viewPoint = viewPointMapper.map(viewPointRequestDto);
        ViewPoint savedViewPoint = viewPointService.save(viewPoint);
        ViewPointResponseDto viewPointResponseDto = viewPointMapper.map(savedViewPoint);
        return new ResponseEntity<>(viewPointResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{viewPointId}")
    public void deleteViewPoint(@PathVariable Long viewPointId) {
        viewPointService.deleteById(viewPointId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<ViewPointResponseDto> updateViewPoint(@RequestBody @Valid ViewPointRequestDto viewPointRequestDto) {
        ViewPoint viewPoint = viewPointService.findById(viewPointRequestDto.getId());
        viewPointMapper.map(viewPointRequestDto, viewPoint);
        ViewPoint updatedViewPoint = viewPointService.update(viewPoint);
        ViewPointResponseDto viewPointResponseDto = viewPointMapper.map(updatedViewPoint);
        return new ResponseEntity<>(viewPointResponseDto, HttpStatus.CREATED);
    }
}
