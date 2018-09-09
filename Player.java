// Michal Bochnak, Netid: mbochn2
// CS 342 Project #1 - Poker
// 9/13/2017
// UIC, Pat Troy
//
// Player.java
//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// keeps information about each player's hand
public class Player {

    // -1 indicates that hand was not evaluated yet,
    // 1-9 represents the hand ranks as fallows: 1. Straight flush, 2. Four of a kind
    // 3. Full house, 4. Flush, 5. Straight, 6. Three of a kind, 7. Two pair
    // 8. One pair, 9.	High card
    private int handRank;
    // hand with the cards
    protected List<Card> cards;
    // handPoints
    private double handPoints;

    // ----------------------------------------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------------------------------------

    // default constructor
    public Player() {
        cards = new ArrayList<Card>();
        handRank = -1;
        handPoints = 0;
    }

    // ----------------------------------------------------------------------------------------
    // getters
    // ----------------------------------------------------------------------------------------
    public int getHandRank() {
        return handRank;
    }

    protected int getHandRankProtected() {
        return handRank;
    }

    public List<Card> getCards() {
        return cards;
    }

    public double getHandPoints() {
        return handPoints;
    }


    // ----------------------------------------------------------------------------------------
    // setters
    // ----------------------------------------------------------------------------------------

    // must receive cards sorted by sortByStrongCard first
    // cards cannot be sorted by sortByHandRank
    // each subfunction evaluates the current cards in the hand
    // and sets the hand rank 1-9
    public void setHandRank(Player player) {
        if (straightFlushOrFlush(player))
            return;
        else if (fourOfAKindOrFullHouseOrThreeOfAKind(player))
            return;
        else if (Straight(player))
            return;
        else if (TwoOrOnePairOrHighCard(player))
            return;
    }

    // function to receive the card
    public void receiveCard(Card card) {
        cards.add(card);
    }

    // display the cards in the hand
    public void displayTheHand() {

        // show info
        System.out.println("The cards in your hand are: ");

        // loop  through all cards and display
        for (int i = 0; i < cards.size(); ++i)
            System.out.print((i+1) + ") " + cards.get(i).getCardName() + "   ");

        // display hand rank
        System.out.print("      - " + displayHandType());

        // formatting
        System.out.println();
    }

    // sorts the cards in the hand from highest to lowest
    // also sorts the hands by the hand ranks, which displays
    // stronger combinations first
    public void sortByStrongCard() {
        Collections.sort(cards, new Comparator<Card>() {
            public int compare(Card c1, Card c2) {
                if (c1.getCardID() < c2.getCardID())
                    return 1;
                else if (c1.getCardID() > c2.getCardID())
                    return -1;
                else
                    return 0;
            }
        });
    }

    // function must receive the cards sorted by sortByStrongCard first
    // sorts by hand ranks,
    // Flush, Straight, High Card does not need to be sorted here
    // they will be in correct order after the sort by sortByStrongCard
    public void sortByHandRank() {
        if (handRank == 1) {
            sortStraightFlush();        // Straight flush
        }
        else if (handRank == 2) {   // Four of a kind
            sortFourOfTheKind();
        }
        else if (handRank == 3) {   // Full House
            sortFullHouse();
        }
        else if (handRank == 6) {   // Three Of A Kind
            sortThreeOfAKind();
        }
        else if (handRank == 7) {   // Two Pairs
            sortTwoPairs();
        }
        else if (handRank == 8) {   // One pair
            sortOnePair();
        }
    }

    // sorts the straight flush
    public void sortStraightFlush() {
        // if the fifth card is '2' special case of straight flush occured
        // so Ace is the lowest card
        if (cards.get(4).getCardID() == 2) {
            // move the Ace to the fifth spot by moving it spot by spot
            for (int i = 0; i < 4; ++i)
                Collections.swap(cards, i, i+1);
        }
    }

    // must receive hand sorted from the strongest card
    // adjusts the order of cards in hand so the four of the kind are listed first
    public void sortFourOfTheKind() {
        // first card different than second - not sorted properly
        if (cards.get(0).getCardID() != cards.get(1).getCardID()) {
            // swap the first card with the last one
            Collections.swap(cards, 0, 4);
        }
    }

