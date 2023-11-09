package com.example.servicesprovider.repository;

import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.dto.UserRequestDto;
import com.example.servicesprovider.model.User;
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

    default Page<User> searchAndFilterUsers(UserRequestDto userRequestDto, Sort.Direction direction, int pageNumber, int pageSize, String sortBy) {
        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userRequestDto.getRole() != null && !userRequestDto.getRole().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("role"), userRequestDto.getRole()));
            }

            if (userRequestDto.getFirstName() != null && !userRequestDto.getFirstName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + userRequestDto.getFirstName() + "%"));
            }

            if (userRequestDto.getLastName() != null && !userRequestDto.getLastName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + userRequestDto.getLastName() + "%"));
            }

            if (userRequestDto.getEmail() != null && !userRequestDto.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + userRequestDto.getEmail() + "%"));
            }

            if (userRequestDto.getAboutMe() != null && !userRequestDto.getAboutMe().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("aboutMe"), "%" + userRequestDto.getAboutMe() + "%"));
            }

            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(pageNumber, pageSize, JpaSort.by(direction, sortBy)));
    }
}
