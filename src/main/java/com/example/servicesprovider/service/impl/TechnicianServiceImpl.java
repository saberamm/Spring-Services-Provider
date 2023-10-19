package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.OfferTimeBeforeOrderTimeException;
import com.example.servicesprovider.exception.PasswordsNotEqualException;
import com.example.servicesprovider.exception.TechnicianNotConfirmedYetException;
import com.example.servicesprovider.exception.UsernameOrPasswordNotCorrectException;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.TechnicianRepository;
import com.example.servicesprovider.service.OfferService;
import com.example.servicesprovider.service.TechnicianService;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Service
public class TechnicianServiceImpl extends BaseServiceImpl<Technician, Long, TechnicianRepository> implements TechnicianService {
    OfferService offerService;

    public TechnicianServiceImpl(TechnicianRepository repository, Validator validator, OfferService offerService) {
        super(repository, validator);
        this.offerService = offerService;
    }

    @Override
    public void changePassword(String userName, String password, String newPassword, String duplicateNewPassword) {
        Technician technician = userAuthentication(userName, password);
        try {
            if (!newPassword.equals(duplicateNewPassword))
                throw new PasswordsNotEqualException("new password and duplicate password are not equal");
            technician.setPassword(newPassword);
            update(technician);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Technician findByUserName(String userName) {
        try {
            return repository.findByUserName(userName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Technician userAuthentication(String userName, String password) {
        Technician technician;
        try {
            technician = repository.findByUserNameAndPassword(userName, password);
            if (technician == null)
                throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
            return technician;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public void deleteByUserName(String userName) {
        try {
            repository.deleteByUserName(userName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Offer addOffer(Offer offer) {
        try {
            if (offer.getTimeForStartWorking().isBefore(offer.getOrder().getWorkTime()))
                throw new OfferTimeBeforeOrderTimeException("offer time can not be before order time");
            if (offer.getTechnician().getTechnicianStatus().equals(TechnicianStatus.NEW)
                    || offer.getTechnician().getTechnicianStatus().equals(TechnicianStatus.PENDING_CONFIRMATION))
                throw new TechnicianNotConfirmedYetException("Technician not confirmed yet");
            return offerService.save(offer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //    public List<Order> OrdersThatTechnicianCanOffer(Technician technician){
//
//    }

    @Override
    public List<Technician> notConfirmedYet() {
        List<Technician> technicians = new ArrayList<>();
        try {
            List<Technician> techniciansNew = repository.findAllByTechnicianStatus(TechnicianStatus.NEW);
            List<Technician> techniciansPending = repository.findAllByTechnicianStatus(TechnicianStatus.PENDING_CONFIRMATION);
            technicians.addAll(techniciansNew);
            technicians.addAll(techniciansPending);
            return technicians;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}