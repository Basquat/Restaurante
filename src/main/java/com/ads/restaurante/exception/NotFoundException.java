package com.ads.restaurante.exception;

/** Recurso não encontrado. Tratada como HTTP 404 pelo ApiExceptionHandler. */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String recurso, Object id) {
        super(recurso + " " + id + " não encontrado");
    }

    public NotFoundException(String mensagem) {
        super(mensagem);
    }
}
