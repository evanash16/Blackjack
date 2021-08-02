package blackjack.evaluation;

import blackjack.TestBase;
import cardgamelib.storage.Hand;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class HitRuleTest extends TestBase {

    @Test
    public void testPasses() {
        Hand testHand = new Hand(Lists.newArrayList(EIGHT_OF_SPADES, THREE_OF_DIAMONDS));
        assertTrue(HitRule.passes(testHand));
    }

    @Test
    public void testPassesFailsWithBlackjack() {
        assertFalse(HitRule.passes(BLACKJACK));
    }

    @Test
    public void testPassesFailsWithBusted() {
        assertFalse(HitRule.passes(BUSTED));
    }
}
