package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.PasswordsNotEqualException;
import com.example.servicesprovider.exception.UsernameOrPasswordNotCorrectException;
import com.example.servicesprovider.model.User;
import com.example.servicesprovider.repository.UserRepository;
import com.example.servicesprovider.service.UserService;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {


    public UserServiceImpl(UserRepository repository, Validator validator) {
        super(repository, validator);
    }

    @Override
    public User findByUserName(String userName) {
        try {
            return repository.findByUserName(userName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public void changePassword(String userName, String password, String newPassword, String duplicateNewPassword) {
        User user = userAuthentication(userName, password);
        try {
            if (!newPassword.equals(duplicateNewPassword))
                throw new PasswordsNotEqualException("new password and duplicate password are not equal");
            user.setPassword(newPassword);
            update(user);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public User userAuthentication(String userName, String password) {
        User user;
        try {
            user = repository.findByUserNameAndPassword(userName, password);
            if (user==null)
                throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
            return user;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}