// Michal Bochnak, Netid: mbochn2
// CS 342 Project #1 - Poker
// 9/13/2017
// UIC, Pat Troy
//
// Card.java
//


// keeps the information about each card
public class Card {

    // class private members
    // cardID is used for sorting and also represent how strong the card is,
    // 2 - 9, then T - 10, J - 11, Q -12, K - 13, A - 14
    private char cardRank;    // rank of each card
    private char cardSuit;      // suit of each card
    private int cardID;             // used for sorting


    // ----------------------------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------------------------

    // constructor with specified initialization
    public Card(char rank, char suit, int id) {
        cardRank = rank;
        cardSuit = suit;
        cardID = id;
    }


    // ----------------------------------------------------------------------------------------
    // getters
    // ----------------------------------------------------------------------------------------
    public char getCardRank() {
        return cardRank;
    }

    public char getCardSuit() {
        return cardSuit;
    }

    public String getCardName() {
        return Character.toString(cardRank) + Character.toString(cardSuit);
    }

    public int getCardID() {
        return cardID;
    }


}   // end of Card class
