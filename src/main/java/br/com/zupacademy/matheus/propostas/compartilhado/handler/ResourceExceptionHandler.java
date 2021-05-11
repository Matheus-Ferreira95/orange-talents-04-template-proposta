package br.com.zupacademy.matheus.propostas.compartilhado.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<MessageError> errosDeValidacao(MethodArgumentNotValidException ex) {
        List<MessageError> erros = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            erros.add(new MessageError(fieldError.getField(), messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())));
        });
        return erros;
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<String> handlerApiErrorException(ApiErrorException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body("kkk");
    }
}
