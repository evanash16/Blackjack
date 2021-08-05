package blackjack.games;

import blackjack.evaluation.*;
import cardgamelib.cards.Value;
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

    public void deal(final List<BlackjackPlayer> players) {
        for (int i = 0; i < 2; i++) {
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
    }

    public void pay(final List<BlackjackPlayer> players) {
        BlackjackScore dealerScore = BlackjackUtil.scoreHand(this.hand);
        for (BlackjackPlayer player : players) {
            do {
                BlackjackScore playerScore = BlackjackUtil.scoreHand(player.getHand());
                Hand playerHand = player.getHand();
                if (!BustRule.passes(playerHand)) {
                    if (NaturalBlackjackRule.passes(this.hand) && this.hand.getCards().get(0).getValue() == Value.ACE) {
                        player.pay((int) ((double) player.removeInsurance(playerHand) * 3)); // insurances
                    }

                    if (NaturalBlackjackRule.passes(playerHand) && !BlackjackRule.passes(this.hand)) {
                        player.pay((int) ((double) player.removeBet(playerHand) * 2.5)); // blackjack
                    } else if (BustRule.passes(this.hand) || playerScore.isGreaterThan(dealerScore)) {
                        player.pay(player.removeBet(playerHand) * 2); // settlement or win
                    } else if (playerScore.isEqualTo(dealerScore)) {
                        player.pay(player.removeBet(playerHand)); // stand-off
                    }
                }
            } while (player.nextHand() != null);
        }
    }

    public void play() {
        while (BlackjackUtil.scoreHand(this.hand).getNumericalValue() < 17) {
            deal(1, this.hand);
        }
    }
}
