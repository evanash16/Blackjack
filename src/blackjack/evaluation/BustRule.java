package blackjack.evaluation;

import cardgamelib.evaluation.Rule;
import cardgamelib.storage.Hand;

public class BustRule extends Rule {

    public static boolean passes(final Hand hand) {
        return BlackjackUtil.scoreHand(hand).getNumericalValue() > 21;
    }
}
