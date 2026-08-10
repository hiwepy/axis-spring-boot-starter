package org.apache.axis.spring.boot.exception;

import java.rmi.RemoteException;

/**
 * Represents a local client-side exception raised during an Apache Axis web-service
 * invocation.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class LocalClientException extends RemoteException {

    private static final long serialVersionUID = 3;
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
     * Constructs a new {@code LocalClientException}.
     */
    public LocalClientException() {
        System.out.println("Local Client Exception ");
    }

    /**
     * Prints the exception message to standard output.
     */
    public void showMessage() {
        System.out.println(this.message);
    }
}