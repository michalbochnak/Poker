// Michal Bochnak, Netid: mbochn2
// CS 342 Project #1 - Poker
// 9/13/2017
// UIC, Pat Troy
//
// OpponentPlayer.java
//

import java.util.*;

// keeps the information about the computer player hand (cards in hand)
// and interacts with the AI (Artificial Intelligence)
public class OpponentPlayer extends Player {


    // ----------------------------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------------------------
    public OpponentPlayer() {
        super();        // superclass constructor
    }


    // ----------------------------------------------------------------------------------------
    // Methods
    // ----------------------------------------------------------------------------------------

    // override method
    // display the card in the hand
    public void displayTheHand(int index) {

        // offset the index to match player ID
        int playerID = index + 1;

        // display info
        System.out.println("Opponent Player " + playerID  + " Cards: ");

        // display cards in the hand
        for (int i = 0; i < this.getCards().size(); ++i)
            System.out.print(this.getCards().get(i).getCardName() + " ");

        // show hand rank
        System.out.print("      - " + displayHandType());
        // for formatting
        System.out.println();
    }

    // picks the cards to be exchanged for computer player,
    // function must receive sorted cards
    // return the List with the card insiced to be exchanged
    public List<Integer> pickCardsForExchange() {
        List<Integer> IDs = new ArrayList<Integer>();
        int rank = getHandRankProtected();

        // Straight Flush, Full House, Flush, or Straight
        // No need to exchange
        if (rank == 1 || rank == 3 || rank == 4 || rank == 5) {
            return IDs;
        }
        // Four of a Kind or Two pair, eschange one card
        else if (rank == 2 || rank == 7)  {
            IDs.add(4);
        }
        //  Three of a kind, exchange two cards
        else if (rank == 6) {
            IDs.add(3);
            IDs.add(4);
        }
        // one pair, exchange 3 cards
        else if (rank == 8) {
            IDs.add(2);
            IDs.add(3);
            IDs.add(4);
        }
        else  {
            // look for 4 cards of the same suit
            // return value is the index of card to be exchanged,
            // or -1 if there is no 4 cards of the same suit
            int index = fourCardsWithTheSameSuit();
            if (index != -1) {
                IDs.add(index);
            }
            // check for four in sequence and discard remaining card
            else {
                int cardIndex = fourCardsInSequence();
                if (cardIndex != -1) {
                    IDs.add((cardIndex));
                }
                // check for Ace and discard the remaining four cards if there is Ace
                else {
                    if (this.getCards().get(0).getCardID() == 14) {
                        IDs.add(1);
                        IDs.add(2);
                        IDs.add(3);
                        IDs.add(4);
                    }
                    // find two highest cards and exchange the other 3
                    else {
                        IDs.add(2);
                        IDs.add(3);
                        IDs.add(4);
                    }
                }
            }
        }
        return IDs;
    }

    // check if there are four cards with the same suit, returns the index if so
    // -1 is returned otherwise
    public int fourCardsWithTheSameSuit() {
        int count = 0;
        char suit = this.getCards().get(0).getCardSuit();
        // count the card with the same suit as first card
        for (Card c : this.getCards()) {
            if (c.getCardSuit() == suit) {
                ++count;
            }
        }

        // four same suits found
        if (count == 4) {
            return cardToBeExchanged(suit);
        }
        // check if there is 4 cards of suit same as second card
        else {
            count = 0;
            suit = this.getCards().get(1).getCardSuit();
            for (Card c : this.getCards()) {
                if (c.getCardSuit() == suit) {
                    ++count;
                }
            }

            // four same found
            if (count == 4) {
                return cardToBeExchanged(suit);
            }
            // not found
            else {
                return -1;
            }
        }
    }

    // find the card with different 'suit' and returns its index
    public int cardToBeExchanged(char suit) {
        int index = -1;     // index
        // loop through all cards
        for (int i = 0; i < 5; i++) {
            if (this.getCards().get(i).getCardSuit() != suit) {
                return index;   // found
            }
        }
        return index;
    }

    // checks if there in four cards in the sequence
    public int fourCardsInSequence() {
        int count = 1;  // number of cards in the sequence
        for (int i = 0; i < 4; ++i) {
            // counts if cards are in sequence
            if (this.getCards().get(i).getCardID() == this.getCards().get(i+1).getCardID()+1) {
                count++;
            }
        }

        // four found
        if (count == 4) {
            return cardToBeExchanged();
        }
        else {
            // check for special case with Ace being the lowest card
            if (this.getCards().get(0).getCardID() == 14 &&
                    this.getCards().get(2).getCardID() == 4 &&
                    this.getCards().get(3).getCardID() == 3 &&
                    this.getCards().get(4).getCardID() == 2) {
                return 2;
            }
            else return -1;
        }
    }

    // finds the card which is out of sequence and returns the index, -1 otherwise
    // it could be first or last card, since the cards are sorted
    public int cardToBeExchanged() {
        if (this.getCards().get(0).getCardID() != this.getCards().get(1).getCardID()+1)
            return 0;       // first
        else
            return 4;       // last
    }




}   // end of opponent player
