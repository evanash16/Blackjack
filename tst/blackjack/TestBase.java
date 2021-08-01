package blackjack;

import cardgamelib.cards.Card;
import cardgamelib.cards.Suit;
import cardgamelib.cards.Value;
import cardgamelib.storage.Hand;
import org.testng.collections.Lists;

public class TestBase {

    // cards
    protected static Card ACE_OF_CLUBS = new Card(Value.ACE, Suit.CLUBS);
    protected static Card KING_OF_HEARTS = new Card(Value.KING, Suit.HEARTS);

    // hands
    protected static Hand BLACKJACK = new Hand(Lists.newArrayList(ACE_OF_CLUBS, KING_OF_HEARTS));
}
