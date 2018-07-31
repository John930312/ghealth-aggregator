package com.todaysoft.ghealth.portal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todaysoft.ghealth.base.response.ErrorResponse;
import com.todaysoft.ghealth.service.impl.ErrorCode;
import com.todaysoft.ghealth.service.impl.ServiceException;

@ControllerAdvice
public class ExceptionResolver
{
    private static Logger log = LoggerFactory.getLogger(ExceptionResolver.class);
    
    @ExceptionHandler(value = SignatureException.class)
    public ResponseEntity<?> signatureExceptionHandler()
    {
        log.error("Signature exception.");
        return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<?> serviceExceptionHandler(ServiceException e)
    {
        log.error(e.getErrorCode());
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(e.getErrorCode());
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> defaultExceptionHandler(Exception e)
    {
        log.error(e.getMessage(), e);
        
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(ErrorCode.UNDEFINED_ERROR);
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
