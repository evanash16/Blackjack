package blackjack;

import blackjack.games.BlackjackDealer;
import blackjack.games.BlackjackGame;
import blackjack.games.BlackjackPlayer;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        BlackjackDealer dealer = new BlackjackDealer(5);
        BlackjackPlayer player = new BlackjackPlayer(1000);
        List<BlackjackPlayer> players = new ArrayList<>();
        players.add(player);

        BlackjackGame game = new BlackjackGame(dealer, players);
        while (true) {
            game.play();
            game.score();
            game.reset();
        }
    }
}
