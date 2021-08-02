package blackjack.evaluation;

import cardgamelib.cards.Card;
import cardgamelib.evaluation.Rule;
import cardgamelib.storage.Hand;

import java.util.List;

public class DoubleDownRule extends Rule {

    public static boolean passes(final Hand hand) {
        List<Card> cards = hand.getCards();
        BlackjackScore score = BlackjackUtil.scoreHand(hand);
        int handValue = score.getNumericalValue() - (score.isSoft() ? 10 : 0);
        return cards.size() == 2 && handValue >= 9 && handValue <= 11;
    }
}
