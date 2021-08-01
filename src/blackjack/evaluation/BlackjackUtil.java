package blackjack.evaluation;

import cardgamelib.cards.Card;
import cardgamelib.cards.Value;
import cardgamelib.storage.Hand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BlackjackUtil {

    public static BlackjackScore scoreHand(Hand hand) {
        boolean soft = false;
        int value = 0;

        // score aces last
        List<Card> cards = hand.getCards().stream()
                .sorted(Comparator.comparingInt(Card::getNumericalValue).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            boolean isLast = i == cards.size() - 1;
            if (card.getValue() == Value.ACE) {
                if (value + 11 <= 21 && isLast) {
                    value += 11;
                    continue;
                } else {
                    soft = true;
                }
            }
            value += card.getNumericalValue();
        }

        return new BlackjackScore(value, soft);
    }
}
