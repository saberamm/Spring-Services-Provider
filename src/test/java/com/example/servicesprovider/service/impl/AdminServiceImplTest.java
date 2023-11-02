package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.repository.AdminRepository;
import com.example.servicesprovider.service.GeneralService_Service;
import com.example.servicesprovider.service.SubServiceTechnicianService;
import com.example.servicesprovider.service.SubService_Service;
import com.example.servicesprovider.service.TechnicianService;
import com.example.servicesprovider.utility.HashGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private GeneralService_Service generalServiceService;

    @Mock
    private SubService_Service subServiceService;

    @Mock
    private TechnicianService technicianService;

    @Mock
    private SubServiceTechnicianService subServiceTechnicianService;

    @Mock
    private HashGenerator hashGenerator;

    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        adminService = new AdminServiceImpl(
                Mockito.mock(AdminRepository.class),
                generalServiceService,
                subServiceService,
                technicianService,
                subServiceTechnicianService,
                hashGenerator
        );
    }

    @Test
    public void testUpdateSubServicePrice() {
        SubService subService = new SubService();
        subService.setId(1L);
        subService.setBasePrice(100.0);

        when(subServiceService.findById(1L)).thenReturn(subService);
        when(subServiceService.update(subService)).thenReturn(subService);

        SubService updatedSubService = adminService.updateSubServicePrice(1L, 150.0);

        Mockito.verify(subServiceService).findById(1L);
        Mockito.verify(subServiceService).update(subService);
        assertEquals(150.0, updatedSubService.getBasePrice(), 0.01);
    }

    @Test
    public void testUpdateSubServiceName() {
        SubService subService = new SubService();
        subService.setId(1L);
        subService.setSubServiceName("OldName");

        when(subServiceService.findById(1L)).thenReturn(subService);
        when(subServiceService.update(subService)).thenReturn(subService);

        SubService updatedSubService = adminService.updateSubServiceName(1L, "NewName");

        Mockito.verify(subServiceService).findById(1L);
        Mockito.verify(subServiceService).update(subService);
        assertEquals("NewName", updatedSubService.getSubServiceName());
    }
}
