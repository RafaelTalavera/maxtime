package com.app.maxtime.exeption;

public class RegistroNoEncontradoException extends RuntimeException {
    public RegistroNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

