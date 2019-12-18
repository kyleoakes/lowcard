package lowcard;

import java.util.Scanner;

public class Deck
{
   private static final int NUMBER_OF_CARDS = 56;//includes 4 jokers
   public static final int MAX_CARDS = 6*NUMBER_OF_CARDS;
   private static Card [] masterPack;
   private Card [] cards;
   private int topCard;
   static
   {
      allocateMasterPack();
   }
   
   public Deck()
   {
      this(1);
   }
   
   public Deck(int numPacks)
   {
      allocateMasterPack();
      init(numPacks);
   }
   
   public void init(int numPacks)
   {
      if (numPacks > 6 || numPacks <= 0)
      {
         for (int i = 0; i < masterPack.length; i++)
         {
            Card card = masterPack[i];
            cards[i] = new Card(card.getValue(),card.getSuit());
         }
      }
      else
      {
         cards = new Card[numPacks * NUMBER_OF_CARDS];
         for (int i = 0; i < numPacks; i++)
         {
            for (int j = 0; j < masterPack.length; j++)
            {
               Card card = masterPack[j];
               cards[j + i*masterPack.length] = new Card(card.getValue(),card.getSuit());
            }
         }

      }
      topCard = cards.length-1;
   }

   public boolean addCard(Card card)
   {
      for (int i = 0; i < cards.length; i++)
      {
         if (card.equals(cards[i]))
            return false;
      }
      cards[++topCard] = card;
      return true;
   }

   public boolean removeCard(Card card)
   {
      for (int i = 0; i < cards.length; i++)
      {
         if (card.equals(cards[i]))
         {
            Card removedCard = cards[i];
            cards[i] = cards[topCard];
            cards[topCard] = new Card('M', Card.Suit.SPADES);
            topCard--;
            return true;
         }
      }
      return false;
   }

   public void sort()
   {
      Card.arraySort(cards,cards.length);
   }

   public int getNumCards()
   {
      return topCard +1;
   }
   
   public Card dealCard()
   {
      if (topCard < 0)
      {
         return new Card('0',Card.Suit.CLUBS);
      }
      Card card = cards[topCard--];
      return new Card(card.getValue(),card.getSuit());
      
   }

   public static Card randomCardGenerator()
   {
      int location = (int)(Math.random()*masterPack.length-1);
      Card card = new Card(masterPack[location].getValue(), masterPack[location].getSuit());
      return new Card(card.getValue(),card.getSuit());
   }
  
   private static void allocateMasterPack()
   {
      if (masterPack == null)
      {
         char [] c = {'a','2','3','4','5','6','7','8','9','t','j','q','k','x'};
         masterPack = new Card[56];
         int counter = 0;
         for (int i = 0; i < Card.Suit.values().length; i++)
         {
            Card.Suit s = Card.Suit.values()[i];
            for (int j = 0; j < c.length; j++)
            {
               masterPack[counter++] = new Card(c[j],s);
            }
         }
      }
   }
   
   public void shuffle()
   {
//      int min = 0;
//      int max = cards.length -1;
//      int range = max - min +1;
      for (int i = 0; i < 700; i++)
      {
         int cardLoc1 = (int)(Math.random()*cards.length);
         Card card1 = cards[cardLoc1];
         
         int cardLoc2 = (int)(Math.random() * cards.length);
         Card card2 = cards[cardLoc2];
         
         //swap
         cards[cardLoc1] = card2;
         cards[cardLoc2] = card1;
      }
      
   }
   
   public Card inspectCard(int k)
   {
      if(k < 0 || k > cards.length -1 || k > topCard)
      {
         return new Card('0',Card.Suit.CLUBS);
      }
      return new Card(cards[k].value,cards[k].getSuit());
   }
   
   public int getTopCard()
   {
      return topCard;
   }
   
   public String toString()
   {
      StringBuilder sBuilder = new StringBuilder();
      for (int i = 0; i < cards.length; i++)
      {
         if ((i+1) % 13 == 0)
         {
            sBuilder.append(cards[i]);
            sBuilder.append("\n");
         }
         else 
         {
            sBuilder.append(cards[i]);
         }
      }
      return sBuilder.toString();
   }
   
   public static void main(String [] args)
   {
      Deck deck = new Deck();
      int players = 0;
      
      //get user input
      System.out.println("How many playing? (Max of 10 players):");
      try(Scanner scanner = new Scanner(System.in))
      {
         if (scanner.hasNextInt())
         {
            players = scanner.nextInt();
         }
      }
      Hand [] playerArr = new Hand[players];
      int val = deck.getTopCard();
      for (int i = 0; i < playerArr.length; i++)
      {
         playerArr[i] = new Hand();
      }
      for (int i = 0; i < val; i++)
      {
         playerArr[i%players].takeCard(deck.dealCard());
      }
      
      System.out.println("Your hands, from an unshuffled deck");
      for (int i = 0; i < playerArr.length; i++)
      {
         System.out.println(playerArr[i].toString());
      }
      
      deck.init(1);
      deck.shuffle();
      
      val = deck.getTopCard();
      for (int i = 0; i < playerArr.length; i++)
      {
         playerArr[i] = new Hand();
      }
      
      for (int i = 0; i < val; i++)
      {
         playerArr[i%players].takeCard(deck.dealCard());
      }
      
      System.out.println("\nYour hands, from a shuffled deck");
      for (int i = 0; i < playerArr.length; i++)
      {
         System.out.println(playerArr[i].toString());
      }
      
   }
}

