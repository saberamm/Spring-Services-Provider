package com.example.servicesprovider.controller;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.gimpy.DropShadowGimpyRenderer;
import com.example.servicesprovider.dto.*;
import com.example.servicesprovider.mapper.OfferMapper;
import com.example.servicesprovider.mapper.OrderMapper;
import com.example.servicesprovider.mapper.SubServiceMapper;
import com.example.servicesprovider.mapper.ViewPointMapper;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.service.ClientService;
import com.example.servicesprovider.service.OfferService;
import com.example.servicesprovider.service.OrderService;
import com.example.servicesprovider.service.UserService;
import com.example.servicesprovider.utility.ImageConverter;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    ClientService clientService;
    UserService userService;
    ModelMapper modelMapper;
    SubServiceMapper subServiceMapper;
    OrderMapper orderMapper;
    ImageConverter imageConverter;
    OfferService offerService;
    OrderService orderService;
    ViewPointMapper viewPointMapper;
    OfferMapper offerMapper;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/find")
    public ResponseEntity<ClientResponseDto> getClient() {
        Client client = clientService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        ClientResponseDto clientResponseDto = modelMapper.map(client, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientResponseDto> addClient(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        Client client = modelMapper.map(clientRequestDto, Client.class);
        Client savedClient = clientService.save(client);
        userService.sendConfirmationEmail(savedClient);
        ClientResponseDto clientResponseDto = modelMapper.map(savedClient, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/delete")
    public String deleteClient() {
        clientService.deleteByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        return "client deleted successfully";
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/update")
    public ResponseEntity<ClientResponseDto> updateClient(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        if (clientRequestDto.getPassword() != null) {
            throw new IllegalCallerException("password cant change here please use change password end point");
        }
        Client client = clientService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        modelMapper.map(clientRequestDto, client);
        Client updatedClient = clientService.update(client);
        ClientResponseDto clientResponseDto = modelMapper.map(updatedClient, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/changePassword")
    public String changePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(SecurityContextHolder.getContext().getAuthentication().getName(),
                passwordUpdateRequest.getOldPassword(),
                passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getDuplicateNewPassword());
        return "password changed successfully";
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/findGeneralServices")
    public ResponseEntity<List<GeneralServiceResponseDto>> seeGeneralServices() {
        List<GeneralService> generalServiceList = clientService.seeGeneralServices();
        List<GeneralServiceResponseDto> generalServiceResponseDtoList = generalServiceList
                .stream()
                .map(generalService -> modelMapper.map(generalService, GeneralServiceResponseDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(generalServiceResponseDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/findSubServices")
    public ResponseEntity<List<SubServiceResponseDto>> seeSubServices() {
        List<SubService> subServiceList = clientService.seeSubServices();
        List<SubServiceResponseDto> subServiceResponseDtoList = subServiceList
                .stream()
                .map(subService -> subServiceMapper.map(subService))
                .collect(Collectors.toList());

        return new ResponseEntity<>(subServiceResponseDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/addOrder")
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        Client client = clientService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        orderRequestDto.setClientId(client.getId());
        Order order = orderMapper.map(orderRequestDto);
        Order savedOrder = clientService.addOrder(order);
        OrderResponseDto orderResponseDto = orderMapper.map(savedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/getOffersSortedByTechnicianScore/{orderId}")
    public ResponseEntity<List<OfferResponseDto>> getOffersSortedByTechnicianScore(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        List<Offer> offerList = offerService.getOffersSortedByTechnicianScore(order);
        List<OfferResponseDto> offerResponseDtoList = offerList
                .stream()
                .map(offer -> offerMapper.map(offer))
                .collect(Collectors.toList());

        return new ResponseEntity<>(offerResponseDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/getOffersSortedByPrice/{orderId}")
    public ResponseEntity<List<OfferResponseDto>> getOffersSortedByPrice(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        List<Offer> offerList = offerService.getOffersSortedByPrice(order);
        List<OfferResponseDto> offerResponseDtoList = offerList
                .stream()
                .map(offer -> offerMapper.map(offer))
                .collect(Collectors.toList());

        return new ResponseEntity<>(offerResponseDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/chooseOffer/{offerId}")
    public ResponseEntity<OrderResponseDto> chooseOffer(@PathVariable Long offerId) {
        Offer offer = offerService.findById(offerId);
        Order order = clientService.chooseOffer(offer);
        OrderResponseDto orderResponseDto = orderMapper.map(order);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/startOrder/{orderId}")
    public ResponseEntity<OrderResponseDto> startOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        Order startedOrder = clientService.startOrder(order);
        OrderResponseDto orderResponseDto = orderMapper.map(startedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/completeOrder/{orderId}")
    public ResponseEntity<OrderResponseDto> completeOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        Order completedOrder = clientService.completeOrder(order);
        OrderResponseDto orderResponseDto = orderMapper.map(completedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/payWithClientCredit/{offerId}")
    public String payWithClientCredit(@PathVariable Long offerId) {
        Client client = clientService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Offer offer = offerService.findById(offerId);
        clientService.payWithClientCredit(offer, client);
        return "Payment was successful";
    }

    @PostMapping("/payWithCreditCard")
    public String payWithCreditCard(@RequestParam Long offerId, @RequestParam String creditCardNumber,
                                    @RequestParam String cvv2, @RequestParam String secondPassword,
                                    @RequestParam LocalDate expireDate, @RequestParam String captcha, HttpSession session) {

        String storedCaptcha = (String) session.getAttribute("captcha");

        if (!captcha.equals(storedCaptcha)) {
            return "CAPTCHA verification failed. Please try again.";
        }
        Offer offer = offerService.findById(offerId);
        CreditCard creditCard = CreditCard.builder().creditCardNumber(creditCardNumber).cvv2(cvv2)
                .secondPassword(secondPassword).expireDate(expireDate).build();
        clientService.payWithCreditCard(creditCard, offer);
        return "Payment was successful";
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/addViewpoint")
    public ResponseEntity<ViewPointResponseDto> addViewpoint(@RequestBody @Valid ViewPointRequestDto viewPointRequestDto) {
        ViewPoint viewPoint = viewPointMapper.map(viewPointRequestDto);
        ViewPoint savedViewPoint = clientService.addViewpoint(viewPoint);
        ViewPointResponseDto viewPointResponseDto = viewPointMapper.map(savedViewPoint);
        return new ResponseEntity<>(viewPointResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/captcha-image")
    public ResponseEntity<byte[]> generateCaptchaImage(HttpSession session) {
        Captcha captcha = new Captcha.Builder(200, 50)
                .addText()
                .addNoise()
                .addBackground()
                .addBorder()
                .gimp(new DropShadowGimpyRenderer())
                .build();

        session.setAttribute("captcha", captcha.getAnswer());

        BufferedImage image = captcha.getImage();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        byte[] imageBytes = imageConverter.convertImageToByteArray(image);

        return new ResponseEntity<>(imageBytes, headers, 200);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/findOrdersByStatus")
    public ResponseEntity<List<OrderResponseDto>> findOrdersByStatus(@RequestParam OrderStatus orderStatus) {
        Client client = clientService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Order> orders = orderService.findAllByClientIdAndOrderStatus(client.getId(), orderStatus);
        List<OrderResponseDto> offerResponseDtoList = orders.stream().map(order -> orderMapper.map(order)).toList();
        return new ResponseEntity<>(offerResponseDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/clientCredit")
    public Double clientCredit() {
        return clientService.clientCredit(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/filterOrders")
    public ResponseEntity<List<OfferResponseDto>> filterOffersByCriteria(
            @RequestParam Long orderId,
            @RequestParam String storeBy) {
        List<Offer> offerList = offerService.filterOffersByCriteria(orderId, storeBy);
        List<OfferResponseDto> offerResponseDtoList = offerList.stream().map(offer -> offerMapper.map(offer)).toList();
        return new ResponseEntity<>(offerResponseDtoList, HttpStatus.OK);
    }
}
