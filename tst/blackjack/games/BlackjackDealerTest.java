package blackjack.games;

import blackjack.TestBase;
import blackjack.evaluation.BlackjackUtil;
import cardgamelib.cards.Card;
import cardgamelib.cards.Suit;
import cardgamelib.cards.Value;
import cardgamelib.storage.Hand;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class BlackjackDealerTest extends TestBase {

    @Test
    public void testDeal() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        BlackjackPlayer player = new BlackjackPlayer(100);
        List<BlackjackPlayer> players = Lists.newArrayList(player);

        dealer.deal(players);

        assertEquals(dealer.getHand().getCards().size(), 2);
        assertEquals(player.getHand().getCards().size(), 2);
    }

    @Test
    public void testPay() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        BlackjackPlayer player = new BlackjackPlayer(100);
        List<BlackjackPlayer> players = Lists.newArrayList(player);

        dealer.getHand().addCards(Lists.newArrayList(ACE_OF_CLUBS, EIGHT_OF_SPADES));
        player.addHand().addCards(Lists.newArrayList(KING_OF_HEARTS, KING_OF_HEARTS));
        player.bet(player.getHand(), 10);

        dealer.pay(players);
        assertEquals(player.getMoney(), 110);
    }

    @Test
    public void testPayWithPlayerBlackjack() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        BlackjackPlayer player = new BlackjackPlayer(100);
        List<BlackjackPlayer> players = Lists.newArrayList(player);

        dealer.getHand().addCards(Lists.newArrayList(KING_OF_HEARTS, EIGHT_OF_SPADES));
        player.addHand().addCards(Lists.newArrayList(KING_OF_HEARTS, ACE_OF_CLUBS));
        player.bet(player.getHand(), 10);

        dealer.pay(players);
        assertEquals(player.getMoney(), 115);
    }

    @Test
    public void testPayWithBlackjackStandoff() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        BlackjackPlayer player = new BlackjackPlayer(100);
        List<BlackjackPlayer> players = Lists.newArrayList(player);

        dealer.getHand().addCards(Lists.newArrayList(KING_OF_HEARTS, ACE_OF_CLUBS));
        player.addHand().addCards(Lists.newArrayList(KING_OF_HEARTS, ACE_OF_CLUBS));
        player.bet(player.getHand(), 10);
        player.insure(player.getHand(), 0);

        dealer.pay(players);
        assertEquals(player.getMoney(), 100);
    }

    @Test
    public void testPayWhenDealerBusts() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        BlackjackPlayer player = new BlackjackPlayer(100);
        List<BlackjackPlayer> players = Lists.newArrayList(player);

        dealer.getHand().addCards(BUSTED.getCards());
        player.addHand().addCards(Lists.newArrayList(KING_OF_HEARTS, THREE_OF_DIAMONDS));
        player.bet(player.getHand(), 10);

        dealer.pay(players);
        assertEquals(player.getMoney(), 110);
    }

    @Test
    public void testPayWhenDealerAndPlayerBust() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        BlackjackPlayer player = new BlackjackPlayer(100);
        List<BlackjackPlayer> players = Lists.newArrayList(player);

        dealer.getHand().addCards(BUSTED.getCards());
        player.addHand().addCards(BUSTED.getCards());
        player.bet(player.getHand(), 10);

        dealer.pay(players);
        assertEquals(player.getMoney(), 90);
    }

    @Test
    public void testPayOnSplit() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        BlackjackPlayer player = new BlackjackPlayer(100);
        List<BlackjackPlayer> players = Lists.newArrayList(player);

        dealer.getHand().addCards(BUSTED.getCards());

        Hand firstHand = player.addHand();
        Hand secondHand = player.addHand();
        firstHand.addCards(Lists.newArrayList(KING_OF_HEARTS, EIGHT_OF_SPADES));
        player.bet(firstHand, 10);
        secondHand.addCards(Lists.newArrayList(KING_OF_HEARTS, THREE_OF_DIAMONDS));
        player.bet(secondHand, 10);

        dealer.pay(players);
        assertEquals(player.getMoney(), 120);
    }

    @Test
    public void testPlay() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        dealer.getHand().addCards(Lists.newArrayList(KING_OF_HEARTS, new Card(Value.SIX, Suit.DIAMONDS)));

        dealer.play();

        assertNotEquals(BlackjackUtil.scoreHand(dealer.getHand()).getNumericalValue(), 16);
    }

    @Test
    public void testPlayAtLimit() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        dealer.getHand().addCards(Lists.newArrayList(ACE_OF_CLUBS, new Card(Value.SIX, Suit.DIAMONDS)));

        dealer.play();

        assertEquals(BlackjackUtil.scoreHand(dealer.getHand()).getNumericalValue(), 17);
    }

    @Test
    public void testPlayWithWeirdEdgeCase() {
        BlackjackDealer dealer = new BlackjackDealer(6);
        dealer.shuffle(10);
        dealer.getHand().addCards(Lists.newArrayList(
                new Card(Value.TEN, Suit.DIAMONDS),
                new Card(Value.FOUR, Suit.HEARTS)));

        dealer.play();
    }
}
