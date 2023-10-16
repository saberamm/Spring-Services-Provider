package com.example.servicesprovider.utility;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Component
public class EntityValidator {
    public final ValidatorFactory validatorFactory = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory();

    public final Validator validator = validatorFactory.usingContext()
            .messageInterpolator(new ParameterMessageInterpolator()).getValidator();

}