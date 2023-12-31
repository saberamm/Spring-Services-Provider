package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.*;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.ClientRepository;
import com.example.servicesprovider.service.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl extends BaseServiceImpl<Client, Long, ClientRepository> implements ClientService {
    OrderService orderService;
    SubService_Service subService_service;
    GeneralService_Service generalService_service;
    OfferService offerService;
    TechnicianService technicianService;
    CreditCardService creditCardService;
    PasswordEncoder passwordEncoder;
    ViewPointService viewPointService;

    public ClientServiceImpl(ClientRepository repository, OrderService orderService, SubService_Service subService_service, GeneralService_Service generalService_service, OfferService offerService, TechnicianService technicianService, CreditCardService creditCardService, PasswordEncoder passwordEncoder, ViewPointService viewPointService) {
        super(repository);
        this.orderService = orderService;
        this.subService_service = subService_service;
        this.generalService_service = generalService_service;
        this.offerService = offerService;
        this.technicianService = technicianService;
        this.creditCardService = creditCardService;
        this.passwordEncoder = passwordEncoder;
        this.viewPointService = viewPointService;
    }

    @Override
    @Transactional
    public Order addOrder(Order order) {
        if (order.getOrderPrice() < order.getSubService().getBasePrice()) {
            throw new PriceIsLowerThanBasePriceException("Order price should not be smaller than sub service base price");
        }
        return orderService.save(order);
    }

    @Override
    @Transactional
    public List<GeneralService> seeGeneralServices() {
        return generalService_service.findAll();
    }

    @Override
    @Transactional
    public void deleteByUserName(String userName) {
        Client client = findByUserName(userName);
        if (client != null) {
            repository.deleteByUserName(userName);
        }
    }

    @Override
    @Transactional
    public List<SubService> seeSubServices() {
        return subService_service.findAll();
    }

    @Override
    @Transactional
    public Client findByUserName(String userName) {
        Client client = repository.findByUserName(userName);
        if (client == null) throw new EntityNotFoundException("Client Model not exist");
        return client;
    }

    @Override
    @Transactional
    public Order chooseOffer(Offer offer) {
        Order order = offer.getOrder();
        order.setSelectedOffersId(offer.getId());
        order.setOrderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE);
        return orderService.update(order);
    }

    @Override
    @Transactional
    public Order startOrder(Order order) {
        Offer offer = offerService.findById(order.getSelectedOffersId());
        if (!order.getOrderStatus().equals(OrderStatus.WAITING_FOR_TECHNICIAN_TO_COME_YOUR_PLACE)) {
            throw new OrderHasNoSelectedOfferException("choose an offer first");
        }
        if (!LocalDateTime.now().isAfter(offer.getTimeForStartWorking())) {
            throw new IsBeforeStartTimeException("Start time is not arrived");
        }
        order.setOrderStatus(OrderStatus.STARTED);
        return orderService.update(order);
    }

    @Override
    @Transactional
    public Order completeOrder(Order order) {
        Offer offer = offerService.findById(order.getSelectedOffersId());
        Technician technician = offer.getTechnician();
        if (order.getOrderStatus().equals(OrderStatus.STARTED)) {
            order.setOrderStatus(OrderStatus.DONE);
        } else {
            throw new OrderNotStartedYetException("Order not started");
        }
        if (isDoneAfterEndTime(order, offer)) {
            addNegativeScore(offer, technician);
        }
        addOverallScore(technician);
        if (technician.getOverallScore() < 0) technician.setTechnicianStatus(TechnicianStatus.INACTIVE);
        technicianService.update(technician);
        return orderService.update(order);
    }

    @Override
    public void addOverallScore(Technician technician) {
        List<ViewPoint> viewPointList = technician.getViewPointList();
        double totalScore = 0.0;
        if (viewPointList != null && !viewPointList.isEmpty()) {
            for (ViewPoint viewPoint : viewPointList) {
                if (viewPoint.getScore() != null) {
                    totalScore += viewPoint.getScore();
                }
            }
            if (technician.getNegativeScore() == null) technician.setNegativeScore(0D);
            technician.setOverallScore(totalScore / viewPointList.size() - technician.getNegativeScore());
        } else {
            if (technician.getNegativeScore() == null) technician.setNegativeScore(0D);
            technician.setOverallScore(totalScore - technician.getNegativeScore());
        }
    }

    @Override
    @Transactional
    public void addNegativeScore(Offer offer, Technician technician) {
        long hours = Duration.between(offer.getTimeForEndWorking(), LocalDateTime.now()).toHours();
        if (technician.getNegativeScore() == null) {
            technician.setNegativeScore(0D);
        }
        technician.setNegativeScore(technician.getNegativeScore() + hours);
    }

    @Override
    public boolean isDoneAfterEndTime(Order order, Offer offer) {
        return LocalDateTime.now().isAfter(offer.getTimeForEndWorking());
    }

    @Override
    @Transactional
    public Client save(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setConfirmationToken(UUID.randomUUID().toString());
        repository.save(client);
        return client;
    }

    @Override
    public Client clientAuthentication(String userName, String password) {
        Client client;
        String hashedPassword = passwordEncoder.encode(password);
        client = repository.findByUserNameAndPassword(userName, hashedPassword);
        if (client == null)
            throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
        return client;
    }

    @Override
    @Transactional
    public void payWithClientCredit(Offer offer, Client client) {
        if (client.getClientCredit() == null) {
            client.setClientCredit(0D);
        }
        if (client.getClientCredit() < offer.getSuggestionPrice())
            throw new NotEnoughCreditException("Your credit is not enough");
        Order order = offer.getOrder();
        Technician technician = offer.getTechnician();
        if (technician.getTechnicianCredit() == null) {
            technician.setTechnicianCredit(0D);
        }
        client.setClientCredit(client.getClientCredit() - offer.getSuggestionPrice());
        technician.setTechnicianCredit(technician.getTechnicianCredit() + offer.getSuggestionPrice() / 100 * 70);
        order.setOrderStatus(OrderStatus.PAID);
        orderService.update(order);
        technicianService.update(technician);
        update(client);
    }

    @Override
    @Transactional
    public void payWithCreditCard(CreditCard creditCard, Offer offer) {
        Technician technician = offer.getTechnician();
        Order order = offer.getOrder();
        if (technician.getTechnicianCredit() == null) {
            technician.setTechnicianCredit(0D);
        }
        CreditCard savedCreditCard = creditCardService.findByCreditCardNumber(creditCard.getCreditCardNumber());
        if (checkCreditCards(savedCreditCard, creditCard)) {
            technician.setTechnicianCredit(technician.getTechnicianCredit() + offer.getSuggestionPrice() / 100 * 70);
            order.setOrderStatus(OrderStatus.PAID);
            orderService.update(order);
            technicianService.update(technician);
        } else throw new CreditCardNotValidException("Credit Card Not valid");
    }

    @Override
    public boolean checkCreditCards(CreditCard savedCreditCard, CreditCard creditCard) {
        return creditCard.getCvv2().equals(savedCreditCard.getCvv2())
                && creditCard.getSecondPassword().equals(savedCreditCard.getSecondPassword());
    }

    @Override
    @Transactional
    public ViewPoint addViewpoint(ViewPoint viewPoint) {
        ViewPoint saveViewPoint = viewPointService.save(viewPoint);
        Technician technician = saveViewPoint.getTechnician();
        addOverallScore(technician);
        technicianService.update(technician);
        return viewPoint;
    }

    @Override
    public Double clientCredit(String userName) {
        return findByUserName(userName).getClientCredit();
    }
}