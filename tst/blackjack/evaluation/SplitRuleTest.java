package blackjack.evaluation;

import blackjack.TestBase;
import cardgamelib.cards.Card;
import cardgamelib.cards.Suit;
import cardgamelib.cards.Value;
import cardgamelib.storage.Hand;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SplitRuleTest extends TestBase {

    @Test
    public void testPasses() {
        Hand testHand = new Hand(Lists.newArrayList(ACE_OF_CLUBS, new Card(Value.ACE, Suit.DIAMONDS)));
        assertTrue(SplitRule.passes(testHand));
    }

    @Test
    public void testPassesFailsWithDifferentCards() {
        Hand testHand = new Hand(Lists.newArrayList(ACE_OF_CLUBS, EIGHT_OF_SPADES));
        assertFalse(SplitRule.passes(testHand));
    }

    @Test
    public void testPassesFailsWithTooManyCards() {
        assertFalse(SplitRule.passes(BUSTED));
    }

}
