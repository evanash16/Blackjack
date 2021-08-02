package blackjack.games;

import blackjack.evaluation.DoubleDownRule;
import blackjack.evaluation.SplitRule;
import blackjack.exceptions.NoBetException;
import blackjack.exceptions.NotEnoughMoneyException;
import cardgamelib.games.Player;
import cardgamelib.storage.Hand;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BlackjackPlayer extends Player {

    @Getter
    private int money;
    private Map<Hand, Integer> bets;

    public BlackjackPlayer(final int money) {
        this.money = money;
        this.bets = new HashMap<>();
    }

    public int getBet(final Hand hand) {
        if (!this.bets.containsKey(hand)) {
            throw new NoBetException(String.format("A bet hasn't been placed on the hand '%s'.", hand));
        }
        return this.bets.get(hand);
    }

    public void addToBet(final Hand hand, final int money) throws NotEnoughMoneyException {
        bet(hand, this.bets.getOrDefault(hand, 0) + money);
    }

    public void bet(final Hand hand, final int money) throws NotEnoughMoneyException {
        if (this.money < money) {
            throw new NotEnoughMoneyException(String.format("Cannot add %d to bet with %d money remaining.", money, this.money));
        }
        this.money -= money;
        this.bets.put(hand, money);
    }

    public void split(final Hand hand) throws NotEnoughMoneyException {
        if (!SplitRule.passes(hand)) {
            return;
        }

        int currentBet = getBet(hand);
        if (this.money < currentBet) {
            throw new NotEnoughMoneyException(String.format("Cannot split on a %d bet with %d money remaining.", currentBet, this.money));
        }

        Hand newHand = hand.moveCard(hand.getCards().get(0));
        addHand(newHand);
        bet(newHand, currentBet);
    }

    public void doubleDown(final Hand hand) throws NotEnoughMoneyException {
        if (!DoubleDownRule.passes(hand)) {
            return;
        }

        int currentBet = getBet(hand);
        if (this.money < currentBet) {
            throw new NotEnoughMoneyException(String.format("Cannot double down on a %d bet with %d money remaining.", currentBet, this.money));
        }

        addToBet(hand, currentBet);
    }
}
