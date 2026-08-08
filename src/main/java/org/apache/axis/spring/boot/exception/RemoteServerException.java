package org.apache.axis.spring.boot.exception;

import java.rmi.RemoteException;

/**
 * Represents a remote server-side exception raised during an Apache Axis web-service
 * invocation.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public class RemoteServerException extends RemoteException {

    private static final long serialVersionUID = 1L;
    private String message;

    /**
     * Returns the exception message.
     * @return the exception message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the exception message.
     * @param message the exception message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Constructs a new {@code RemoteServerException}.
     */
    public RemoteServerException() {
        System.out.println("Remote Servier Exception ");
    }

    /**
     * Prints the exception message to standard output.
     */
    public void showMessage() {
        System.out.println(this.message);
    }
}
