package com.haitiand.modbuslib.Exceptions;

/**
 * Created by lny on 2018/4/23.
 */

public class PlcException extends Exception {
    public PlcException() {
    }

    public PlcException(String message) {
        super(message);
    }

    public PlcException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlcException(Throwable cause) {
        super(cause);
    }
}
