package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.model.Admin;
import com.example.servicesprovider.model.GeneralService;
import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.AdminRepository;
import com.example.servicesprovider.service.AdminService;
import com.example.servicesprovider.service.GeneralService_Service;
import com.example.servicesprovider.service.SubService_Service;
import com.example.servicesprovider.service.TechnicianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private GeneralService_Service generalServiceService;

    @Mock
    private SubService_Service subServiceService;

    @Mock
    private TechnicianService technicianService;

    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        adminService = new AdminServiceImpl(adminRepository, null, generalServiceService, subServiceService, technicianService);
    }

    @Test
    public void testAddGeneralService() {
        GeneralService generalService = new GeneralService();
        generalService.setServiceName("ServiceName");

        when(generalServiceService.save(generalService)).thenReturn(generalService);

        GeneralService savedService = adminService.addGeneralService(generalService);

        assertEquals(generalService, savedService);
    }

    @Test
    public void testAddSubService() {
        SubService subService = new SubService();
        subService.setSubServiceName("SubServiceName");

        GeneralService generalService = new GeneralService();
        subService.setGeneralService(generalService);

        when(subServiceService.findSubServiceByName("SubServiceName")).thenReturn(null);
        when(generalServiceService.existsById(generalService.getId())).thenReturn(true);
        when(subServiceService.save(subService)).thenReturn(subService);

        SubService savedSubService = adminService.addSubService(subService);

        assertEquals(subService, savedSubService);
    }



    @Test
    public void testAddTechnician() {
        Technician technician = new Technician();
        technician.setUserName("TechnicianUserName");

        when(technicianService.save(technician)).thenReturn(technician);

        Technician savedTechnician = adminService.addTechnician(technician);

        assertEquals(technician, savedTechnician);
    }


    @Test
    public void testGeneralServicesList() {
        List<GeneralService> generalServices = new ArrayList<>();

        when(generalServiceService.findAll()).thenReturn(generalServices);

        List<GeneralService> retrievedServices = adminService.generalServicesList();

        assertEquals(generalServices, retrievedServices);
    }

    @Test
    public void testDeleteTechnician() {
        String username = "technicianUsername";

        adminService.deleteTechnician(username);

        verify(technicianService, times(1)).deleteByUserName(username);
    }

    @Test
    public void testSeeTechnicianNotAccepted() {
        List<Technician> notAcceptedTechnicians = new ArrayList<>();

        when(technicianService.notConfirmedYet()).thenReturn(notAcceptedTechnicians);

        List<Technician> retrievedTechnicians = adminService.seeTechnicianNotAccepted();

        assertEquals(notAcceptedTechnicians, retrievedTechnicians);
    }

    @Test
    public void testAddTechnicianToSubService() {
        Technician technician = new Technician();
        technician.setId(1L);
        technician.setTechnicianStatus(TechnicianStatus.CONFIRMED);

        SubService subService = new SubService();
        List<SubService> subServices = new ArrayList<>();

        when(subServiceService.findSubServicesByTechnicianId(technician.getId())).thenReturn(subServices);

        adminService.addTechnicianToSubService(technician, subService);

        verify(subServiceService, times(1)).findSubServicesByTechnicianId(technician.getId());
        verify(technicianService, times(1)).update(technician);
    }



    @Test
    public void testFindByUserName() {
        String username = "adminUsername";
        Admin admin = new Admin();

        when(adminRepository.findByUserName(username)).thenReturn(admin);

        Admin retrievedAdmin = adminService.findByUserName(username);

        assertEquals(admin, retrievedAdmin);
    }


    @Test
    public void testConfirmTechnician() {
        Technician technician = new Technician();
        technician.setTechnicianStatus(TechnicianStatus.PENDING_CONFIRMATION);

        when(technicianService.update(technician)).thenReturn(technician);

        Technician confirmedTechnician = adminService.confirmTechnician(technician);

        assertEquals(TechnicianStatus.CONFIRMED, confirmedTechnician.getTechnicianStatus());
    }


    @Test
    public void testAddSubService_DuplicateSubServiceNameException() {
        SubService subService = new SubService();
        subService.setSubServiceName("SubServiceName");

        when(subServiceService.findSubServiceByName("SubServiceName")).thenReturn(subService);

        SubService savedSubService = adminService.addSubService(subService);

        assertNull(savedSubService);
    }

    @Test
    public void testAddSubService_GeneralServiceNotExistException() {
        SubService subService = new SubService();
        subService.setSubServiceName("SubServiceName");

        when(subServiceService.findSubServiceByName("SubServiceName")).thenReturn(null);

        subService.setGeneralService(null);

        SubService savedSubService = adminService.addSubService(subService);

        assertNull(savedSubService);
    }

    @Test
    public void testAddTechnicianToSubService_TechnicianNotConfirmedYetException() {
        SubService subService = new SubService();
        subService.setId(1L);

        Technician technician = new Technician();
        technician.setId(1L);
        technician.setTechnicianStatus(TechnicianStatus.NEW);

        when(technicianService.findById(technician.getId())).thenReturn(null);

        adminService.addTechnicianToSubService(technician, subService);

    }

    @Test
    public void testFindByUserName_AdminNotFoundException() {
        String username = "adminUsername";

        when(adminRepository.findByUserName(username)).thenThrow(RuntimeException.class);

        Admin admin = adminService.findByUserName(username);

        assertNull(admin);
    }
}