    // must receive hand sorted from the strongest card
    // adjusts the order of cards in hand so three alike are listed first,
    // then two alike are listed
    public void sortFullHouse() {
        // two alike listed first, swap the cards
        if (cards.get(1).getCardID() != cards.get(2).getCardID()) {
            // swap first with last
            Collections.swap(cards, 0, 4);
            // swap second with fourth
            Collections.swap(cards, 1, 3);
        }
    }

    // must receive hand sorted from the strongest card
    // adjusts the order of cards such that Three Of A Kind are listed first
    public void sortThreeOfAKind() {
        // Three Of The Kind in the middle
        if (cards.get(1).getCardID() == cards.get(2).getCardID() &&
                cards.get(2).getCardID() == cards.get(3).getCardID()) {
            // swap first card with the fourth card
            Collections.swap(cards, 0, 3);
        }
        else if (cards.get(2).getCardID() == cards.get(3).getCardID() &&
                cards.get(3).getCardID() == cards.get(4).getCardID()) {
            // swap the first with last
            Collections.swap(cards, 0, 4);
            // swap second with fourth
            Collections.swap(cards, 1, 3);
        }
    }

    // must receive hand sorted from the strongest card
    // adjust the order of cards such that pairs are listed first
    public void sortTwoPairs() {
        // single card is first
        if (cards.get(1).getCardID() == cards.get(2).getCardID() &&
                cards.get(3).getCardID() == cards.get(4).getCardID()) {
            // swap first with third
            Collections.swap(cards, 0, 2);
            // swap third with fifth
            Collections.swap(cards, 2, 4);
        }
        // single card in the middle, between pairs
        if (cards.get(0).getCardID() == cards.get(1).getCardID() &&
                cards.get(3).getCardID() == cards.get(4).getCardID()) {
            // swap third with fifth
            Collections.swap(cards, 2, 4);
        }
    }

    // must receive hand sorted from the strongest card
    // adjust the order of cards such that pair is listed first
    public void sortOnePair() {
        // pair is a second and third card
        if (cards.get(1).getCardID() == cards.get(2).getCardID()) {
            // swap first with third
            Collections.swap(cards, 0, 2);
        }
        // pair is a third and fourth card
        else if (cards.get(2).getCardID() == cards.get(3).getCardID()) {
            // swap first with third
            Collections.swap(cards, 0, 2);
            // swap second with fourth
            Collections.swap(cards, 1, 3);
        }
        // pair is a fourth and fifth
        else if (cards.get(3).getCardID() == cards.get(4).getCardID()) {
            // swap third with fifth
            Collections.swap(cards, 2, 4);
            // swap first with third
            Collections.swap(cards, 0, 2);
            // swap second with fourth
            Collections.swap(cards, 1, 3);
        }
    }

    // exchanges the cards for player
    public void exchangeCards(List<Integer> cardsToExchangeIDs, CardPile deck) {

        // remove the cards which are to be exchanged
        // removing from the front will offset the indices of the card after removed card
        // thus, travel the IDs of cards to be removed backwards to keep the indices correct
        for (int i = cardsToExchangeIDs.size() - 1; i >= 0 ; --i) {
            int index = cardsToExchangeIDs.get(i);
            cards.remove(index);
        }

        // distribute new cards for the player from the deck
        for (int i = 0; i < cardsToExchangeIDs.size(); ++i) {
            // hand the card from the top of the deck to user and remove it from the deck
            cards.add(deck.getCards().get(0));
            deck.getCards().remove(0);
        }
    }


    // checks if the hand contains Straight flush or flush
    public static boolean straightFlushOrFlush(Player player) {
        // check if cards are all same suit
        boolean straightFlush = true;
        if (sameSuit(player)) {
            List<Card> tempCards = player.getCards();
            // check for straight flush
            for (int i = 0; i < 4; ++i) {
                if (tempCards.get(i).getCardID() != tempCards.get(i+1).getCardID()+1)
                    straightFlush = false;
            }
            // check for special case when Ace is a lowest card
            if (straightFlushSpecialCase(tempCards))
                straightFlush = true;

            if (straightFlush)       // set rank to 1. - straight flush
                player.handRank = 1;
            else                            // set rank to 4. - flush
                player.handRank = 4;

            return true;
        }
        // if 'if statement' was not executed, that means there is
        // no straight flush or flush in the hand, return false
        return false;
    }

