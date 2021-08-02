package blackjack.games;

import blackjack.TestBase;
import blackjack.exceptions.NoBetException;
import blackjack.exceptions.NotEnoughMoneyException;
import cardgamelib.cards.Card;
import cardgamelib.cards.Suit;
import cardgamelib.cards.Value;
import cardgamelib.storage.Hand;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static org.testng.Assert.assertEquals;

public class BlackjackPlayerTest extends TestBase {

    @Test
    public void testPay() {
        BlackjackPlayer player = new BlackjackPlayer(10);
        player.pay(10);

        assertEquals(player.getMoney(), 20);
    }

    @Test(expectedExceptions = {NoBetException.class})
    public void testGetBetFailsWithoutBetOnHand() {
        BlackjackPlayer player = new BlackjackPlayer(100);
        Hand newHand = player.addHand();

        player.getBet(newHand);
    }

    @Test
    public void testBet() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(100);
        Hand newHand = player.addHand();

        int expectedBet = 10;
        player.bet(newHand, expectedBet);

        assertEquals(player.getBet(newHand), expectedBet);
    }

    @Test
    public void testAddToBet() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(100);
        Hand newHand = player.addHand();

        int expectedBet = 10;
        player.bet(newHand, expectedBet);
        assertEquals(player.getBet(newHand), expectedBet);

        player.addToBet(newHand, expectedBet);
        assertEquals(player.getBet(newHand), expectedBet * 2);
    }

    @Test(expectedExceptions = {NotEnoughMoneyException.class})
    public void testBetFailsWithOverBet() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(5);
        Hand newHand = player.addHand();

        player.bet(newHand, 10);
    }

    @Test
    public void testSplit() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(100);
        Hand newHand = player.addHand();

        player.bet(newHand, 10);

        Card splitCard = new Card(Value.ACE, Suit.DIAMONDS);
        newHand.addCards(Lists.newArrayList(ACE_OF_CLUBS, splitCard));

        player.split(newHand);

        assertEquals(player.getHands().size(), 2);
        assertEquals(player.getHand(), new Hand(Lists.newArrayList(splitCard)));
        assertEquals(player.nextHand(), new Hand(Lists.newArrayList(ACE_OF_CLUBS)));
    }

    @Test
    public void testSplitFailsAsNoop() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(100);
        Hand newHand = player.addHand();

        player.bet(newHand, 10);

        Hand expectedHand = new Hand(Lists.newArrayList(ACE_OF_CLUBS, KING_OF_HEARTS));
        newHand.addCards(expectedHand.getCards());

        player.split(newHand);
        assertEquals(expectedHand, newHand);
    }

    @Test(expectedExceptions = {NotEnoughMoneyException.class})
    public void testSplitFailsAsNoopWithNotEnoughMoney() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(10);
        Hand newHand = player.addHand();

        player.bet(newHand, 10);

        Hand expectedHand = new Hand(Lists.newArrayList(ACE_OF_CLUBS, new Card(Value.ACE, Suit.SPADES)));
        newHand.addCards(expectedHand.getCards());

        player.split(newHand);
        assertEquals(expectedHand, newHand);
    }

    @Test
    public void testDoubleDown() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(100);
        Hand newHand = player.addHand();

        int expectedBet = 10;
        player.bet(newHand, expectedBet);

        newHand.addCards(Lists.newArrayList(EIGHT_OF_SPADES, THREE_OF_DIAMONDS));

        player.doubleDown(newHand);

        assertEquals(player.getBet(newHand), expectedBet * 2);
    }

    @Test
    public void testDoubleDownFailsAsNoop() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(100);
        Hand newHand = player.addHand();

        int expectedBet = 10;
        player.bet(newHand, expectedBet);

        newHand.addCards(Lists.newArrayList(EIGHT_OF_SPADES, KING_OF_HEARTS));

        player.doubleDown(newHand);

        assertEquals(player.getBet(newHand), expectedBet);
    }

    @Test(expectedExceptions = {NotEnoughMoneyException.class})
    public void testDoubleDownFailsAsNoopWithNotEnoughMoney() throws Exception {
        BlackjackPlayer player = new BlackjackPlayer(10);
        Hand newHand = player.addHand();

        int expectedBet = 10;
        player.bet(newHand, expectedBet);

        newHand.addCards(Lists.newArrayList(EIGHT_OF_SPADES, THREE_OF_DIAMONDS));

        player.doubleDown(newHand);

        assertEquals(player.getBet(newHand), expectedBet);
    }
}
