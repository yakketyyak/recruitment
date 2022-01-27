package com.recruitment.casttest.common.advice;


import com.recruitment.casttest.common.advice.error.ApiError;
import com.recruitment.casttest.common.advice.error.ErrorDetail;
import com.recruitment.casttest.common.exception.BusinessException;
import com.recruitment.casttest.common.exception.CommonsException;
import com.recruitment.casttest.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class RestErrorHandler {

    private List<ErrorDetail> getErrorDetails(BindingResult bindingResult) {

        Stream<ErrorDetail> fieldErrors = bindingResult.getFieldErrors().stream().map(error -> ErrorDetail.builder()
                .code(error.getCode())
                .defaultMessage(error.getDefaultMessage())
                .field(error.getField())
                .rejectValue(error.getRejectedValue())
                .build());

        Stream<ErrorDetail> globalErrors = bindingResult.getGlobalErrors().stream().map(error -> ErrorDetail.builder()
                .code(error.getCode())
                .defaultMessage(error.getDefaultMessage())
                .field(error.getObjectName())
                .build());

        return Stream.concat(fieldErrors, globalErrors).collect(Collectors.toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError processError(HttpMessageNotReadableException exception, HttpServletRequest httpServletRequest) {

        Throwable rootCause = exception.getMostSpecificCause();
        String message = new StringJoiner(":")
                .add(rootCause.getClass().getName())
                .add(rootCause.getMessage()).toString();

        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage(message)
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError processError(BindException exception, HttpServletRequest httpServletRequest) {

        List<ErrorDetail> errorDetails = this.getErrorDetails(exception);

        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(errorDetails)
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError processError(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest) {

        List<ErrorDetail> errorDetails = this.getErrorDetails(exception);

        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(errorDetails)
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiError processError(HttpRequestMethodNotSupportedException exception, HttpServletRequest httpServletRequest) {

        return ApiError.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .debugMessage(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError processError(BusinessException exception, HttpServletRequest httpServletRequest) {

        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiError processError(HttpMediaTypeNotSupportedException exception, HttpServletRequest httpServletRequest) {

        String unsupported = "Unsupported content type : " + exception.getContentType();
        String supported = "Supported content type : " + MediaType.toString(exception.getSupportedMediaTypes());

        return ApiError.builder()
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .debugMessage(new StringJoiner(" - ")
                        .add(unsupported).add(supported).toString())
                .path(httpServletRequest.getRequestURI())
                .build();
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError processError(NotFoundException exception, HttpServletRequest httpServletRequest) {

        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .debugMessage(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();
    }


    @ExceptionHandler(CommonsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError processError(CommonsException exception, HttpServletRequest httpServletRequest) {

        return ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .debugMessage(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError processError(Exception exception, HttpServletRequest httpServletRequest) {

        return ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .path(httpServletRequest.getRequestURI())
                .debugMessage(exception.getMessage())
                .build();
    }
}
