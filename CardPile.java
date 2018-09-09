// Michal Bochnak, Netid: mbochn2
// CS 342 Project #1 - Poker
// 9/13/2017
// UIC, Pat Troy
//
// CardPile.java
//

import java.util.*;

// this is the collection of cards
public class CardPile {

    // class private members
    private List<Card> cards;       // deck of card


    // ----------------------------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------------------------

    // default constructor, creates the pile of the card in order :
    // 2 - As, clubs - diamonds - hearts - spades
    public CardPile() {

        // create the list to keep the cards
        cards = new ArrayList<Card>();
        // create the char array with ranks and suits
        char[] ranks = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
        char [] suits = {'C', 'D', 'H', 'S'};

        // iterate through the card pile array and initialize the cards ranks and suits
        // the outer 'for loop' iterates through the 13 card ranks
        // inner 'for loop' iterates through 4 suits
        // 13 x 4 = 52 total cards initialized
        for (int i = 0; i < 13 ; i++) {
            for (int j = 0; j < 4; j++) {
                // create the temp card
                Card tempCard = new Card(ranks[i], suits[j], i+2);
                cards.add(tempCard);
            }
        }
    }


    // ----------------------------------------------------------------------------------------
    // Getters
    // ----------------------------------------------------------------------------------------
    public List<Card> getCards() {
        return cards;
    }


    // ----------------------------------------------------------------------------------------
    // Methods
    // ----------------------------------------------------------------------------------------

    // shuffles the cards
    public void shuffleCardPile() {
        // shuffle couple times for better shuffling
        for (int count = 0; count < 5; count++) {
            // create the temp CardPileClass List
            List<Card> tempCards = new ArrayList<Card>();

            // selects the random card from the pile and insert into tempPile,
            // repeat until original deck is empty
            while (cards.size() > 0) {
                // select random card from the original card pile
                Card random = cards.get(new Random().nextInt(cards.size()));
                // insert selected cart into the temp card pile
                tempCards.add(random);
                // remove the selected card from the original pile
                cards.remove(random);
            }

            // at this point original card pile should be empty, display error if not
            if (cards.size() > 0)
                System.out.println("Original Cards Pile is not empty.");

            // add the shuffled cards from tempPile into the original Pile
            for (int i = 0; i < tempCards.size(); ++i)
                cards.add(tempCards.get(i));

        }

        // display informations about that deck is being shuffled
        System.out.println("The Deck of Cards is being shuffled.");
    }



} // end of CardPile class
