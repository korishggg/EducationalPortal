package com.educational.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionValues notFoundExp = new ExceptionValues(
                notFoundException.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(notFoundExp, notFound);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    ResponseEntity<Object> handleAlreadyExistException(AlreadyExistsException alreadyExistsException) {
        HttpStatus alreadyExist = HttpStatus.BAD_REQUEST;
        ExceptionValues alreadyExistExp = new ExceptionValues(
                alreadyExistsException.getMessage(),
                alreadyExist,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(alreadyExistExp, alreadyExist);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    ResponseEntity<Object> handleIncorrectPasswordException(IncorrectPasswordException incorrectPasswordException) {
        HttpStatus wrongPass = HttpStatus.UNAUTHORIZED;
        ExceptionValues incorrectPasswordExp = new ExceptionValues(
                incorrectPasswordException.getMessage(),
                wrongPass,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(incorrectPasswordExp, wrongPass);
    }

    @ExceptionHandler(NotEnoughPermissionException.class)
    ResponseEntity<Object> handleNotEnoughPermissionException(NotEnoughPermissionException notEnoughPermissionException) {
        HttpStatus notPermission = HttpStatus.FORBIDDEN;
        ExceptionValues notEnoughPermissionExp = new ExceptionValues(
                notEnoughPermissionException.getMessage(),
                notPermission,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(notEnoughPermissionExp, notPermission);
    }
    @ExceptionHandler(IncorrectParamException.class)
    ResponseEntity<Object> handleIncorrectParamException(IncorrectParamException incorrectParamException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionValues incorrectParamExp = new ExceptionValues(
                incorrectParamException.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(incorrectParamExp, badRequest);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<ObjectError> objectErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        String errors = "";
        for (ObjectError error: objectErrors){
            if(errors.isEmpty()){
                errors = error.getDefaultMessage();
            }else {
                errors = errors + " and " + error.getDefaultMessage();
            }
        }
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionValues incorrectParamExp = new ExceptionValues(
                errors,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(incorrectParamExp, badRequest);
    }
}
