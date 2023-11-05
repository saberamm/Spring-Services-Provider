package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.PasswordsNotEqualException;
import com.example.servicesprovider.exception.UsernameOrPasswordNotCorrectException;
import com.example.servicesprovider.model.User;
import com.example.servicesprovider.repository.UserRepository;
import com.example.servicesprovider.service.UserService;
import com.example.servicesprovider.utility.HashGenerator;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    HashGenerator hashGenerator;

    public UserServiceImpl(UserRepository repository, HashGenerator hashGenerator) {
        super(repository);
        this.hashGenerator = hashGenerator;
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
    public Page<User> searchAndFilterUsers(String role, String firstName, String lastName, String email,String aboutMe, String sortBy, Pageable pageable) {
        return repository.searchAndFilterUsers(role, firstName, lastName, email, aboutMe, sortBy, pageable);
    }
}