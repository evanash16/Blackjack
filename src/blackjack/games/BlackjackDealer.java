package blackjack.games;

import blackjack.evaluation.BlackjackRule;
import blackjack.evaluation.BlackjackScore;
import blackjack.evaluation.BlackjackUtil;
import blackjack.evaluation.BustRule;
import cardgamelib.exceptions.EmptyDeckException;
import cardgamelib.exceptions.NoHandsException;
import cardgamelib.games.Dealer;
import cardgamelib.storage.Hand;
import lombok.Getter;

import java.util.List;

public class BlackjackDealer extends Dealer {

    @Getter
    private Hand hand;

    public BlackjackDealer(int n) {
        super(n);
        hand = new Hand();
    }

    public void deal(final List<BlackjackPlayer> players) throws EmptyDeckException {
        for (BlackjackPlayer player : players) {
            Hand playerHand;
            try {
                playerHand = player.getHand();
            } catch (NoHandsException e) {
                playerHand = player.addHand();
            }

            deal(1, playerHand);
        }

        deal(1, this.hand);
    }

    public void pay(final List<BlackjackPlayer> players) throws NoHandsException {
        BlackjackScore dealerScore = BlackjackUtil.scoreHand(this.hand);
        for (BlackjackPlayer player : players) {
            do {
                BlackjackScore playerScore = BlackjackUtil.scoreHand(player.getHand());
                Hand playerHand = player.getHand();
                if (!BustRule.passes(playerHand)) {
                    if (BlackjackRule.passes(playerHand) && !BlackjackRule.passes(this.hand)) {
                        player.pay((int) ((double) player.getBet(playerHand) * 2.5)); // blackjack
                    } else if (BustRule.passes(this.hand) || playerScore.isGreaterThan(dealerScore)) {
                        player.pay(player.getBet(playerHand) * 2); // settlement or win
                    } else if (playerScore.isEqualTo(dealerScore)) {
                        player.pay(player.getBet(playerHand)); // stand-off
                    }
                }
            } while (player.nextHand() != null);
        }
    }

    public void play() throws EmptyDeckException {
        while (BlackjackUtil.scoreHand(this.hand).getNumericalValue() < 17) {
            deal(1, this.hand);
        }
    }
}
