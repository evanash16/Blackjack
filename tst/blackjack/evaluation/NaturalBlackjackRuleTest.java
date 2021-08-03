package blackjack.evaluation;

import blackjack.TestBase;
import cardgamelib.storage.Hand;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class NaturalBlackjackRuleTest extends TestBase {

    @Test
    public void testPasses() {
        assertTrue(NaturalBlackjackRule.passes(BLACKJACK));
    }

    @Test
    public void testPassesFail() {
        Hand testHand = new Hand(Lists.newArrayList(KING_OF_HEARTS, KING_OF_HEARTS, ACE_OF_CLUBS));
        assertFalse(NaturalBlackjackRule.passes(testHand));
    }
}
