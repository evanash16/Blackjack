package blackjack.games;

import blackjack.evaluation.*;
import blackjack.exceptions.NoInsuranceException;
import cardgamelib.cards.Value;
import cardgamelib.games.Game;
import cardgamelib.storage.Hand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame extends Game<BlackjackDealer, BlackjackPlayer> {

    private Scanner scanner;

    private enum Action {
        STAND("Stand"),
        HIT("Hit"),
        SPLIT("Split"),
        DOUBLEDOWN("Double down");

        @Getter
        private String action;

        Action(final String action) {
            this.action = action;
        }
    }

    public BlackjackGame(final BlackjackDealer dealer, final List<BlackjackPlayer> players) {
        super(dealer, players);
        dealer.shuffle(7);
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        BlackjackDealer dealer = getDealer();
        List<BlackjackPlayer> players = getPlayers();

        for (BlackjackPlayer player : players) {
            player.addHand();
            int bet;
            do {
                System.out.println();
                System.out.println(String.format("Your money: %d", player.getMoney()));
                System.out.print("Bet (min. $2, max. $500): ");
                bet = scanner.nextInt();
            } while (bet < 2 || bet > 500);

            player.bet(player.getHand(), bet);
        }

        dealer.deal(players);
        System.out.println();
        System.out.println(String.format("Up card: %s", dealer.getHand().getCards().get(0)));

        if (dealer.getHand().getCards().get(0).getValue() == Value.ACE) {
            for (BlackjackPlayer player : players) {
                System.out.println();
                System.out.println(String.format("Your hand: %s", player.getHand()));
                int maxInsurance = player.getBet(player.getHand()) / 2;
                int insurance;
                do {
                    System.out.print(String.format("Insurance (min. $0, max. $%d): ", maxInsurance));
                    insurance = scanner.nextInt();
                } while (insurance > maxInsurance);

                player.insure(player.getHand(), insurance);
            }
        }

        if (NaturalBlackjackRule.passes(dealer.getHand())) {
            System.out.println();
            System.out.println(String.format("Dealer's hand: %s", dealer.getHand()));
            return;
        }

        for (BlackjackPlayer player : players) {
            do {
                boolean done = false;
                if (done) {
                    break;
                }

                Hand playerHand = player.getHand();
                printPlayer(player);

                List<Action> validActions = new ArrayList<>(Arrays.asList(Action.STAND, Action.HIT));
                if (SplitRule.passes(playerHand)) {
                    validActions.add(Action.SPLIT);
                }
                if (DoubleDownRule.passes(playerHand)) {
                    validActions.add(Action.DOUBLEDOWN);
                }
                Action selectedAction;
                do {
                    int selection;
                    do {
                        System.out.println("Would you like to...");
                        for (int i = 0; i < validActions.size(); i++) {
                            Action action = validActions.get(i);
                            System.out.println(String.format("%d) %s", i + 1, action.getAction()));
                        }
                        System.out.print("Action: ");
                        selection = scanner.nextInt();
                    } while (selection <= 0 || selection > validActions.size());
                    selectedAction = validActions.get(selection - 1);
                    switch (selectedAction) {
                        case HIT:
                            dealer.deal(1, playerHand);
                            break;
                        case SPLIT:
                            validActions.remove(Action.SPLIT);
                            validActions.remove(Action.DOUBLEDOWN);
                            player.split(playerHand);
                            break;
                        case DOUBLEDOWN:
                            validActions.remove(Action.DOUBLEDOWN);
                            validActions.remove(Action.SPLIT);
                            player.doubleDown(playerHand);
                            dealer.deal(1, playerHand);
                            done = true;
                            break;
                        default:
                            done = true;
                            break;
                    }
                    printPlayer(player);
                } while (!done && selectedAction != Action.STAND && !BustRule.passes(playerHand));
            } while (player.nextHand() != null);
        }

        dealer.play();
        System.out.println();
        System.out.println(String.format("Dealer's hand: %s", dealer.getHand()));
    }

    public void score() {
        getDealer().pay(getPlayers());
    }

    public void reset() {
        BlackjackDealer dealer = getDealer();
        List<BlackjackPlayer> players = getPlayers();

        for (BlackjackPlayer player : players) {
            dealer.collect(player.removeHands());
        }
        dealer.collect(dealer.getHand());

        if (dealer.getDeck().getRemainingCardCount() < 75) {
            dealer.shuffle(7);
        }
    }

    private void printPlayer(final BlackjackPlayer player) {
        Hand currentHand = player.getHand();
        System.out.println("------------------------");
        System.out.println(String.format("Money: %d", player.getMoney()));
        System.out.println(String.format("Bet: $%d", player.getBet(currentHand)));
        try {
            System.out.println(String.format("Insurance: $%d", player.getInsurance(currentHand)));
        } catch (NoInsuranceException ignored) {
        }
        System.out.println(String.format("Hand: %s", currentHand));
        System.out.println("------------------------");
    }
}
