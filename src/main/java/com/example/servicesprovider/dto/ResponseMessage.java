package com.example.servicesprovider.dto;

import java.time.LocalDateTime;

public record ResponseMessage(LocalDateTime time, String message) {
}
