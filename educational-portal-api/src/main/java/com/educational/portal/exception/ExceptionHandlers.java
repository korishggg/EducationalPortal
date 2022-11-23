package com.educational.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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

}
