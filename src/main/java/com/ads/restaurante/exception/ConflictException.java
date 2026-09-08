package com.ads.restaurante.exception;

/** Violação de regra de unicidade/estado (ex.: username já usado). HTTP 409. */
public class ConflictException extends RuntimeException {

    public ConflictException(String mensagem) {
        super(mensagem);
    }
}
