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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viewPoint")
@AllArgsConstructor
public class ViewPointController {
    ViewPointMapper viewPointMapper;
    ViewPointService viewPointService;

    @GetMapping("/find/{viewPointId}")
    public ResponseEntity<ViewPointResponseDto> getViewPoint(@PathVariable Long viewPointId) {
        ViewPoint viewPoint = viewPointService.findById(viewPointId);
        ViewPointResponseDto viewPointResponseDto = viewPointMapper.map(viewPoint);
        return new ResponseEntity<>(viewPointResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ViewPointResponseDto> addViewPoint(@RequestBody @Valid ViewPointRequestDto viewPointRequestDto) {
        ViewPoint viewPoint = viewPointMapper.map(viewPointRequestDto);
        ViewPoint savedViewPoint = viewPointService.save(viewPoint);
        ViewPointResponseDto viewPointResponseDto = viewPointMapper.map(savedViewPoint);
        return new ResponseEntity<>(viewPointResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{viewPointId}")
    public void deleteViewPoint(@PathVariable Long viewPointId) {
        viewPointService.deleteById(viewPointId);
    }

    @PutMapping("/update")
    public ResponseEntity<ViewPointResponseDto> updateViewPoint(@RequestBody @Valid ViewPointRequestDto viewPointRequestDto) {
        ViewPoint viewPoint = viewPointService.findById(viewPointRequestDto.getId());
        viewPointMapper.map(viewPointRequestDto, viewPoint);
        ViewPoint updatedViewPoint = viewPointService.update(viewPoint);
        ViewPointResponseDto viewPointResponseDto = viewPointMapper.map(updatedViewPoint);
        return new ResponseEntity<>(viewPointResponseDto, HttpStatus.CREATED);
    }
}
