package com.nucleus.app;

/**
 * Created by Sreeraag on 08-04-2016.
 */
public class ReflectionException extends RuntimeException {
    /**
     * Uid.
     */
    private static final long serialVersionUID = -5110160439913415481L;

    /**
     * Constructor.
     *
     * @param e
     *            exception thrown by <code>java.lang.reflection</code>.
     */
    public ReflectionException(final Exception e) {
        super(e);
    }

}