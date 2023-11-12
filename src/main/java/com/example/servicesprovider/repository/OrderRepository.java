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

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends BaseRepository<Order, Long> {

    List<Order> findAllBySubServiceId(Long subServiceId);

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    default Page<Order> searchAndFilterUsers(OrderFilterRequestDto orderFilterRequestDto, Sort.Direction direction, int pageNumber, int pageSize, String sortBy) {
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


            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(pageNumber, pageSize, JpaSort.by(direction, sortBy)));
    }
}