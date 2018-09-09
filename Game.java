// Michal Bochnak, Netid: mbochn2
// CS 342 Project #1 - Poker
// 9/13/2017
// UIC, Pat Troy
//
// Game.java
//

import java.util.*;

// This class is to contain the method main. This class will also keep
// track of the particulars of the game, such as the order of actions
// of the game (shuffling, dealing, discarding and determining the winner)
// and the evaluation of the hand (does the player have a Straight Flush,
// Four of a Kind, Full House, etc.).
public class Game {

    public static void main (String[] args) {

        // call function to control game
        processGame();
    }


    // method to control all of the game and events in the game
    public static void processGame() {

        // create the class with game info
        // and assign number of opponents chosen by user
        GameInfo gameInfo = new GameInfo(getNumberOfComputerPlayers());
        // create pile of card
        CardPile cardPile = new CardPile();
        // create the UserPlayer Class
        UserPlayer userPlayer = new UserPlayer();
        // create needed number of the computer opponents players
        OpponentPlayers opponentPlayers =
                new OpponentPlayers(gameInfo.getNumOfComputerPlayers());


        // shuffle the deck with the cards
        cardPile.shuffleCardPile();

        // dealt the cards to the players
        dealtTheCards(userPlayer, opponentPlayers, cardPile, gameInfo);

        // sortByStrongCard the cards of each player
        sortAllHandsByStrongCard(userPlayer, opponentPlayers);

        // set the hand ranks for each hand
        setHandRanks(userPlayer, opponentPlayers);

        // sort cards for all players by hand rank
        sortAllHandsByHandRank(userPlayer, opponentPlayers);

        // exchange the cards for all the players
        discardAndDraw(userPlayer, opponentPlayers, cardPile);

        // sortByStrongCard the cards of each player
        sortAllHandsByStrongCard(userPlayer, opponentPlayers);

        // set the hand ranks for each hand
        setHandRanks(userPlayer, opponentPlayers);

        // sort cards for all players by hand rank
        sortAllHandsByHandRank(userPlayer, opponentPlayers);

        // find and list the winner
        findAndListTheWinner(userPlayer, opponentPlayers);

        // thank you the user for playing
        displayEndGreeting();
    }

    // gets the number of desired AI players from the user
    public static int getNumberOfComputerPlayers() {

        // Ask user how many computer players he wish to play with
        System.out.print("Enter the number of computer opponents" +
                " you wish to play with (1-5): ");

        // instance of the scanner
        Scanner sc = new Scanner(System.in);

        // handle input
        int a = -1;
        if (sc.hasNextInt())
            a = sc.nextInt();

        // verify if input is correct, prompt again if needed
        while (a < 1 || a > 5) {
            System.out.println("Number must be 1 - 5. Try again.");
            // Ask user how many computer players he wish to play with
            System.out.print("Enter the number of computer opponents" +
                    " you wish to play with (1-5): ");
            Scanner sc2 = new Scanner(System.in);
            if (sc2.hasNextInt())
                a = sc2.nextInt();
        }

        return a;       // return number of players
    }

    // dealt the cards to all the players
    public static void dealtTheCards(UserPlayer user, OpponentPlayers compPlayers,
                                     CardPile deck, GameInfo gameInfo) {

        // distribute the cards
        for (int i = 0; i < 5; ++i) {
            // take the card from the top of  the deck
            Card tempCard = deck.getCards().get(0);
            // hand the card to player and remove it from the deck
            user.receiveCard(tempCard);
            deck.getCards().remove(0);
            // hand cards to the computer opponents
            for (int j = 0; j < compPlayers.getOpponentPlayers().size(); ++j) {
                // take the card from the top of the deck
                tempCard = deck.getCards().get(0);
                // hand the card to the computer player
                compPlayers.getOpponentPlayers().get(j).receiveCard(tempCard);
                // remove the card from the deck
                deck.getCards().remove(0);
            }
        }

        // display information about the event
        System.out.println("The cards are being dealt to "
                + gameInfo.getNumOfTotalPlayers() + " Players.");
    }

    // discard the cards from the user and AI players
    // method is devided into two submethods
    public static void discardAndDraw(UserPlayer user, OpponentPlayers compPlayers,
                                      CardPile deck) {

        // exchange user cards
        exchangeUserCards(user, deck);

        // exchange computer opponents cards
        exchangeOpponentsCards(compPlayers, deck);
    }

