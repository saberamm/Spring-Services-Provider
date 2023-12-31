package com.example.servicesprovider.repository;

import com.example.servicesprovider.model.SubService;
import com.example.servicesprovider.model.SubServiceTechnician;
import com.example.servicesprovider.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubServiceTechnicianRepository extends JpaRepository<SubServiceTechnician, Long> {

    void deleteByTechnicianAndSubService(Technician technician, SubService subService);
    boolean existsByTechnicianAndSubService(Technician technician, SubService subService);
    SubServiceTechnician findByTechnician_IdAndSubService_Id(Long technician_id, Long subService_id);
}
