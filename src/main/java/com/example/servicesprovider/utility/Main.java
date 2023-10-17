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

        Client client = Client.builder().firstName("reza").lastName("asghar").userName("ttwr").birthDate(LocalDate.of(2005, 5, 5))
                .password("1wW#qwer").email("qwe@asd.com").clientCredit(50000D).nationalCode("5555555555").phoneNumber("09999999999").build();


        byte[] image = imageConverter.readFileToBytes("C:\\Users\\Administrator\\Desktop\\Temp\\sss.jpg");

        Technician technician = Technician.builder().technicianCredit(100000D).birthDate(LocalDate.of(2000, 8, 8)).firstName("mmd")
                .lastName("trdf").email("asd@asd.com").userName("qweqwe").technicianStatus(TechnicianStatus.NEW).aboutMe("asdda").build();

//        ApplicationContext.getUserService().save(user);
//        ApplicationContext.getAdminService().save(admin);
//        ApplicationContext.getClientService().save(client);
//        ApplicationContext.getTechnicianService().save(technician);

//        User user1 = ApplicationContext.getUserService().findById(1L);
//        Admin admin1 = ApplicationContext.getAdminService().findById(2L);
//        Client client1 = ApplicationContext.getClientService().findById(3L);
//        Technician technician1 = ApplicationContext.getTechnicianService().findById(4L);

//        user1.setFirstName("bb");
//        ApplicationContext.getUserService().update(user1);
//        admin1.setLastName("admin22");
//        ApplicationContext.getAdminService().update(admin1);
//        client1.setFirstName("reza");
//        ApplicationContext.getClientService().update(client1);
//        technician1.setAboutMe("im bad");
//        technician1.setPassword("Rr#33333");
//        ApplicationContext.getTechnicianService().update(technician1);

//        ApplicationContext.getUserService().delete(user1);
//        ApplicationContext.getAdminService().delete(admin1);
//        ApplicationContext.getClientService().delete(client1);
//        ApplicationContext.getTechnicianService().delete(technician1);

//        ImageConverter.saveBytesToFile(technician1.getTechnicianPhoto(),
//                "C:\\Users\\Administrator\\Desktop\\Temp", "technician.jpg");

        //----------------  general service -------------------//

        GeneralService generalService = GeneralService.builder().serviceName("sakhteman").build();

//        ApplicationContext.getGeneralService_Service().save(generalService);

//        GeneralService generalService1 = ApplicationContext.getGeneralService_Service().findById(1L);

//        generalService1.setServiceName("bargh");
//        ApplicationContext.getGeneralService_Service().update(generalService1);
//        ApplicationContext.getGeneralService_Service().delete(generalService1);

        //----------------  sub general service -------------------//

        SubService subService =SubService.builder().subServiceName("barghe sakhteman").basePrice(10000D).description("baray bargh sakhteman")
                .generalService(generalService_service.findById(1L).get()).build();

//        ApplicationContext.getSubService_Service().save(subService);
//
//        SubService subService1 = ApplicationContext.getSubService_Service().findById(1L);
//
//        subService1.setDescription("bargh sakhteaman ha");
//        subService1.setBasePrice(8000D);
//        subService1.setDescription("adfgfhnnnbv");
//        ApplicationContext.getSubService_Service().update(subService1);
//
//        ApplicationContext.getSubService_Service().delete(subService1);

        //----------------  order service -------------------//


//        Order order = new Order(50000D, "qweqe",
//                LocalDateTime.of(2030, 5, 5, 15, 5, 5),
//                "khonamon", OrderStatus.WAITING_FOR_TECHNICIAN_OFFER,
//                ApplicationContext.getClientService().findById(3L),
//                ApplicationContext.getSubService_Service().findById(1L));
//
//        ApplicationContext.getOrderService().save(order);

//        Order order1 = ApplicationContext.getOrderService().findById(1L);

//        order1.setOrderAddress("home");
//        ApplicationContext.getOrderService().update(order1);


//        ApplicationContext.getOrderService().delete(order1);


        //----------------  offer service -------------------//
