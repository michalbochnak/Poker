// Michal Bochnak, Netid: mbochn2
// CS 342 Project #1 - Poker
// 9/13/2017
// UIC, Pat Troy
//
// OpponentPlayers.java
//

import java.util.*;


// this class is the container for all of the computer players
public class OpponentPlayers {

    private int numOfOpponentPlayers;   // number of AI players
    // List of computer players
    private List<OpponentPlayer> opponentPlayers;



    // ----------------------------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------------------------

    // creates the array of opponent players
    // number of the computer players is specified by the parameter
    public OpponentPlayers(int numOfPlayers) {
        numOfOpponentPlayers = numOfPlayers;
        // create new array
        opponentPlayers = new ArrayList<OpponentPlayer>();
        // create desired number of players
        for (int i = 0; i < numOfPlayers; ++i)
            opponentPlayers.add(new OpponentPlayer());
    }


    // ----------------------------------------------------------------------------------------
    // getters
    // ----------------------------------------------------------------------------------------
    public int getNumOfOpponentPlayers() {
        return numOfOpponentPlayers;
    }

    public List<OpponentPlayer> getOpponentPlayers() {
        return opponentPlayers;
    }

    public OpponentPlayer getOpponentPlayerAtIndex(int index) {
        return opponentPlayers.get(index);
    }


    // ----------------------------------------------------------------------------------------
    // methods
    // ----------------------------------------------------------------------------------------

    // displays the all opponent players hands
    public void displayOpponentsHands() {

        // loop through all AI players
        for (int i = 0; i < opponentPlayers.size(); ++i)
            opponentPlayers.get(i).displayTheHand(i);
    }

}
