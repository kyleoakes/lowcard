package lowcard;

public class Hand
{
  public static final int MAX_CARDS = 100;
 
  private Card [] myCards;
  private int numCards;  
  public Hand()
  {
     myCards = new Card[MAX_CARDS];
     numCards = 0;
  }
 
  public void resetHand()
  {
     numCards = 0;
  }
 
  public boolean takeCard(Card card)
  {
     if (numCards > MAX_CARDS -1 || card == null)
     {
        return false;
     }
     myCards[numCards++] = new Card(card.value,card.getSuit());
     return true;
  }

  public void sort()
  {
      Card.arraySort(myCards,myCards.length);
  }

  public Card playCard()
  {
     if (numCards >= 0)
     {
        numCards--;
        if (numCards == 0 && myCards[numCards] != null)
        {
           Card card = myCards[numCards];
           myCards[numCards] = null;
          
           return card;
        }
        else if(myCards[numCards] == null)
        {
           return new Card('0',Card.Suit.DIAMONDS);
        }
        return myCards[numCards];
     }
     return new Card('0',Card.Suit.DIAMONDS);
    
  }

   public Card playCard(int cardIndex)
   {
       if ( numCards == 0 ) //error
       {
           //Creates a card that does not work
           return new Card('M', Card.Suit.SPADES);
       }
       //Decreases numCards.
       Card card = myCards[cardIndex];

       numCards--;
       for(int i = cardIndex; i < numCards; i++)
       {
           myCards[i] = myCards[i+1];
       }

       myCards[numCards] = null;

       return card;
   }

 
  public int getNumCards()
  {
     return numCards;
  }
 
  public Card inspectCard(int k)
  {
     if (0 <= k &&
           k <= numCards &&
           myCards[k] != null &&
           myCards[k].getErrorFlag() == false)
     {
        Card card = myCards[k];
        return new Card(card.value,card.getSuit());
    
     }
     return new Card('0',Card.Suit.CLUBS);
    
  }
 
  public String toString()
  {
     StringBuilder sBuilder = new StringBuilder();
     sBuilder.append("Hand: ");
     sBuilder.append("\n");
     if (numCards == 0)
     {
        sBuilder.append("Draw some cards then we'll talk");
        return sBuilder.toString();
     }
     for (int i = 0; i < numCards; i++)
     {
        if (myCards[i] != null)
        {
           if (i != numCards -1)
           {
              if((i +1) % 10 == 0 && i != 0){
                 sBuilder.append(myCards[i].toString());
                 sBuilder.append("\n");
              }
              else {
                 {
                    sBuilder.append(myCards[i].toString());
                    sBuilder.append(", ");
                 }
              }

           }
           else {
              sBuilder.append(myCards[i].toString());
           }
        }
     }
     return sBuilder.toString();
  }
 
  public static void main(String [] args)
  {
     Card card1 = new Card();
     Card card2 = new Card('2',Card.Suit.CLUBS);
     Card card3 = new Card('3',Card.Suit.DIAMONDS);
     Card card4 = new Card('4',Card.Suit.HEARTS);
     Card card5 = new Card('5',Card.Suit.HEARTS);
     Hand hand = new Hand();
     boolean bool = true;
    
     hand.takeCard(new Card('t',Card.Suit.HEARTS));
     for (int i = 0; i < 1000; i++)
     {
        hand.takeCard(card1);
        hand.takeCard(card2);
        hand.takeCard(card3);
        hand.takeCard(card4);
        hand.takeCard(card5);
       
     }
    
     System.out.println(hand.toString());
     int val = hand.getNumCards();
     for (int i = 0; i < val; i++)
     {
        System.out.println("Playing " +  hand.playCard());
     }
     System.out.println(hand.toString());
  }

}


