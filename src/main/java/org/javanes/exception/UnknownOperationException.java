package org.javanes.exception;

public final class UnknownOperationException extends Exception {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;
    
    public UnknownOperationException() {
        super("Exception when decoding operation code");
    }
    
    public UnknownOperationException(String message) {
        super(message);
    }

}
