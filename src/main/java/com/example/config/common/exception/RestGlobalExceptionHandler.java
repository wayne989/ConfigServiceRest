package com.example.config.common.exception;

import com.example.config.logging.ConfigServiceLogger;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

/**
 * RestGlobalExceptionHandler
 * this class intended for restful service exception handling, which returns proper http status
 * and respond back the error message in json format
 */
@ControllerAdvice
@RestController
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    Logger errorLogger = ConfigServiceLogger.getErrorLogger();

    /**
     * this is to handle the particular InvalidJSONFormatException when it is thrown from
     * controller
     * @param ex
     * @param req
     * @return
     */
    @ExceptionHandler(InvalidJSONFormatException.class)
    public final ResponseEntity<HashMap> handleInvalidJSONFormatException(InvalidJSONFormatException ex, WebRequest req){
        HashMap<String, String> errorMap = new HashMap<String,String>();
        errorMap.put("Invalid JSON String", ex.getInvalidJsonStr());
        errorLogger.error("error=" + "Invalid JSON String: " + ex.getInvalidJsonStr());
        return new ResponseEntity<HashMap>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
