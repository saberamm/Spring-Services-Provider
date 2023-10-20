package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.SubServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SubService_ServiceImplTest {

    @Mock
    private SubServiceRepository subServiceRepository;

    @InjectMocks
    private SubService_ServiceImpl subServiceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindSubServiceByName() {
        // Create a sample SubService object
        SubService subService = new SubService();
        subService.setSubServiceName("Test SubService");

        // Mock the behavior of the repository
        Mockito.when(subServiceRepository.findBySubServiceName("Test SubService"))
                .thenReturn(subService);

        // Test the service method
        SubService result = subServiceService.findSubServiceByName("Test SubService");

        // Verify the result
        assertEquals("Test SubService", result.getSubServiceName());
    }

    @Test
    public void testFindSubServiceByNameWhenExceptionThrown() {
        // Mock the repository to throw an exception
        Mockito.when(subServiceRepository.findBySubServiceName("Nonexistent SubService"))
                .thenThrow(new RuntimeException("Data access error"));

        // Test the service method
        SubService result = subServiceService.findSubServiceByName("Nonexistent SubService");

        // Verify that the result is null
        assertNull(result);
    }

    @Test
    public void testFindSubServicesByTechnicianId() {
        // Create a sample list of SubService objects
        SubService subService1 = new SubService();
        subService1.setSubServiceName("Service 1");

        SubService subService2 = new SubService();
        subService2.setSubServiceName("Service 2");

        List<SubService> subServiceList = Arrays.asList(subService1, subService2);

        // Mock the behavior of the repository
        Mockito.when(subServiceRepository.findByTechnicianListId(1L))
                .thenReturn(subServiceList);

        // Test the service method
        List<SubService> result = subServiceService.findSubServicesByTechnicianId(1L);

        // Verify the result
        assertEquals(2, result.size());
    }

    @Test
    public void testFindSubServicesByTechnicianIdWhenExceptionThrown() {
        // Mock the repository to throw an exception
        Mockito.when(subServiceRepository.findByTechnicianListId(2L))
                .thenThrow(new RuntimeException("Data access error"));

        // Test the service method
        List<SubService> result = subServiceService.findSubServicesByTechnicianId(2L);

        // Verify that the result is null
        assertNull(result);
    }
}
