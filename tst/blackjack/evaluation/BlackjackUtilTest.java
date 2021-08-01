package blackjack.evaluation;

import blackjack.TestBase;
import cardgamelib.cards.Card;
import cardgamelib.cards.Suit;
import cardgamelib.cards.Value;
import cardgamelib.storage.Hand;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class BlackjackUtilTest extends TestBase {

    @Test
    public void testScoreHand() {
        Hand testHand = new Hand(Lists.newArrayList(
                new Card(Value.TWO, Suit.SPADES),
                new Card(Value.TEN, Suit.DIAMONDS)));

        BlackjackScore expected = new BlackjackScore(12, false);
        assertEquals(BlackjackUtil.scoreHand(testHand), expected);
    }

    @Test
    public void testScoreHandWithBlackjack() {
        BlackjackScore expected = new BlackjackScore(21, true);
        assertEquals(BlackjackUtil.scoreHand(BLACKJACK), expected);
    }

    @Test
    public void testScoreHandWithElevenAces() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            cards.add(ACE_OF_CLUBS);
        }

        Hand testHand = new Hand(cards);
        BlackjackScore expected = new BlackjackScore(21, true);
        assertEquals(BlackjackUtil.scoreHand(testHand), expected);
    }

    @Test
    public void testScoreHandWithThreeAcesAndATen() {
        Hand testHand = new Hand(Lists.newArrayList(ACE_OF_CLUBS, ACE_OF_CLUBS, ACE_OF_CLUBS, KING_OF_HEARTS));
        BlackjackScore expected = new BlackjackScore(13, true);
        assertEquals(BlackjackUtil.scoreHand(testHand), expected);
    }

    @Test
    public void testScoreHandWithUnnaturalBlackjack() {
        Hand testHand = new Hand(Lists.newArrayList(ACE_OF_CLUBS, KING_OF_HEARTS, KING_OF_HEARTS));
        BlackjackScore expected = new BlackjackScore(21, false);
        assertEquals(BlackjackUtil.scoreHand(testHand), expected);
    }

    @Test
    public void testScoreHandWithBust() {
        Hand testHand = new Hand(Lists.newArrayList(
                ACE_OF_CLUBS,
                KING_OF_HEARTS,
                new Card(Value.EIGHT, Suit.SPADES),
                new Card(Value.THREE, Suit.DIAMONDS)));
        BlackjackScore expected = new BlackjackScore(22, false);
        assertEquals(BlackjackUtil.scoreHand(testHand), expected);
    }
}
