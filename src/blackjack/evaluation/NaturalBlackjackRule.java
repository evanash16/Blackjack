package blackjack.evaluation;

import cardgamelib.storage.Hand;

public class NaturalBlackjackRule extends BlackjackRule {

    public static boolean passes(final Hand hand) {
        return hand.getCards().size() == 2 && BlackjackRule.passes(hand);
    }
}
