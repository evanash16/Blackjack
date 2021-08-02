package blackjack.evaluation;

import cardgamelib.cards.Card;
import cardgamelib.evaluation.Rule;
import cardgamelib.storage.Hand;

import java.util.List;

public class SplitRule extends Rule {

    public static boolean passes(final Hand hand) {
        List<Card> cards = hand.getCards();
        return cards.size() == 2 &&
                cards.get(0).getNumericalValue() == cards.get(1).getNumericalValue();
    }
}
