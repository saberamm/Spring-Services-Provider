package com.example.servicesprovider.utility;

import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.AdminPosition;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Main implements CommandLineRunner {

    UserService userService;
    TechnicianService technicianService;
    ClientService clientService;
    AdminService adminService;
    OfferService offerService;
    OrderService orderService;
    SubService_Service subService_service;
    GeneralService_Service generalService_service;
    ViewPointService viewPointService;
    ImageConverter imageConverter;

    public Main(UserService userService, TechnicianService technicianService, ClientService clientService, AdminService adminService, OfferService offerService, OrderService orderService, SubService_Service subService_service, GeneralService_Service generalService_service, ViewPointService viewPointService, ImageConverter imageConverter) {
        this.userService = userService;
        this.technicianService = technicianService;
        this.clientService = clientService;
        this.adminService = adminService;
        this.offerService = offerService;
        this.orderService = orderService;
        this.subService_service = subService_service;
        this.generalService_service = generalService_service;
        this.viewPointService = viewPointService;
        this.imageConverter = imageConverter;
    }

    @Override
    public void run(String... args) {


        //----------------  users -------------------//

        User user = new User("aa", "aa",
                "aa", "yyOs1#222",
                LocalDate.of(2000, 2, 2), "sss@sss.com");

        Admin admin = Admin.builder().firstName("reza").lastName("asghar").userName("ttwr").birthDate(LocalDate.of(2005, 5, 5))
                .password("1wW#qwer").position(AdminPosition.EMPLOYEE).email("qwe@asd.com").build();

        Client client = Client.builder().firstName("reza").lastName("asghar").userName("tt4wr").birthDate(LocalDate.of(2005, 5, 5))
                .password("1wW#qwer").email("qwe@a4sd.com").clientCredit(50000D).nationalCode("5555555555").phoneNumber("09999999999").build();


        byte[] image = imageConverter.readFileToBytes("C:\\Users\\Administrator\\Desktop\\Temp\\sss.jpg");

        Technician technician = Technician.builder().technicianCredit(100000D).birthDate(LocalDate.of(2000, 8, 8)).firstName("mmd")
                .lastName("trdf").technicianPhoto(image).phoneNumber("99999999999").email("asd@asd.com").nationalCode("5036699885").userName("qweqwe").technicianStatus(TechnicianStatus.NEW).aboutMe("asdda").password("1!dDdddd").build();

//        userService.save(user);
//        adminService.save(admin);
//        clientService.save(client);
//        technicianService.save(technician);
//
//        User user1 = userService.findById(1L);
//        Admin admin1 = adminService.findById(2L);
//        Client client1 = clientService.findById(3L);
//        Technician technician1 = technicianService.findById(4L);

//        user1.setFirstName("bb");
//        userService.update(user1);
//        admin1.setLastName("admin22");
//        adminService.update(admin1);
//        client1.setFirstName("reza");
//        clientService.update(client1);
//        technician1.setAboutMe("im bad");
//        technician1.setPassword("Rr#33333");
//        technicianService.update(technician1);
//
//        userService.delete(user1);
//        adminService.delete(admin1);
//        clientService.delete(client1);
//        technicianService.delete(technician1);
//
//        imageConverter.saveBytesToFile(technician1.getTechnicianPhoto(),
//                "C:\\Users\\Administrator\\Desktop\\Temp", "technician.jpg");

        //----------------  general service -------------------//

        GeneralService generalService = GeneralService.builder().serviceName("sakhteman").build();
//
//        generalService_service.save(generalService);
//
//        GeneralService generalService1 = generalService_service.findById(1L);
//
//        generalService1.setServiceName("bargh");
//        generalService_service.update(generalService1);
//        generalService_service.delete(generalService1);

        //----------------  sub general service -------------------//

        SubService subService = SubService.builder().subServiceName("barghe sakhteman").basePrice(10000D).description("baray bargh sakhteman")
                .generalService(generalService_service.findById(1L)).build();

        subService_service.save(subService);
//
//        SubService subService1 = subService_service.findById(1L);
//
//        subService1.setDescription("bargh sakhteaman ha");
//        subService1.setBasePrice(8000D);
//        subService1.setDescription("adfgfhnnnbv");
//        subService_service.update(subService1);
//
//        subService_service.delete(subService1);

        //----------------  order service -------------------//

//        Order order = Order.builder().orderPrice(2000D).orderAddress("khonamon").
//                orderDescription("qweqe").orderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_OFFER).
//                client(clientService.findById(3L)).subService(subService_service.findById(1L)).build();
//
//        orderService.save(order);
//
//        Order order1 = orderService.findById(1L);
//
//        order1.setOrderAddress("home");
//        orderService.update(order1);
//
//
//        orderService.delete(order1);


        //----------------  offer service -------------------//

//        Offer offer = Offer.builder().timeOfferSent(LocalDateTime.of(2000, 5, 5, 15, 50))
//                .suggestionPrice(200000D).timeForStartWorking(LocalDateTime.of(2000, 6, 5, 15, 50))
//                .timeForEndWorking(LocalDateTime.of(2000, 7, 5, 15, 50)).order(orderService.findById(1L)).build();
//
//        offerService.save(offer);
//
//        Offer offer1 = offerService.findById(1L);
//
//        offer1.setTimeOfferSent(LocalDateTime.of(2000, 6, 5, 15, 50));
//        offerService.update(offer1);
//
//        offerService.delete(offer1);

        //----------------  viewpoint service -------------------//

//        ViewPoint viewPoint = ViewPoint.builder().score(4).technician(technicianService.findById(4L)).comment("awwwlliii").build();
//
//        viewPointService.save(viewPoint);
//
//        ViewPoint viewPoint1 = viewPointService.findById(1L);
//
//        viewPoint1.setScore(6);
//        viewPointService.update(viewPoint1);
//
//        viewPointService.delete(viewPoint1);

        //----------------  change password -------------------//

//        clientService.changePassword("asddsa", "1Q@eeeee", "5@rRaaaa", "5@rRaaaa");

        //---------------- add general service and sub service by admin -------------------//

//        GeneralService generalService2 = GeneralService.builder().serviceName("bargh").build();
//
//        adminService.addGeneralService(generalService2);
//
//        SubService subService2 = SubService.builder().subServiceName("barghe khodro").basePrice(200000D).description("baray tamir khodro").generalService(generalService_service.findById(2L)).build();
//
//        adminService.addSubService(subService2);

        //---------------- delete and add technician by admin -------------------//

//        Technician technician2 = Technician.builder().technicianCredit(10000D).birthDate(LocalDate.of(2006, 8, 8)).firstName("mmd")
//                .lastName("trdf").email("asd@asggd.com").userName("qweqhhfwe").technicianStatus(TechnicianStatus.NEW).aboutMe("asdkkda").build();
//
//
//        adminService.addTechnician(technician2);
//
//        adminService.deleteTechnician("ooo");

        //---------------- see all general services by admin -------------------//

//        System.out.println(adminService.generalServicesList());

        //---------------- see all technicians by admin -------------------//

//        System.out.println(adminService.seeTechnicianNotAccepted());
//        Technician technician3 = technicianService.findById(4L);
//        technician3.setTechnicianStatus(TechnicianStatus.CONFIRMED);
//        technicianService.update(technician3);
//
//
//        Technician technician3 = technicianService.findById(4L);
//        SubService subService3 = subService_service.findById(1L);
//        adminService.addTechnicianToSubService(technician3, subService3);
//        subService_service.delete(subService3);


        //---------------- see all technicians by admin -------------------//

//        System.out.println(clientService.seeGeneralServicesByClient());
//        System.out.println(clientService.seeSubServicesByClient());
//        Order order4 = Order.builder().orderPrice(30000D).orderAddress("fff").orderDescription("sasas").
//                orderStatus(OrderStatus.WAITING_FOR_TECHNICIAN_OFFER).workTime(LocalDateTime.of(2002, 8, 8, 18, 18))
//                .subService(subService_service.findById(1L)).build();
//        Client client4 = clientService.findById(3L);
//        clientService.addOrderByClient(order4, client4);

    }
}