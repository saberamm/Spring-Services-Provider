package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.PasswordsNotEqualException;
import com.example.servicesprovider.exception.UsernameOrPasswordNotCorrectException;
import com.example.servicesprovider.model.User;
import com.example.servicesprovider.repository.UserRepository;
import com.example.servicesprovider.service.UserService;
import com.example.servicesprovider.utility.HashGenerator;

import javax.validation.Validator;

public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    HashGenerator hashGenerator;

    public UserServiceImpl(UserRepository repository, Validator validator, HashGenerator hashGenerator) {
        super(repository, validator);
        this.hashGenerator = hashGenerator;
    }

    @Override
    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public void changePassword(String userName, String password, String newPassword, String duplicateNewPassword) {
        User user = userAuthentication(userName, password);
        if (!newPassword.equals(duplicateNewPassword))
            throw new PasswordsNotEqualException("new password and duplicate password are not equal");
        user.setPassword(newPassword);
        save(user);
    }

    @Override
    public User save(User user) {
        if (isValid(user)) {
            user.setPassword(hashGenerator.generateSHA512Hash(user.getPassword()));
            repository.save(user);
        }
        return user;
    }

    @Override
    public User userAuthentication(String userName, String password) {
        User user;
        String hashedPassword = hashGenerator.generateSHA512Hash(password);
        user = repository.findByUserNameAndPassword(userName, hashedPassword);
        if (user == null)
            throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
        return user;
    }
}