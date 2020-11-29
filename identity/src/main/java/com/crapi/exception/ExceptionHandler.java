package com.crapi.exception;

import com.crapi.model.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.crapi.model.CRAPIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author Traceabel AI
 */
@ControllerAdvice
@Slf4j
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private static final long serialVersionUID = -3880069851908752573L;
    //private static final Logger handlerLogger = LoggerFactory.getLogger(ExceptionHandler.class);


    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({CRAPIExceptionHandler.class})
    private ResponseEntity<Object> handleCRAPIException(CRAPIExceptionHandler e) {
        CRAPIResponse cr = new CRAPIResponse(e.getMessage(), e.getStatus());
        return new ResponseEntity<Object>(cr, HttpStatus.valueOf(e.getStatus()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails( "Validation Failed",
                ex.getBindingResult().toString());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}