//
//        Offer offer = new Offer(LocalDateTime.of(2000, 5, 5, 15, 5, 5)
//                , LocalDateTime.of(2000, 6, 5, 15, 5, 5),
//                80000D, "12 days",
//                ApplicationContext.getTechnicianService().findById(4L),
//                ApplicationContext.getOrderService().findById(1L));
//
//        ApplicationContext.getOfferService().save(offer);

//        Offer offer1 = ApplicationContext.getOfferService().findById(1L);

//        offer1.setWorkDuration("14 days");
//        ApplicationContext.getOfferService().update(offer1);

//        ApplicationContext.getOfferService().delete(offer1);

        //----------------  viewpoint service -------------------//

//        ViewPoint viewPoint = new ViewPoint("awwwwli",4,
//                ApplicationContext.getTechnicianService().findById(4L));
//
//        ApplicationContext.getViewPointService().save(viewPoint);

//        ViewPoint viewPoint1 = ApplicationContext.getViewPointService().findById(1L);

//        viewPoint1.setScore(6);
//        ApplicationContext.getViewPointService().update(viewPoint1);

//        ApplicationContext.getViewPointService().delete(viewPoint1);

        //----------------  change password -------------------//

//        ApplicationContext.getClientService().changePassword("asddsa", "1Q@eeeee", "5@rRaaaa");

        //---------------- add general service and sub service by admin -------------------//

//        GeneralService generalService2 = new GeneralService("khodro");
//
//        ApplicationContext.getAdminService().addGeneralServiceByAdmin(generalService2);
//
//        SubService subService2 = new SubService("barghe khodro", 200000D,
//                "baray tamir khodro", ApplicationContext.getGeneralService_Service().findById(2L));
//
//        ApplicationContext.getAdminService().addSubServiceByAdmin(subService2);

//        SubService subService2 = new SubService("barghe khodro", 200000D,
//                "baray tamir khodro", ApplicationContext.getGeneralService_Service().findById(500L));
//
//        ApplicationContext.getAdminService().addSubServiceByAdmin(subService2);

        //---------------- delete and add technician by admin -------------------//

//        Technician technician2 = new Technician("ooo", "ooo", "ooo", "wqw#2Aoooo",
//                LocalDate.of(2009, 4, 4), "asd@aoo.com", TechnicianStatus.NEW,
//                image, 0D, "09103333333", "5555666666", "im good");

//        ApplicationContext.getAdminService().addTechnicianByAdmin(technician2);

//        ApplicationContext.getAdminService().deleteTechnicianByAdmin("ooo");

        //---------------- see all general services by admin -------------------//

//        System.out.println(ApplicationContext.getAdminService().generalServicesListByAdmin());

        //---------------- see all technicians by admin -------------------//

//        System.out.println(ApplicationContext.getAdminService().seeTechnicianNotAcceptedByAdmin());
//        Technician technician3 = ApplicationContext.getTechnicianService().findById(4L);
//        technician3.setTechnicianStatus(TechnicianStatus.CONFIRMED);
//        ApplicationContext.getTechnicianService().update(technician3);


//        Technician technician3 = ApplicationContext.getTechnicianService().findById(4L);
//        SubService subService3=ApplicationContext.getSubService_Service().findById(1L);
//        ApplicationContext.getAdminService().addTechnicianToSubService(technician3,subService3);
//        ApplicationContext.getSubService_Service().delete(subService3);


        //---------------- see all technicians by admin -------------------//

//        System.out.println(ApplicationContext.getClientService().seeGeneralServicesByClient());
//        System.out.println(ApplicationContext.getClientService().seeSubServicesByClient());
//        Order order4 = new Order(3000000D, "salam",
//                LocalDateTime.of(2002, 8, 8, 18, 18), "fff",
//                OrderStatus.WAITING_FOR_TECHNICIAN_OFFER, ApplicationContext.getSubService_Service().findById(1L));
//        Client client4 = ApplicationContext.getClientService().findById(3L);
//        ApplicationContext.getClientService().addOrderByClient(order4, client4);

    }
}