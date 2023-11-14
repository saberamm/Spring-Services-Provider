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

    @GetMapping("/find/{username}")
    public ResponseEntity<ClientResponseDto> getClient(@PathVariable String username) {
        Client client = clientService.findByUserName(username);
        ClientResponseDto clientResponseDto = modelMapper.map(client, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientResponseDto> addClient(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        Client client = modelMapper.map(clientRequestDto, Client.class);
        Client savedClient = clientService.save(client);
        ClientResponseDto clientResponseDto = modelMapper.map(savedClient, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteClient(@PathVariable String username) {
        clientService.deleteByUserName(username);
    }

    @PutMapping("/update")
    public ResponseEntity<ClientResponseDto> updateClient(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        if (clientRequestDto.getPassword() != null) {
            throw new IllegalCallerException("password cant change here please use change password end point");
        }
        Client client = clientService.findByUserName(clientRequestDto.getUserName());
        modelMapper.map(clientRequestDto, client);
        Client updatedClient = clientService.update(client);
        ClientResponseDto clientResponseDto = modelMapper.map(updatedClient, ClientResponseDto.class);
        return new ResponseEntity<>(clientResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        userService.changePassword(passwordUpdateRequest.getUserName(),
                passwordUpdateRequest.getOldPassword(),
                passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getDuplicateNewPassword());
    }

    @GetMapping("/findGeneralServices")
    public ResponseEntity<List<GeneralServiceResponseDto>> seeGeneralServices() {
        List<GeneralService> generalServiceList = clientService.seeGeneralServices();
        List<GeneralServiceResponseDto> generalServiceResponseDtoList = generalServiceList
                .stream()
                .map(generalService -> modelMapper.map(generalService, GeneralServiceResponseDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(generalServiceResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/findSubServices")
    public ResponseEntity<List<SubServiceResponseDto>> seeSubServices() {
        List<SubService> subServiceList = clientService.seeSubServices();
        List<SubServiceResponseDto> subServiceResponseDtoList = subServiceList
                .stream()
                .map(subService -> subServiceMapper.map(subService))
                .collect(Collectors.toList());

        return new ResponseEntity<>(subServiceResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/addOrder")
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        Order order = orderMapper.map(orderRequestDto);
        Order savedOrder = clientService.addOrder(order);
        OrderResponseDto orderResponseDto = orderMapper.map(savedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

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

    @PutMapping("/chooseOffer/{offerId}")
    public ResponseEntity<OrderResponseDto> chooseOffer(@PathVariable Long offerId) {
        Offer offer = offerService.findById(offerId);
        Order order = clientService.chooseOffer(offer);
        OrderResponseDto orderResponseDto = orderMapper.map(order);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/startOrder/{orderId}")
    public ResponseEntity<OrderResponseDto> startOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        Order startedOrder = clientService.startOrder(order);
        OrderResponseDto orderResponseDto = orderMapper.map(startedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/completeOrder/{orderId}")
    public ResponseEntity<OrderResponseDto> completeOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        Order completedOrder = clientService.completeOrder(order);
        OrderResponseDto orderResponseDto = orderMapper.map(completedOrder);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/payWithClientCredit/{offerId}/{clientId}")
    public String payWithClientCredit(@PathVariable Long offerId, @PathVariable Long clientId) {
        Client client = clientService.findById(clientId);
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

    @GetMapping("findOrdersByStatus")
    public ResponseEntity<List<OrderResponseDto>> findOrdersByStatus(@RequestParam Long clientId, @RequestParam OrderStatus orderStatus) {
        List<Order> orders = orderService.findAllByClientIdAndOrderStatus(clientId, orderStatus);
        List<OrderResponseDto> offerResponseDtoList = orders.stream().map(order -> orderMapper.map(order)).toList();
        return new ResponseEntity<>(offerResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("clientCredit/{userName}")
    public Double clientCredit(@PathVariable String userName) {
        return clientService.clientCredit(userName);
    }
}
