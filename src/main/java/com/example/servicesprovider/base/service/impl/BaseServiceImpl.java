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
        if (isValid(e)) {
            repository.save(e);
        }
        return e;
    }


    @Override
    public E update(E e) {
        if (isValid(e)) {
            repository.save(e);
        }
        return e;
    }

    @Override
    public void delete(E e) {
        repository.delete(e);
    }

    @Override
    public E findById(ID id) {
        Optional<E> e;
        e = repository.findById(id);
        if (e.isEmpty())
            throw new EntityNotFoundException("Model does not exist");
        return e.get();
    }

    @Override
    public List<E> findAll() {
            return repository.findAll();
    }

    @Override
    public boolean existsById(ID id) {
            return repository.existsById(id);
    }

    @Override
    public boolean isValid(E e) {
        Set<ConstraintViolation<E>> violations = validator.validate(e);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<E> p : violations)
                throw new NotValidModelException(p.getMessage());
        }
        return true;
    }
}
