package com.example.servicesprovider.controller;

import com.example.servicesprovider.dto.OrderRequestDto;
import com.example.servicesprovider.dto.OrderResponseDto;
import com.example.servicesprovider.mapper.OrderMapper;
import com.example.servicesprovider.model.Order;
import com.example.servicesprovider.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    OrderMapper orderMapper;
    OrderService orderService;

    @GetMapping("/find/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        OrderResponseDto orderResponseDto = orderMapper.map(order);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        Order order = orderMapper.map(orderRequestDto);
        Order savedOrder = orderService.save(order);
        OrderResponseDto orderResponseDto = orderMapper.map(savedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteById(orderId);
    }

    @PutMapping("/update")
    public ResponseEntity<OrderResponseDto> updateOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        Order order = orderService.findById(orderRequestDto.getId());
        orderMapper.map(orderRequestDto, order);
        Order updatedOrder = orderService.update(order);
        OrderResponseDto orderResponseDto = orderMapper.map(updatedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }
}
