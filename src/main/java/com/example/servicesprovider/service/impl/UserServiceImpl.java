package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.exception.PasswordsNotEqualException;
import com.example.servicesprovider.exception.UsernameOrPasswordNotCorrectException;
import com.example.servicesprovider.model.User;
import com.example.servicesprovider.repository.UserRepository;
import com.example.servicesprovider.service.UserService;
import com.example.servicesprovider.utility.HashGenerator;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    HashGenerator hashGenerator;

    public UserServiceImpl(UserRepository repository, Validator validator, HashGenerator hashGenerator) {
        super(repository, validator);
        this.hashGenerator = hashGenerator;
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
        String hashedNewPassword = hashGenerator.generateSHA512Hash(newPassword);
        String hashedDuplicateNewPassword = hashGenerator.generateSHA512Hash(newPassword);
        try {
            if (!hashGenerator.areHashesEqual(hashedNewPassword, hashedDuplicateNewPassword))
                throw new PasswordsNotEqualException("new password and duplicate password are not equal");
            user.setPassword(hashedNewPassword);
            update(user);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public User save(User user) {
        try {
            if (isValid(user)) {
                user.setPassword(hashGenerator.generateSHA512Hash(user.getPassword()));
                repository.save(user);
            }
        } catch (Exception ex) {
            System.out.println("Error while saving model: " + ex.getMessage());
            return null;
        }
        return user;
    }

    @Override
    public User userAuthentication(String userName, String password) {
        User user;
        String hashedPassword = hashGenerator.generateSHA512Hash(password);
        try {
            user = repository.findByUserNameAndPassword(userName, hashedPassword);
            if (user == null)
                throw new UsernameOrPasswordNotCorrectException("Username or password not correct");
            return user;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}