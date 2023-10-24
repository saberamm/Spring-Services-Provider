package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.base.service.impl.BaseServiceImpl;
import com.example.servicesprovider.model.ViewPoint;
import com.example.servicesprovider.repository.ViewPointRepository;
import com.example.servicesprovider.service.ViewPointService;

import javax.validation.Validator;

public class ViewPointServiceImpl extends BaseServiceImpl<ViewPoint, Long, ViewPointRepository> implements ViewPointService {

    public ViewPointServiceImpl(ViewPointRepository repository, Validator validator) {
        super(repository, validator);
    }
}