    // perform the change of the cards for the user
    public static void exchangeUserCards(UserPlayer user, CardPile deck) {

        // display the cards in the user's hand
        user.displayTheHand();

        // display suggestions about possible exchanges if user have High Card hand
        user.showSuggestions();

        // list to hold the IDs of chosen cards to be exchanged
        List <Integer> cardsToExchangeIDs = new ArrayList<Integer>();

        // use loop to collect the ID's of card to be exchanged
        do {
            // create new instance of the scanner
            Scanner sc = new Scanner(System.in);
            // clear the list
            cardsToExchangeIDs.clear();
            // prompt the user to specify which cards he wants to exchange
            System.out.print("List the cards numbers you wish to discard "
                    + "fallowed by some letter (example: 4 5 d): ");
            while (sc.hasNextInt()) {
                cardsToExchangeIDs.add(sc.nextInt() -1);
            }
            // clear the input line
            sc.nextLine();
            // verify if all of the specified cards qualify for the exchange
        } while (!correctInput(cardsToExchangeIDs, user));

        // sortByStrongCard the indices before sending to exchange function
        Collections.sort(cardsToExchangeIDs);
        // exchange the user cards
        user.exchangeCards(cardsToExchangeIDs, deck);
    }

    // function verifies if the user entered values for cards to be exchanged are valid
    public static boolean correctInput(List<Integer> cardsToExchangeIDs, UserPlayer user) {

        // bool to be returned, true if input correct, false otherwise
        boolean inputCorrect = true;

        // to many cards
        if (cardsToExchangeIDs.size() > 4)
            inputCorrect = false;
        // 4 cards to exchange, check if remaining card is Ace
        else if(cardsToExchangeIDs.size() == 4) { 
            // check if remaining card is Ace
            int leftCardIndex = findLeftCardID(cardsToExchangeIDs);
            Card leftCard = user.getCards().get(leftCardIndex-1);
            // Ace
            if (leftCard.getCardID() == 14)
                inputCorrect = true;
            // not Ace
            else
                inputCorrect = false;
        }

        if (inputCorrect) {             // check if numbers are in the range
            for (int a : cardsToExchangeIDs) {
                if (a < 0 || a > 4)
                    inputCorrect = false;
            }
        }

        // display information if input is not correct
        if (!inputCorrect)
            System.out.println("Numbers must be 1 - 5. " +
                    "Max number of cards to exchange is 4. " +
                    "The 4th card can be exchanged only if the remaining card is Ace. " +
                    "Try again.");

        return inputCorrect;
    }

    // finds and returns the ID of the leftmost card
    public static int findLeftCardID(List <Integer> cardsToExchangeIDs) {
        if (!cardsToExchangeIDs.contains(1))
            return 1;
        else if(!cardsToExchangeIDs.contains(2))
            return 2;
        else if (!cardsToExchangeIDs.contains(3))
            return 3;
        else if (!cardsToExchangeIDs.contains(4))
            return 4;
        else
            return 5;
    }

    // exchange the cards for the all AI players
    public static void exchangeOpponentsCards(OpponentPlayers compPlayers,
                                              CardPile deck) {

        // loop through all the computer players
        for (int i = 0; i < compPlayers.getNumOfOpponentPlayers(); ++i ) {
            // decide which cards should be exchanged
            List<Integer> cardsToBeExchangedIDs =
                    compPlayers.getOpponentPlayerAtIndex(i).pickCardsForExchange();

            // exchange the cards
            compPlayers.getOpponentPlayerAtIndex(i).
                    exchangeCards(cardsToBeExchangedIDs, deck);

            // display info how many cards each player exchanged
            System.out.println("Computer Player " + (i+1) +  " has discarded "
                    + cardsToBeExchangedIDs.size() + " cards.");
        }
    }

    // display the end game greetings
    public static void displayEndGreeting() {
        System.out.println("Thank you for playing the game of Poker!");
        System.out.println("I hope you had a great time!");
    }

// displays the hands of all of the players
    public static void displayTheHands(UserPlayer user, OpponentPlayers compPlayers) {

        // display the user hand
        user.displayTheHand();
        // display the computer players hands
        compPlayers.displayOpponentsHands();
    }

