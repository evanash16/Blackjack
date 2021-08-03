package blackjack.games;

import blackjack.evaluation.DoubleDownRule;
import blackjack.evaluation.SplitRule;
import blackjack.exceptions.NoBetException;
import blackjack.exceptions.NoInsuranceException;
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
    private Map<Hand, Integer> insurances;

    public BlackjackPlayer(final int money) {
        super();

        this.money = money;
        this.bets = new HashMap<>();
        this.insurances = new HashMap<>();
    }

    public void pay(final int money) {
        this.money += money;
    }

    public int getBet(final Hand hand) {
        if (!this.bets.containsKey(hand)) {
            throw new NoBetException(String.format("A bet hasn't been placed on the hand '%s'.", hand));
        }
        return this.bets.get(hand);
    }

    public int removeBet(final Hand hand) {
        if (!this.bets.containsKey(hand)) {
            throw new NoBetException(String.format("A bet hasn't been placed on the hand '%s'.", hand));
        }
        return this.bets.remove(hand);
    }

    public void addToBet(final Hand hand, final int money) {
        bet(hand, this.bets.getOrDefault(hand, 0) + money);
    }

    public void bet(final Hand hand, final int money) {
        if (this.money < money) {
            throw new NotEnoughMoneyException(String.format("Cannot add %d to bet with %d money remaining.", money, this.money));
        }
        this.money -= money;
        this.bets.put(hand, money);
    }

    public void insure(final Hand hand, final int money) {
        if (this.money <= money) {
            throw new NotEnoughMoneyException(String.format("Cannot place %d of insurance with %d money remaining.", money, this.money));
        }
        this.money -= money;
        this.insurances.put(hand, money);
    }

    public int getInsurance(final Hand hand) {
        if (!this.insurances.containsKey(hand)) {
            throw new NoInsuranceException(String.format("Insurance hasn't been played on the hand '%s'", hand));
        }
        return this.insurances.get(hand);
    }

    public int removeInsurance(final Hand hand) {
        if (!this.insurances.containsKey(hand)) {
            throw new NoInsuranceException(String.format("Insurance hasn't been played on the hand '%s'", hand));
        }
        return this.insurances.remove(hand);
    }

    public void split(final Hand hand) {
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

    public void doubleDown(final Hand hand) {
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
