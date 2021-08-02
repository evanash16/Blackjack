package blackjack.evaluation;

import blackjack.TestBase;
import cardgamelib.storage.Hand;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DoubleDownRuleTest extends TestBase {

    @Test
    public void testPassesWithNine() {
        Hand testHand = new Hand(Lists.newArrayList(EIGHT_OF_SPADES, ACE_OF_CLUBS));
        assertTrue(DoubleDownRule.passes(testHand));
    }

    @Test
    public void testPassesWithEleven() {
        Hand testHand = new Hand(Lists.newArrayList(EIGHT_OF_SPADES, THREE_OF_DIAMONDS));
        assertTrue(DoubleDownRule.passes(testHand));
    }

    @Test
    public void testPassesFailsWithTooManyCards() {
        Hand testHand = new Hand(Lists.newArrayList(EIGHT_OF_SPADES, ACE_OF_CLUBS, THREE_OF_DIAMONDS));
        assertFalse(DoubleDownRule.passes(testHand));
    }

    @Test
    public void testPassesFailsWithLessThanNine() {
        Hand testHand = new Hand(Lists.newArrayList(THREE_OF_DIAMONDS, THREE_OF_DIAMONDS));
        assertFalse(DoubleDownRule.passes(testHand));
    }

    @Test
    public void testPassesWithMoreThanEleven() {
        Hand testHand = new Hand(Lists.newArrayList(ACE_OF_CLUBS, THREE_OF_DIAMONDS));
        assertFalse(DoubleDownRule.passes(testHand));
    }
}
