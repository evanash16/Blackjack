# Games

## BlackjackGame (Game)

### Methods
* play() : plays a single hand of Blackjack
  1. If there aren't enough cards to play (somewhere between 60 and 75 cards remaining), the deck is shuffled.
  1. Collect bets from each player (min. $2, max. $500).
  1. Deal one card to each player, and one face up to the dealer.
  1. Deal a second card to each player, and one face down to the dealer.
  1. If the upcard is an ace, collect an optional insurance (up to half of their bet) from each player.
    * If the dealer doesn't have blackjack, pay out all "natural" Blackjacks 1.5:1.
    * If the dealer has blackjack, pay all insurances 2:1.
  1. For each remaining player, take their action (stand, hit, split, double down).
    * If they split, they can hit or stand on each remaining hand.
      * If the hand has more than two elements, this is not a valid action.
      * If the two cards in the hand aren't of the same denomination, this is not a valid action.
    * If they double down, they can only receive one more card.
      * If they have more than two cards, this is not a valid action.
      * If the two cards total less than 9 or more than 11, this is not a valid action.
    * If they hit and bust, take their bet.
  1. Play the dealer (hits on soft 17).
* score(): 
  * If the dealer busted, a settlement is reached and all bets are paid out.
  * Otherwise, pay all bets out on hands which beat the dealer.

## BlackjackDealer (Dealer)

### Attributes
* hand (Hand)

### Methods
* getHand() &rarr; Hand :  return the dealer's hand
* deal(List<BlackjackPlayer> players) : deal one card to each player, and one card face up to self. Then deal one card to each player, and one card face down to self.
  * If the dealer already has two cards, an exception should be thrown.
  * The second card should be dealt face down.
* pay(List<BlackjackPlayer> players) : for each player's hand(s), pay out or collect bets
  * If the dealer busted, pay out all non-busted hands (settlements).
  * Otherwise, pay all bets on hands which exceed the dealer's hand. Collect all others.
* play() : hit until the value of the hand is 17 or more.

## BlackjackPlayer (Player)

### Attributes
* money (int)
* bets (Map<Hand, int>)
* insurances (Map<Hand, int>)

### Methods
* getMoney() &rarr; int : returns the amount of money the player has left
* pay(int money) : pays the player
* getBet(Hand hand) &rarr; int : returns the amount of money placed on a given hand. If no bet has been placed on the hand, an exception should be thrown.
* removeBet(Hand hand) &rarr; int : removes a bet on a hand, and returns the amount of money placed on the hand. If no bet has been placed on the hand, an exception should be thrown.
* addToBet(Hand hand, int money) : adds additional money to the hand. If there isn't enough money available, an exception should be thrown.
* bet(Hand hand, int money) &rarr; int : places a bet on a given hand. If there isn't enough money available, an exception should be thrown.
* insure(Hand hand, int money) : places an insurance bet on a given hand. 
  * If there isn't enough money available, an exception should be thrown. 
  * If no bet has been placed on the hand, an exception should be thrown.
* getInsurance(Hand hand) &rarr; int : returns the amount of insurances placed on a given hand. If no insurance has been placed, an exception should be thrown.
* removeInsurance(Hand hand) &rarr; int: removes insurance on a hand, and returns the amount of money placed on the hand. If no insurance has been placed on the hand, an exception should be thrown.
* split(Hand hand) : effectively wraps `hand.moveCard(Card card)`. If there isn't enough money available, an exception should be thrown.
* doubleDown(Hand hand) : doubles the bet on a given hand. If there isn't enough money available, an exception should be thrown.

# Evaluation

## BlackjackUtil

### Methods
* BlackjackScore scoreHand(Hand hand) &rarr; Score : returns the score for a hand
  * If the hand is comprised of an ace and non-ten card, the hand is soft.

## BlackjackScore (Score)

### Attributes
* value (int)
* soft (boolean)

### Methods
* isSoft() &rarr; boolean : returns true
* getNumericalValue() &rarr; int : returns the maximum non-busting value of the blackjack score
* isGreaterThan(Score other) &rarr; boolean : returns `true` if the maximum value of this score is greater than that of the other
* isLessThan(Score other) &rarr; boolean : returns `true` if the maximum value of this score is less than that of the other
* isEqualTo(Score other) &rarr; boolean : returns `true` if the maximum value of this score is equal to that of the other
* toString() &rarr; String : returns a string representation of the score (ex. `BLACKJACK.toString()` &rarr; `Soft 21`)
* equals(Object other) &rarr; boolean : returns `true` if `other` is a Score and `isEqualTo(other)` is `true`

## BlackjackRule (Rule)

### Methods
* passes(Hand hand) &rarr; boolean : returns `true` if the hand is blackjack (maximum non-busting score is 21)

## BustRule (Rule)

### Methods
* passes(Hand hand) &rarr; boolean : returns `true` if the hand is busted

## SplitRule (Rule)

### Methods
* passes(Hand hand) &rarr boolean : returns `true` if the hand is splittable
  * If the hand has more than two elements, this is not a valid action.
  * If the two cards in the hand aren't of the same denomination, this is not a valid action.
  

## DoubleDownRule (Rule)

### Methods
* passes(Hand hand) &rarr; boolean : returns `true` if the hand is double down-able
  * If they have more than two cards, this is not a valid action.
  * If the two cards total less than 9 or more than 11, this is not a valid action.

## HitRule (Rule)

### Methods
* passes(Hand hand) &rarr; boolean : returns `true` if the hand isn't busted
  * If the minimum value is greater than 21, this is not a valid action.

