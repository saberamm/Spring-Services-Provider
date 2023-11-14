package com.example.servicesprovider.exception;

import com.example.servicesprovider.utility.ErrorDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PasswordsNotEqualException.class)
    public ResponseEntity<Object> PasswordsNotEqualExceptionHandler(PasswordsNotEqualException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLinkException.class)
    public ResponseEntity<Object> InvalidLinkExceptionHandler(InvalidLinkException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredLinkException.class)
    public ResponseEntity<Object> ExpiredLinkExceptionHandler(ExpiredLinkException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameOrPasswordNotCorrectException.class)
    public ResponseEntity<Object> UsernameOrPasswordNotCorrectExceptionHandler(UsernameOrPasswordNotCorrectException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TechnicianNotConfirmedYetException.class)
    public ResponseEntity<Object> TechnicianNotConfirmedYetExceptionHandler(TechnicianNotConfirmedYetException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PriceIsLowerThanBasePriceException.class)
    public ResponseEntity<Object> PriceIsLowerThanBasePriceExceptionHandler(PriceIsLowerThanBasePriceException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotStartedYetException.class)
    public ResponseEntity<Object> OrderNotStartedYetExceptionHandler(OrderNotStartedYetException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderHasNoSelectedOfferException.class)
    public ResponseEntity<Object> OrderHasNoSelectedOfferExceptionHandler(OrderHasNoSelectedOfferException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OfferTimeBeforeOrderTimeException.class)
    public ResponseEntity<Object> OfferTimeBeforeOrderTimeExceptionHandler(OfferTimeBeforeOrderTimeException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughCreditException.class)
    public ResponseEntity<Object> NotEnoughCreditExceptionHandler(NotEnoughCreditException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoOrdersFoundException.class)
    public ResponseEntity<Object> NoOrdersFoundExceptionHandler(NoOrdersFoundException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 404);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IsBeforeStartTimeException.class)
    public ResponseEntity<Object> IsBeforeStartTimeExceptionHandler(IsBeforeStartTimeException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidImageTypeException.class)
    public ResponseEntity<Object> InvalidImageTypeExceptionHandler(InvalidImageTypeException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageSizeNotValidException.class)
    public ResponseEntity<Object> ImageSizeNotValidExceptionHandler(ImageSizeNotValidException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeneralServiceNotExistException.class)
    public ResponseEntity<Object> GeneralServiceNotExistExceptionHandler(GeneralServiceNotExistException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 404);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateSubServiceNameException.class)
    public ResponseEntity<Object> DuplicateSubServiceNameExceptionHandler(DuplicateSubServiceNameException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateGeneralServiceNameException.class)
    public ResponseEntity<Object> DuplicateGeneralServiceNameExceptionHandler(DuplicateGeneralServiceNameException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreditCardNotValidException.class)
    public ResponseEntity<Object> CreditCardNotValidExceptionHandler(CreditCardNotValidException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> EntityNotFoundExceptionHandler(EntityNotFoundException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 404);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> RuntimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> ExceptionHandler(Exception e) {
        log.error(e.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), 400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @Nullable HttpHeaders headers,
                                                                  @Nullable HttpStatusCode status,
                                                                  @Nullable WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                "TotalErrors:" + ex.getErrorCount() + " First Error:" + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),
                400);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
