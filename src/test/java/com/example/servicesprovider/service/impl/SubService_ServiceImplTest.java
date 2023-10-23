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

        SubService subService = new SubService();
        subService.setSubServiceName("Test SubService");


        Mockito.when(subServiceRepository.findBySubServiceName("Test SubService"))
                .thenReturn(subService);


        SubService result = subServiceService.findSubServiceByName("Test SubService");


        assertEquals("Test SubService", result.getSubServiceName());
    }

    @Test
    public void testFindSubServiceByNameWhenExceptionThrown() {

        Mockito.when(subServiceRepository.findBySubServiceName("Nonexistent SubService"))
                .thenThrow(new RuntimeException("Data access error"));


        SubService result = subServiceService.findSubServiceByName("Nonexistent SubService");


        assertNull(result);
    }

    @Test
    public void testFindSubServicesByTechnicianId() {

        SubService subService1 = new SubService();
        subService1.setSubServiceName("Service 1");

        SubService subService2 = new SubService();
        subService2.setSubServiceName("Service 2");

        List<SubService> subServiceList = Arrays.asList(subService1, subService2);


        Mockito.when(subServiceRepository.findByTechnicianListId(1L))
                .thenReturn(subServiceList);


        List<SubService> result = subServiceService.findSubServicesByTechnicianId(1L);


        assertEquals(2, result.size());
    }

    @Test
    public void testFindSubServicesByTechnicianIdWhenExceptionThrown() {

        Mockito.when(subServiceRepository.findByTechnicianListId(2L))
                .thenThrow(new RuntimeException("Data access error"));


        List<SubService> result = subServiceService.findSubServicesByTechnicianId(2L);


        assertNull(result);
    }
}