    // return true if special case for Straight Flush occured
    // Ace as the lowest card
    public static boolean straightFlushSpecialCase(List <Card> hand) {
        // check if cards are Ace - 2 - 3 - 4 - 5
        if (hand.get(0).getCardID() == 14 &&
                hand.get(1).getCardID() == 5 &&
                hand.get(2).getCardID() == 4 &&
                hand.get(3).getCardID() == 3 &&
                hand.get(4).getCardID() == 2) {
            return true;        // special case found
        }
        else {
            return false;
        }
    }

    // checks if cards are same suit, return true if so,
    // false is returned otherwise
    public static boolean sameSuit(Player usr) {
        boolean same = true;
        // iterates through all cards, trigger the 'sameSuit' to false if
        // card of different color was found
        for (Card c  : usr.getCards()) {
            if (c.getCardSuit() != usr.getCards().get(0).getCardSuit())
                same = false;
        }
        return same;
    }

    // check for Four of the Kind, Full HOuse or Three of the kind
    public static boolean fourOfAKindOrFullHouseOrThreeOfAKind(Player player) {

        List<Card> cards = player.getCards();
        if (fourOfAKind(cards)) {
            player.handRank = 2;     // four of a kind found
            return true;
        }
        int tempHandRank = fullHouseOrThreeOfAKind(cards);
        // full house or three of the kind found
        if (tempHandRank != -1) {
            player.handRank = tempHandRank;
            return true;
        }

        return false;        // none of three found
    }

    // checks if there is 4 of a kind in the hand
    public static boolean fourOfAKind(List<Card> cards) {
        // check how many times rank of the first card occur
        char tempRank = cards.get(0).getCardRank();
        int count = 0;
        for (Card c : cards)
            if (c.getCardRank() == tempRank)
                count++;

        // 4 cards alike, return true
        if (count == 4)
            return true;

        // verify how many time the rank of the second card occurs
        tempRank = cards.get(1).getCardRank();
        count = 0;
        for (Card c : cards)
            if (c.getCardRank() == tempRank)
                count++;

        // four cards alike, return true
        if (count == 4)
            return true;
        else             // less tha 4 cards alike, return false
            return false;
    }

    // returns the hand rank number if found, 3 - full-house, 6 - three of a kind
    // -1 - default index
    public static int fullHouseOrThreeOfAKind(List <Card> cards) {
        char tempRank = 'X';
        int count = 0;
        // get the list of indices of the cards that ranks are alike
        // function returns empty list if there is no three cards with alike ranks
        List<Integer> sameSuit = collectThreeAlike(cards);
        if (!sameSuit.isEmpty()) {
            // check if two leftover cards ranks are alike
            // get the first free card rank
            for (Card c : cards) {
                if (!sameSuit.contains(cards.indexOf(c))) {
                    tempRank = c.getCardRank();
                }
            }

            // check if the other free card has the same rank,
            // if so hand is the full house
            for (Card c : cards) {
                if (!sameSuit.contains(cards.indexOf(c))) {
                    if (c.getCardRank() == tempRank)
                        count++;
                }
            }

            if (count == 2)      // full house found
                return 3;
            else                         // three of a kind found
                return 6;
        }

        // full house or three of a kind not found
        return -1;                   // default rank
    }

    // returns list of indices if three cards with the same ranks are found
    // checks for the three cards with the same ranks as first, second, or third card
    // first found is returned, otherwise empty list is returned
    public static List<Integer> collectThreeAlike(List<Card> cards) {

        int count = 0;
        List<Integer> sameRanksIndices = new ArrayList<Integer>();

        for (int i = 0; i < 3; ++i) {
            char tempRank = cards.get(i).getCardRank();
            // check if there is 3 cards of the same rank as the first card
            // add each card with the same rank to the 'sameRankIndices' list
            for (Card c : cards) {
                if (c.getCardRank() == tempRank) {
                    count++;
                    sameRanksIndices.add(cards.indexOf(c));
                }
            }

            // if the 'sameRankIndices' has size of 3, three alike ranks were
            // found, return the list
            if (sameRanksIndices.size() == 3)
                return sameRanksIndices;

            // three alike not found, loop will continue up to third card
            // empty the list, same indices might be there
            sameRanksIndices.clear();
        }

        // three of the same rank does not found, return empty list
        sameRanksIndices.clear();
        return sameRanksIndices;
    }

