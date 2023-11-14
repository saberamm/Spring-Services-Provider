package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.dto.OrderFilterRequestDto;
import com.example.servicesprovider.model.*;
import com.example.servicesprovider.model.enumeration.OrderStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends BaseRepository<Order, Long> {
    List<Order> findAllByClientIdAndOrderStatus(Long clientId, OrderStatus orderStatus);

    @Query(value = "SELECT DISTINCT o FROM Order o " +
            "JOIN o.offerList offer " +
            "JOIN offer.technician t " +
            "WHERE t.id = :technicianId AND o.orderStatus = :orderStatus"
    )
    List<Order> findAllByTechnicianIdAndOrderStatus(Long technicianId, OrderStatus orderStatus);

    List<Order> findAllBySubServiceId(Long subServiceId);

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    default Page<Order> searchAndFilterOrders(OrderFilterRequestDto orderFilterRequestDto, Sort.Direction direction, int pageNumber, int pageSize, String sortBy) {
        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (orderFilterRequestDto.getClientId() != null) {
                Join<Order, Client> clientJoin = root.join("client", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(clientJoin.get("id"), orderFilterRequestDto.getClientId()));

                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.DONE),
                        criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.PAID)
                ));
            }

            if (orderFilterRequestDto.getTechnicianId() != null) {
                Join<Order, Offer> offerJoin = root.join("offerList", JoinType.INNER);
                Join<Offer, Technician> technicianJoin = offerJoin.join("technician", JoinType.INNER);

                predicates.add(criteriaBuilder.equal(technicianJoin.get("id"), orderFilterRequestDto.getTechnicianId()));

                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.DONE),
                        criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.PAID)
                ));
            }

            if (orderFilterRequestDto.getOrderDateStart() != null && orderFilterRequestDto.getOrderDateEnd() != null) {
                predicates.add(criteriaBuilder.between(root.get("workTime"), orderFilterRequestDto.getOrderDateStart(), orderFilterRequestDto.getOrderDateEnd()));
            } else if (orderFilterRequestDto.getOrderDateStart() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("workTime"), orderFilterRequestDto.getOrderDateStart()));
            } else if (orderFilterRequestDto.getOrderDateEnd() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("workTime"), orderFilterRequestDto.getOrderDateEnd()));
            }

            if (orderFilterRequestDto.getOrderStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("orderStatus"), orderFilterRequestDto.getOrderStatus()));
            }

            if (orderFilterRequestDto.getSubServiceName() != null && !orderFilterRequestDto.getSubServiceName().isEmpty()) {
                Join<Order, SubService> subServiceJoin = root.join("subService", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(subServiceJoin.get("subServiceName"), "%" + orderFilterRequestDto.getSubServiceName() + "%"));
            }

            if (orderFilterRequestDto.getGeneralServiceName() != null && !orderFilterRequestDto.getGeneralServiceName().isEmpty()) {
                Join<Order, SubService> subServiceJoin = root.join("subService", JoinType.LEFT);
                Join<SubService, GeneralService> generalServiceJoin = subServiceJoin.join("generalService", JoinType.LEFT);
                predicates.add(criteriaBuilder.like(generalServiceJoin.get("serviceName"), "%" + orderFilterRequestDto.getGeneralServiceName() + "%"));
            }


            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(pageNumber, pageSize, JpaSort.by(direction, sortBy)));
    }
}