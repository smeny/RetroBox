package org.javanes.exception;

public class NotInitialisedException extends Exception {
    
    /**
     * Default UID serial version.
     */
    private static final long serialVersionUID = 1L;

    public NotInitialisedException() {
        super("ALU have not been initialised");
    }
}
