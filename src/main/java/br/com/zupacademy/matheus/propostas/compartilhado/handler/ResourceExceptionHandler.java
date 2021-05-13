package br.com.zupacademy.matheus.propostas.compartilhado.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Collection<String> errosDeValidacao(MethodArgumentNotValidException ex) {
        Collection<String> mensagens = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagemRecuperada = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            String mensagem = String.format("Campo %s %s", e.getField(), mensagemRecuperada);
            mensagens.add(mensagem);
        });

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return mensagens;
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<String> handlerApiErrorException(ApiErrorException ex) {
        Collection<String> messages = new ArrayList<>();
        messages.add(ex.getReason());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
}
