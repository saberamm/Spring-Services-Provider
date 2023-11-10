package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.dto.UserFilterRequestDto;
import com.example.servicesprovider.model.*;
import jakarta.persistence.criteria.Expression;
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

public interface UserRepository extends BaseRepository<User, Long> {

    User findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    default Page<User> searchAndFilterUsers(UserFilterRequestDto userFilterRequestDto, Sort.Direction direction, int pageNumber, int pageSize, String sortBy) {
        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userFilterRequestDto.getRole() != null && !userFilterRequestDto.getRole().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("role"), userFilterRequestDto.getRole()));
            }

            if (userFilterRequestDto.getFirstName() != null && !userFilterRequestDto.getFirstName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + userFilterRequestDto.getFirstName() + "%"));
            }

            if (userFilterRequestDto.getLastName() != null && !userFilterRequestDto.getLastName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + userFilterRequestDto.getLastName() + "%"));
            }

            if (userFilterRequestDto.getEmail() != null && !userFilterRequestDto.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + userFilterRequestDto.getEmail() + "%"));
            }

            if (userFilterRequestDto.getAboutMe() != null && !userFilterRequestDto.getAboutMe().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("aboutMe"), "%" + userFilterRequestDto.getAboutMe() + "%"));
            }

            if (userFilterRequestDto.getBirthDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("birthDate"), userFilterRequestDto.getBirthDate()));
            }

            if (userFilterRequestDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), userFilterRequestDto.getId()));
            }

            if (userFilterRequestDto.getUserName() != null && !userFilterRequestDto.getUserName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("userName"), "%" + userFilterRequestDto.getUserName() + "%"));
            }

            if (userFilterRequestDto.getPosition() != null) {
                predicates.add(criteriaBuilder.equal(root.get("position"), userFilterRequestDto.getPosition()));
            }

            if (userFilterRequestDto.getClientCredit() != null) {
                predicates.add(criteriaBuilder.equal(root.get("clientCredit"), userFilterRequestDto.getClientCredit()));
            }

            if (userFilterRequestDto.getPhoneNumber() != null && !userFilterRequestDto.getPhoneNumber().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + userFilterRequestDto.getPhoneNumber() + "%"));
            }

            if (userFilterRequestDto.getNationalCode() != null && !userFilterRequestDto.getNationalCode().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("nationalCode"), "%" + userFilterRequestDto.getNationalCode() + "%"));
            }

            if (userFilterRequestDto.getTechnicianStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("technicianStatus"), userFilterRequestDto.getTechnicianStatus()));
            }

            if (userFilterRequestDto.getTechnicianCredit() != null) {
                predicates.add(criteriaBuilder.equal(root.get("technicianCredit"), userFilterRequestDto.getTechnicianCredit()));
            }

            if (userFilterRequestDto.getNegativeScore() != null) {
                predicates.add(criteriaBuilder.equal(root.get("negativeScore"), userFilterRequestDto.getNegativeScore()));
            }

            if (userFilterRequestDto.getOverallScore() != null) {
                predicates.add(criteriaBuilder.equal(root.get("overallScore"), userFilterRequestDto.getOverallScore()));
            }

            if (userFilterRequestDto.getSubServiceName() != null && !userFilterRequestDto.getSubServiceName().isEmpty()) {
                Join<User, SubServiceTechnician> subServiceTechnicianJoin = root.join("subServiceTechnicianList", JoinType.LEFT);
                Join<SubServiceTechnician, SubService> subServiceJoin = subServiceTechnicianJoin.join("subService", JoinType.LEFT);

                predicates.add(criteriaBuilder.like(subServiceJoin.get("subServiceName"), "%" + userFilterRequestDto.getSubServiceName() + "%"));
            }

            if (userFilterRequestDto.getSignUpDateStart() != null && userFilterRequestDto.getSignUpDateEnd() != null) {
                predicates.add(criteriaBuilder.between(root.get("signUpDate"), userFilterRequestDto.getSignUpDateStart(), userFilterRequestDto.getSignUpDateEnd()));
            } else if (userFilterRequestDto.getSignUpDateStart() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("signUpDate"), userFilterRequestDto.getSignUpDateStart()));
            } else if (userFilterRequestDto.getSignUpDateEnd() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("signUpDate"), userFilterRequestDto.getSignUpDateEnd()));
            }

            if (userFilterRequestDto.getNumberOfClientsOrder() != null) {
                Join<User, Order> clientOrderJoin = root.join("orderList", JoinType.LEFT);
                Expression<Long> userId = root.get("id").as(Long.class);
                Expression<Long> clientOrderCount = criteriaBuilder.count(clientOrderJoin);
                query.having(criteriaBuilder.equal(clientOrderCount, userFilterRequestDto.getNumberOfClientsOrder()));
                query.groupBy(userId);
            }

            if (userFilterRequestDto.getOrderStatus() != null) {
                Join<User, Order> orderJoin = root.join("orderList", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(orderJoin.get("orderStatus"), userFilterRequestDto.getOrderStatus()));
            }

            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(pageNumber, pageSize, JpaSort.by(direction, sortBy)));
    }
}
