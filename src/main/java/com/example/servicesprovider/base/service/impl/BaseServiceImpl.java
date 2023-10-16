package com.example.servicesprovider.base.service.impl;

import com.example.servicesprovider.base.model.BaseModel;
import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.base.service.BaseService;
import com.example.servicesprovider.exception.NotValidModelException;
import jakarta.persistence.EntityNotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class BaseServiceImpl<E extends BaseModel<ID>, ID extends Serializable, R extends BaseRepository<E, ID>>
        implements BaseService<E, ID> {

    protected final R repository;
    protected final Validator validator;

    public BaseServiceImpl(R repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public E save(E e) {
        try {
            if (isValid(e)) {
                repository.save(e);
            }
        } catch (Exception ex) {
            System.out.println("Error while saving model: " + ex.getMessage());
            return null;
        }
        return e;
    }


    @Override
    public E update(E e) {
        try {
            if (isValid(e)) {
                repository.save(e);
            }
        } catch (Exception ex) {
            System.out.println("Error while updating model: " + ex.getMessage());
        }
        return e;
    }

    @Override
    public void delete(E e) {
        try {
            repository.delete(e);
        } catch (Exception ex) {
            System.out.println("Error while deleting model: " + ex.getMessage());
        }
    }

    @Override
    public Optional<E> findById(ID id) {
        Optional<E> e;
        try {
            e = repository.findById(id);
            if (e.isEmpty())
                throw new EntityNotFoundException("Model does not exist");
        } catch (Exception ex) {
            System.out.println("Error while finding model: " + ex.getMessage());
            return Optional.empty();
        }
        return e;
    }

    @Override
    public List<E> findAll() {
        try {
            return repository.findAll();
        } catch (Exception ex) {
            System.out.println("Error while fetching models: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean existsById(ID id) {
        try {
            return repository.existsById(id);
        } catch (Exception ex) {
            System.out.println("Error while checking if model exist: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean isValid(E e) throws NotValidModelException {
        Set<ConstraintViolation<E>> violations = validator.validate(e);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<E> p : violations)
                throw new NotValidModelException(p.getMessage());
            return false;
        }
        return true;
    }
}
