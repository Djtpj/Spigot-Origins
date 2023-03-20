package io.github.djtpj.trait;

/**
 * Thrown when a {@link Trait} is not fully defined
 */
public class IllDefinedTraitException extends Exception {
    public IllDefinedTraitException(String message) {
        super(message);
    }
}
