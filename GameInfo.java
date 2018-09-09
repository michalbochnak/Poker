// Michal Bochnak, Netid: mbochn2
// CS 342 Project #1 - Poker
// 9/13/2017
// UIC, Pat Troy
//
// GameInfo.java
//

// holds info about the game such as how many computer opponents etc
public class GameInfo {

    private int numOfComputerPlayers;
    private int numOfTotalPlayers;


    // ----------------------------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------------------------

    // constructor that specifies number of AI players
    public GameInfo(int numComputerPlayers) {
        numOfComputerPlayers = numComputerPlayers;
        numOfTotalPlayers = numComputerPlayers + 1;
    }


    // ----------------------------------------------------------------------------------------
    // getters
    // ----------------------------------------------------------------------------------------
    public int getNumOfComputerPlayers() {
        return numOfComputerPlayers;
    }

    public int getNumOfTotalPlayers() {
        return numOfTotalPlayers;
    }



}       // end of GameInfo class


