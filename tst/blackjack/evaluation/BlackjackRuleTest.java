package blackjack.evaluation;

import blackjack.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class BlackjackRuleTest extends TestBase {

    @Test
    public void testPasses() {
        assertTrue(BlackjackRule.passes(BLACKJACK));
    }
}
