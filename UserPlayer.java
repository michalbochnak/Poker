// Michal Bochnak, Netid: mbochn2
// CS 342 Project #1 - Poker
// 9/13/2017
// UIC, Pat Troy
//
// UserPlayer.java
//


// keeps the information about user's hand (cards in the hand)
// and interacts with the user interface
public class UserPlayer extends Player {

    // ----------------------------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------------------------
    public UserPlayer() {
        // call constructor form the super class
        super();
    }


    // ----------------------------------------------------------------------------------------
    // Methods
    // ----------------------------------------------------------------------------------------

    // displays suggestions for user
    public void showSuggestions() {

        // user hand is High Card, check if Ace is there, if so
        // display suggestion to exchange 4 cards
        if (this.getHandRank() == 9) {
            if (this.getCards().get(0).getCardID() == 14) {
                System.out.println("Ace in hand. You can discard up to 4 cards.");
            }
            // no Ace, 3 cards can be discarded
            else {
                System.out.println("Up to three cards can be discarded.");
            }
        }
    }

}   // end of UserPlayer class
