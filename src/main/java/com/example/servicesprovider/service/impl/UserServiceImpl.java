package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.dto.UserFilterRequestDto;
import com.example.servicesprovider.exception.ExpiredLinkException;
import com.example.servicesprovider.exception.InvalidLinkException;
import com.example.servicesprovider.exception.PasswordsNotEqualException;
import com.example.servicesprovider.exception.UsernameOrPasswordNotCorrectException;
import com.example.servicesprovider.model.Technician;
import com.example.servicesprovider.model.User;
import com.example.servicesprovider.model.enumeration.TechnicianStatus;
import com.example.servicesprovider.repository.UserRepository;
import com.example.servicesprovider.service.TechnicianService;
import com.example.servicesprovider.service.UserService;
import com.example.servicesprovider.utility.HashGenerator;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    HashGenerator hashGenerator;
    JavaMailSender javaMailSender;
    TechnicianService technicianService;

    public UserServiceImpl(UserRepository repository, HashGenerator hashGenerator, JavaMailSender javaMailSender, TechnicianService technicianService) {
        super(repository);
        this.hashGenerator = hashGenerator;
        this.javaMailSender = javaMailSender;
        this.technicianService = technicianService;
    }

    @Override
    @Transactional
    public User findByUserName(String userName) {
        User user = repository.findByUserName(userName);
        if (user == null) throw new EntityNotFoundException("Model not exist");
        return user;
    }

    @Override
    @Transactional
    public void changePassword(String userName, String password, String newPassword, String duplicateNewPassword) {
        User user = userAuthentication(userName, password);
        if (!newPassword.equals(duplicateNewPassword))
            throw new PasswordsNotEqualException("new password and duplicate password are not equal");
        user.setPassword(newPassword);
        save(user);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(hashGenerator.generateSHA512Hash(user.getPassword()));
        user.setConfirmationToken(UUID.randomUUID().toString());
        repository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User userAuthentication(String userName, String password) {
        User user;
        String hashedPassword = hashGenerator.generateSHA512Hash(password);
        user = repository.findByUserNameAndPassword(userName, hashedPassword);
        if (user == null)
            throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
        return user;
    }

    @Override
    @Transactional
    public Page<User> searchAndFilterUsers(UserFilterRequestDto userFilterRequestDto, Sort.Direction direction, int pageNumber, int pageSize, String sortBy) {
        return repository.searchAndFilterUsers(userFilterRequestDto, direction, pageNumber, pageSize, sortBy);
    }

    @Override
    public void sendConfirmationEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Confirm Your Registration");
        mailMessage.setText("Click the link to confirm your registration: "
                + "http://localhost:8080/user/confirmation?token=" + user.getConfirmationToken());
        javaMailSender.send(mailMessage);
    }

    @Override
    public void confirmUser(String token) {
        User user = repository.findByConfirmationToken(token);

        if (user == null) {
            throw new InvalidLinkException("Link is invalid");
        }

        if (user.isConfirmed()) {
            throw new ExpiredLinkException("Link has expired");
        }

        user.setConfirmed(true);

        repository.save(user);

        if (user.getClass().getSimpleName().equals("Technician")) {
            Technician technician = technicianService.findByUserName(user.getUserName());
            technician.setTechnicianStatus(TechnicianStatus.PENDING_CONFIRMATION);
            technicianService.update(technician);
        }
    }
}