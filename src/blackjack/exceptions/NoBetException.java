package blackjack.exceptions;

public class NoBetException extends RuntimeException {

    public NoBetException(final String message) {
        super(message);
    }
}
