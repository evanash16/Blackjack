package blackjack.evaluation;

import cardgamelib.evaluation.Score;
import lombok.Getter;

@Getter
public class BlackjackScore implements Score {

    private int value;
    private boolean soft;

    public BlackjackScore(final int value, final boolean soft) {
        this.value = value;
        this.soft = soft;
    }

    @Override
    public int getNumericalValue() {
        return this.value;
    }

    @Override
    public boolean isGreaterThan(Score other) {
        return other instanceof BlackjackScore &&
                getNumericalValue() > other.getNumericalValue();
    }

    @Override
    public boolean isLessThan(Score other) {
        return other instanceof BlackjackScore &&
                getNumericalValue() < other.getNumericalValue();
    }

    @Override
    public boolean isEqualTo(Score other) {
        return other instanceof BlackjackScore &&
                getNumericalValue() == other.getNumericalValue();
    }

    @Override
    public String toString() {
        return String.format(this.soft ? "Soft %d" : "%d", value);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Score &&
                isEqualTo((Score) other);
    }
}
