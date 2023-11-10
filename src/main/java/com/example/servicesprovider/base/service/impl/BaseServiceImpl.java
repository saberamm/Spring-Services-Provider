package com.example.servicesprovider.base.service.impl;

import com.example.servicesprovider.base.model.BaseModel;
import com.example.servicesprovider.base.repository.BaseRepository;
import com.example.servicesprovider.base.service.BaseService;
import jakarta.persistence.EntityNotFoundException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E extends BaseModel<ID>, ID extends Serializable, R extends BaseRepository<E, ID>>
        implements BaseService<E, ID> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E e) {
        return repository.save(e);
    }


    @Override
    public E update(E e) {
        return repository.save(e);
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
    public void deleteById(ID id) {
        Optional<E> e = repository.findById(id);
        if (e.isPresent()) {
            repository.deleteById(id);
        }
        throw new EntityNotFoundException("Model does not exist");
    }
}
