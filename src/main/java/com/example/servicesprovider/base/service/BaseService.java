package com.example.servicesprovider.base.service;

import com.example.servicesprovider.base.model.BaseModel;
import com.example.servicesprovider.exception.NotValidModelException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<E extends BaseModel<ID>, ID extends Serializable> {
    E save(E e);

    E update(E e);

    void delete(E e);

    E findById(ID id);

    List<E> findAll();

    boolean existsById(ID id);

    boolean isValid(E e) throws NotValidModelException;
}