    // verify who has the best hand and list the winner
    public static void findAndListTheWinner(UserPlayer user,
                                            OpponentPlayers compPlayers) {

        // find the list of players with strongest hand, can be one or more
        // two players with full house are equal at this point
        List<Player> bestHands= findPlayersWithBestHand(user, compPlayers);

        // one winner
        Player singleWinner = bestHands.get(0);
        // only one player on the list, he is the winner
        if (bestHands.size() == 1) {
            singleWinner = bestHands.get(0);
        }
        // more players on the list, find the best
        else {
            // keeps only winner, or more players if there is a draw
            findBestOf(bestHands);
            // one player wins
            if(bestHands.size() == 1)
                singleWinner = bestHands.get(0);
        }

        // show the hands
        displayTheHands(user, compPlayers);

        // list the winner
        if (bestHands.size() == 1) {
            if (singleWinner == user)
                System.out.println("You won! Congratulations!");
                // find the computer player ID to display
            else {
                for (int i = 0; i < compPlayers.getNumOfOpponentPlayers(); ++i) {
                    if (singleWinner == compPlayers.getOpponentPlayers().get(i)) {
                        System.out.println("Computer player " + (i + 1) + " won! Good luck next time!");
                        break;
                    }
                }
            }
        }
        // tie, list players
        else {
            System.out.print("Tie between: ");
            for (int i = 0; i < bestHands.size(); ++i) {
                for (int k = 0; k < compPlayers.getNumOfOpponentPlayers(); ++k) {
                    if (bestHands.get(i) == compPlayers.getOpponentPlayers().get(k)) {
                        System.out.println("Opponent Player " + (k + 1) + " ");
                    }
                }
                if (bestHands.get(i) == user) {
                    System.out.println("You ");
                }
            }
        }
    }

    // sorts the hands from best to worst card (Ace - 2)
    public static void sortAllHandsByStrongCard(UserPlayer user,
                                                OpponentPlayers compPlayers) {

        // sortByStrongCard user hand
        user.sortByStrongCard();
        // sortByStrongCard computer player hands
        for (int i = 0; i < compPlayers.getNumOfOpponentPlayers(); ++i) {
            compPlayers.getOpponentPlayerAtIndex(i).sortByStrongCard();
        }
    }

    // cards must be sorted by sortAllHandsByStrongCard()
    // before sorting with this function
    // sorts the cards by the rank of hands
    public static void sortAllHandsByHandRank(UserPlayer usr,
                                              OpponentPlayers compPlayers) {
        // sort by hand rank
        usr.sortByHandRank();
        // sortByStrongCard computer player hands
        for (int i = 0; i < compPlayers.getNumOfOpponentPlayers(); ++i) {
            compPlayers.getOpponentPlayerAtIndex(i).sortByHandRank();
        }
    }

    // sets the hand rank for every player
    public static void  setHandRanks(UserPlayer user, OpponentPlayers compPlayers) {

        // set hand rank for user
        user.setHandRank(user);
        // set hand ranks for AI players
        List<OpponentPlayer> opPlayers = compPlayers.getOpponentPlayers();
        for (OpponentPlayer p : opPlayers)
            p.setHandRank(p);
    }

    // finds the players with the best hands, in example each player with
    // full house is equal at this point
    public static List<Player>  findPlayersWithBestHand(UserPlayer user,
                                                        OpponentPlayers compPlayers) {

        // List to keep all players with the best rank, if the same
        List <Player> bestHandsPlayers = new ArrayList<Player>();
        int bestRank = user.getHandRank();
        // find best hand rank among players
        for (OpponentPlayer p : compPlayers.getOpponentPlayers())
            if (p.getHandRank() < bestRank)
                bestRank = p.getHandRank();

        // if user has best rank, add user to list
        if (user.getHandRank() == bestRank)
            bestHandsPlayers.add(user);

        // loop thru AI players and add them to the list if they have best rank
        for (OpponentPlayer p : compPlayers.getOpponentPlayers()) {
            if (p.getHandRank() == bestRank)
                bestHandsPlayers.add(p);
        }

        return bestHandsPlayers;        // return the list
    }

    // finds best player from the List
    public static Player findBestOf(List<Player> bestHands) {

        // set the point for hands, helps when determining the winner
        for (Player p: bestHands)
            p.setTheHandPoints();

        // grab first player
        Player bestPlayer = bestHands.get(0);
        double maxScore = 0;

        // find max score
        for (Player p : bestHands) {
            if (p.getHandPoints() > maxScore) {
                maxScore = p.getHandPoints();
                bestPlayer = p;
            }
        }

        // remove all players below high score, more than 1 player can remain if
        // there is a tie
        for (int i = 0; i < bestHands.size(); ++i) {
            if ((bestHands.get(i).getHandPoints() - maxScore) < 0.00000000001) {
                bestHands.remove(i);
            }
        }

        return bestPlayer;
    }



}   // end of Game class