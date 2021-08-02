package blackjack.evaluation;

import blackjack.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class BustRuleTest extends TestBase {

    @Test
    public void testPasses() {
        assertTrue(BustRule.passes(BUSTED));
    }
}
