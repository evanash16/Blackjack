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
    protected static Card EIGHT_OF_SPADES = new Card(Value.EIGHT, Suit.SPADES);
    protected static Card THREE_OF_DIAMONDS = new Card(Value.THREE, Suit.DIAMONDS);

    // hands
    protected static Hand BLACKJACK = new Hand(Lists.newArrayList(ACE_OF_CLUBS, KING_OF_HEARTS));
    protected static Hand BUSTED = new Hand(Lists.newArrayList(
            ACE_OF_CLUBS,
            KING_OF_HEARTS,
            EIGHT_OF_SPADES,
            new Card(Value.THREE, Suit.DIAMONDS)));
}