    // checks for the straight
    public static boolean  Straight(Player player) {
        boolean straight = true;
        List <Card> cards = player.getCards();

        // compare adjacent cards in hand and trigger 'straight' variable
        // to false if condition for straight is broken
        for (int i = 0; i < 4; ++i) {
            if(cards.get(i).getCardID() != cards.get(i+1).getCardID() + 1) {
                straight = false;
            }
        }

        // if found, assign the rank
        if (straight == true) {
            player.handRank = 5;
            return true;
        }
        // check for the special case with Ace as the low card
        else {
            if (straightSpecialCase(cards)) {
                player.handRank = 5;    // special case found
                return true;
            }
            else {
                return false;                   // not found
            }
        }
    }

    // checks for special case for straight
    public static boolean straightSpecialCase(List <Card> hand) {
        // Ace - 2 - 3 - 4 - 5
        if (hand.get(0).getCardID() == 14 &&
                hand.get(1).getCardID() == 5 &&
                hand.get(2).getCardID() == 4 &&
                hand.get(3).getCardID() == 3 &&
                hand.get(4).getCardID() == 2) {
            return true;        // found
        }
        else {
            return false;       // not found
        }
    }

    // checks for the Two or One Pair or High Card
    public static boolean TwoOrOnePairOrHighCard(Player player) {
        // local copy of the cards
        List<Card> cards = player.getCards();
        List<Integer> firstPairIndeces = new ArrayList<Integer>();
        List<Integer> secondPairIndeces = new ArrayList<Integer>();
        // find first pair
        for (int i = 0; i < 4; ++i) {
            if (cards.get(i).getCardID() == cards.get(i+1).getCardID()) {
                if (firstPairIndeces.isEmpty()) {
                    firstPairIndeces.add(i);
                    firstPairIndeces.add(i + 1);
                }
                // second pair
                else {
                    secondPairIndeces.add(i);
                    secondPairIndeces.add(i + 1);
                }
            }
        }

        if (!secondPairIndeces.isEmpty())
            player.handRank = 7;            // two pairs
        else if (!firstPairIndeces.isEmpty())
            player.handRank = 8;            // one pair
        else
            player.handRank = 9;            // high card

        return true;
    }

    // sets the points for each hand, easier to find the winner
    public void setTheHandPoints() {
        if (handRank == 1) // straight flush
            handPoints =  100000 * cards.get(0).getCardID();
        else if(handRank == 2)   // four of a kind
            handPoints = 30000 * cards.get(0).getCardID() ;
        else if (handRank == 3)  // full house
            handPoints = 4000 * cards.get(0).getCardID();
        else if (handRank == 4)  // flush
            handPoints = (500 * cards.get(0).getCardID());
        else if (handRank == 5) // straight
            handPoints =  70 * cards.get(0).getCardID();
        else if (handRank == 6)  // three of a kind
            handPoints = 8 * cards.get(0).getCardID();
        else if (handRank == 7) { // two pair
            handPoints = cards.get(0).getCardID() + (0.025 * cards.get(2).getCardID()) +
                    (0.1 * cards.get(4).getCardID());
        }
        else if (handRank == 8) {// one pair
            handPoints = (0.1 * cards.get(0).getCardID()) + (0.001 * cards.get(2).getCardID()) +
                    (0.0001 * cards.get(3).getCardID())  + (0.00002 * cards.get(4).getCardID()) ;
        }
        else { // high card
            handPoints = (0.01 * cards.get(0).getCardID()) + (0.001 * cards.get(1).getCardID()) +
                    (0.0001 * cards.get(2).getCardID()) + (0.00001 * cards.get(3).getCardID()) +
                    (0.000001 * cards.get(4).getCardID());
        }
    }

    // Displays the info about hand type
    public String displayHandType() {
        if (handRank == 1)
            return "Straight Flush";
        else if (handRank == 2)
            return "Four Of A Kind";
        else if (handRank == 3)
            return "Full House";
        else if (handRank == 4)
            return "Flush";
        else if (handRank == 5)
            return "Straight";
        else if (handRank == 6)
            return "Three Of A Kind";
        else if (handRank == 7)
            return "Two Pair";
        else if (handRank == 8)
            return "One Pair";
        else
            return "High Card";
    }


}   // end of Player class
