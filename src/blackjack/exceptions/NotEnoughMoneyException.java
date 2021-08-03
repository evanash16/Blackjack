package blackjack.exceptions;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(final String message) {
        super(message);
    }
}
