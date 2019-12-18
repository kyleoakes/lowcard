package lowcard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

public class CardTable extends JFrame
{

   private static int MAX_CARDS_PER_HAND = 56;
   private static int MAX_PLAYERS = 2;
   private static GUICard guiCard = new GUICard();
   private static CardGameFramework cardGameFramework;
   private static Card[] winningsComputer = new Card[MAX_CARDS_PER_HAND];  // Added By Nick
   private static Card[] winningsPlayer = new Card[MAX_CARDS_PER_HAND];   // Added By Nick
   private static int winCompCounter = 0;     // Added By Nick
   private static int winPlayerCounter = 0;   // Added By Nick
   private static final int PLAYERPLAYAREALABEL = 0;
   private static final int COMPUTERPLAYAREALABEL = 1;
   private static final int SHORTDELAY = 500;
   private static final int MEDDELAY = 800;
   private static final int LONGDELAY = 1500;
   private static final int PLAYER = 1;
   private static final int COMPUTER = 0;
   static int counter;
   private int numCardsPerHand;
   private int numPlayers;
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
   private JLabel turnLabel;
   private int turn;
   private int firstPlayer;
   private int secondPlayer;

   private Card lastCardPlayedByHuman;
   private Card lastCardPlayedByComputer;

   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      setTitle(title);
      setLayout(new BorderLayout());
      this.numCardsPerHand = numCardsPerHand;
      this.numPlayers = numPlayers;

      JPanel pnlTextPanel = new JPanel();
      pnlTextPanel.setPreferredSize(new Dimension(220,295));
      turnLabel = new JLabel("<html> </html>");
      setTurnLabel(" ");
      pnlTextPanel.add(turnLabel);
      add(pnlTextPanel,BorderLayout.WEST);

      cardGameFramework = new CardGameFramework(1,2,0,null,numPlayers,numCardsPerHand);
      cardGameFramework.deal(); //initially deal cards
      System.out.println("COMPUTER" + cardGameFramework.getHand(COMPUTER));
      System.out.println("PLAYER" + cardGameFramework.getHand(PLAYER));

      //initialize the computerPlayer part of the GUI
      pnlComputerHand = new JPanel();
      pnlComputerHand.setLayout(new GridLayout(1,numCardsPerHand));
      initializeHandGUI(pnlComputerHand,COMPUTER);
      add(pnlComputerHand,BorderLayout.NORTH);
      pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
      pnlComputerHand.setBackground(new Color(39, 119, 20));
   
      //initialize the playArea part of the GUI
      pnlPlayArea = new JPanel(new GridLayout(2,2));
      // The spot where human plays card
      JLabel lblPlayerCard = new JLabel(GUICard.getBackCardIcon());
      pnlPlayArea.add(lblPlayerCard);
      // The spot where computer plays card
      JLabel lblComputerCard = new JLabel(GUICard.getBackCardIcon());
      pnlPlayArea.add(lblComputerCard);
      // Labels for the above spots
      pnlPlayArea.add(new JLabel("Player",JLabel.CENTER));
      pnlPlayArea.add(new JLabel("Computer",JLabel.CENTER));
      add(pnlPlayArea,BorderLayout.CENTER);
      pnlPlayArea.setBorder(new TitledBorder("Playing Area"));
      pnlPlayArea.setBackground(new Color(39, 119, 20));

      //initialize the Player part of the GUI
      pnlHumanHand = new JPanel(new GridLayout(1,numCardsPerHand));
      initializeHandGUI(pnlHumanHand,PLAYER);
      add(pnlHumanHand,BorderLayout.SOUTH);
      pnlHumanHand.setBorder(new TitledBorder("Your Hand"));
      pnlHumanHand.setBackground(new Color(39, 119, 20));

      //setVisible initially so that the player has to choose heads or tails
      setVisible(false);

      //will contain the play logic of the game.
       headsOrTailsWindow();
   }

   // Added by Nick - to add cards to winnings arrays.
   private void addToWinningsArray()
   {
      int winner = getWinnerOfRound(lastCardPlayedByHuman, lastCardPlayedByComputer, false);
      if(winner == 0 && winningsComputer != null 
            && winCompCounter < MAX_CARDS_PER_HAND)
      {
         winningsComputer[winCompCounter++] = lastCardPlayedByHuman;
         winningsComputer[winCompCounter++] = lastCardPlayedByComputer;
      }
      else if(winner == 1 && winningsPlayer != null 
            && winPlayerCounter < MAX_CARDS_PER_HAND)
      {
         winningsPlayer[winPlayerCounter++] = lastCardPlayedByHuman;
         winningsPlayer[winPlayerCounter++] = lastCardPlayedByComputer;
      }
   }

   
   private void headsOrTailsChosen(boolean bool,boolean choice, JFrame win)
   {
      if (bool == choice)
         setFirstPlayer(PLAYER);
      else
         setFirstPlayer(COMPUTER);
      win.dispose();
      setVisible(true);
   }

   private void headsOrTailsWindow()
   {
      JFrame win = new JFrame("HEADS OR TAILS");
      win.setLayout(new FlowLayout());
      win.setVisible(true);
      win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      win.setLocationRelativeTo(null);
      win.setAlwaysOnTop(true);
      win.setSize(new Dimension(300,100));
      JLabel text = new JLabel("Pick heads or tails to see who goes first!",JLabel.CENTER);
      win.add(text);

      JButton heads = new JButton("Heads");
      boolean b = new Random().nextBoolean();
      //////////////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!////////////////
      System.out.println(b);
      heads.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            headsOrTailsChosen(b,true,win);
         }
      });
      JButton tails = new JButton("Tails");
      tails.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            headsOrTailsChosen(b,false,win);
         }
      });
      win.add(heads);
      win.add(tails);
   }

   private void initializeHandGUI(JPanel panel, int player)
   {
      Hand hand =  cardGameFramework.getHand(player);
      JButton [] arr = new JButton[numCardsPerHand];

      for (int i = 0; i < arr.length; i++)
      {
         if (player == COMPUTER)
         {
            JButton button = new JButton();
            button.setIcon(GUICard.getBackCardIcon());
            button.setDisabledIcon(GUICard.getBackCardIcon());
//             JButton button = new JButton(GUICard.getIcon(hand.inspectCard(i)));
//             button.setDisabledIcon(GUICard.getIcon(hand.inspectCard(i)));
            button.setEnabled(false);
button.setContentAreaFilled(false);
            arr[i] = button;
         }

         else
         {
            JButton button = new JButton(GUICard.getIcon(hand.inspectCard(i)));
            button.setDisabledIcon(GUICard.getIcon(hand.inspectCard(i)));
            button.setActionCommand("" + i);
            button.setEnabled(false);
button.setContentAreaFilled(false);
            arr[i] = button;
            arr[i].addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent actionEvent)
               {
                  int index = Integer.parseInt(button.getActionCommand());
                  playCard(index, PLAYER, pnlHumanHand);
                  
                  if (getFirstPlayer() == PLAYER)
                  {
                     setTimer(SHORTDELAY);
                  }
                  else
                  {
                     setTimer(SHORTDELAY);
                  }
               }

            });

         }
         panel.add(arr[i]);
      }
   }


   public int nextRound(int loser)
   {
      setFirstPlayer(loser);
   
      return getFirstPlayer();
   }
   
   // Added by Kyle to decide when it's time to count up the scores
   private boolean checkHandsEmpty()
   {
      if (
            (cardGameFramework.getHand(PLAYER).getNumCards() == 0) &&
            (cardGameFramework.getHand(COMPUTER).getNumCards() == 0)
         )
      {
         System.out.println("checkHandsEmpty(): All players are out of cards!");
         setTurnLabel("All players are out of cards!");
         // Calc score and display it
         int playerScore = 0;
         int compScore = 0;
         HashMap<Character, Integer> valueMap = generateValueMap();
         for (int i = 0; i < winningsPlayer.length; i++)
         {
            playerScore += valueMap.get(winningsPlayer[i].getValue());
         }
         for (int i = 0; i < winningsComputer.length; i++)
         {
            compScore += valueMap.get(winningsComputer[i].getValue());
         }
         setTurnLabel("Player Score: " + playerScore);
         setTurnLabel("Computer Score: " + compScore);
         setTurnLabel("" + ((playerScore > compScore)?"Player won the game!":"Computer won the game!"));
         return true;
      }
      else
      {
         return false;
      }
   }

   private void nextTurn()
   {
      ///// EDITED BY KYLE TO STOP GAME WHEN ALL PLAYERS' HANDS ARE EMPTY /////
      if (!checkHandsEmpty())
      {
         turn++;
         if (turn > 1)
         {
            if (secondPlayer == PLAYER)
               setResetTimer(MEDDELAY);
            else
               setResetTimer(LONGDELAY);
         }
         else
         {
            if (secondPlayer == PLAYER && turn == 1)
            {
                setPlayerCardsPlayable();
            }
            else
            {
               setPlayerCardsUnplayable();
               setComputerPlayTimer(MEDDELAY);
           }
         }
      }
   }

   private void playCard(int index,int player, JPanel panel)
   {
      Hand hand = cardGameFramework.getHand(player);
      ((JLabel)pnlPlayArea.getComponent(player == PLAYER ? 0:1)).setIcon(GUICard.getIcon(hand.inspectCard(index)));
      panel.getComponent(index).setVisible(false);
      if (player == PLAYER)
      {
         lastCardPlayedByHuman = cardGameFramework.playCard(player,index);
         setPlayerCardsUnplayable();
         setTurnLabel("Player played " + lastCardPlayedByHuman);
      }
      else
      {
         lastCardPlayedByComputer = cardGameFramework.playCard(player,index);
         setTurnLabel("Computer played " + lastCardPlayedByComputer);
      }
      // If there are cards left, draw a card. Otherwise, remove a button.
      if (cardGameFramework.getNumCardsRemainingInDeck() > 0)
      {
         cardGameFramework.takeCard(player);
      }
      else
      {
         removeButtonFromHandGUI(player);
      }
      updateHand(player);
      System.out.println(
            player == COMPUTER 
            ? "Computer played " + lastCardPlayedByComputer 
            : "Human " + lastCardPlayedByHuman
      );
      
      System.out.println("COMPUTER Hand " + cardGameFramework.getHand(COMPUTER));
      System.out.println("PLAYER Hand " + cardGameFramework.getHand(PLAYER));
      
   }

   private void playComputerCard()
   {
      int index = selectComputerCardToPlay();
      playCard(index,COMPUTER,pnlComputerHand);
      nextTurn();
   }

   private boolean removeButtonFromHandGUI(int player)
   {
      if (player == PLAYER)
      {
         //remove last card-button in player hand
         pnlHumanHand.remove(pnlHumanHand.getComponentCount() - 1);
         return true;
      } else if (player == COMPUTER)
      {
         //remove last card-button in computer hand
         pnlComputerHand.remove(pnlComputerHand.getComponentCount() - 1);
         return true;
      } else
      {
         return false;
      }
   }

   private void resetPlayArea()
   {
      /////////////////////////////////////////////////////////////////////
      ////////////////////   NICK ADDED TO TEST  //////////////////////////
      //////////////////////////////////////////////////////////////////////
      addToWinningsArray();
      for(int i = 0; i < MAX_CARDS_PER_HAND; i++)
      {
         if (winningsComputer[i] != null)
            System.out.println("***Comp Win Array*** " + winningsComputer[i]);
      }
      
      for(int i = 0; i < MAX_CARDS_PER_HAND; i++)
      {
         if (winningsPlayer[i] != null)
            System.out.println("***Player Win Array*** " + winningsPlayer[i]);
      }
      ////////////////////////////////////////////////////////////////////////
      ///////////////////////  ENDS HERE /////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////
      
      //only reset the card values, we do not care about the string labels
      for (int i = 0; i < 2; i++)
      {
         ((JLabel)pnlPlayArea.getComponent(i)).setIcon(GUICard.getBackCardIcon());
      }
      System.out.println("resetPlayArea(): Reset play area.");
   }
   
   // Convert card value from char to int for comparison (not file indexing)
   private HashMap<Character, Integer> generateValueMap()
   {
      // Set up a map used to convert Card values from char to int to make them
      // easier to compare.
      HashMap<Character,Integer> valueMap = new HashMap<Character, Integer>();
      valueMap.put('A', 0);valueMap.put('2', 1);valueMap.put('3',2);
      valueMap.put('4',3);valueMap.put('5', 4);valueMap.put('6',5);
      valueMap.put('7', 6);valueMap.put('8',7);valueMap.put('9',8);
      valueMap.put('T',9);valueMap.put('J',10);valueMap.put('Q',11);
      valueMap.put('K',12);valueMap.put('X',13);
      
      return valueMap;
   }
   
   private int selectComputerCardToPlay()
   {
      // the index of the card the computer selects. Will be returned.
      int selectedCardIndex = 0;
      // Determine if the computer is going first
      boolean computerGoingFirst = (getFirstPlayer() == COMPUTER);
   
      HashMap<Character, Integer> valueMap = generateValueMap();

      int humanCardValue;
   
      // Set up array for values of cards in computer hand
      int[] handValues = new int[pnlComputerHand.getComponentCount()];
      // Look through computer hand, saving card values as ints for comparison
      for (int i = 0; i < cardGameFramework.getHand(COMPUTER).getNumCards(); i++)
      {
         handValues[i] = valueMap.get(
                 cardGameFramework.getHand(COMPUTER).inspectCard(i).getValue()
         );
      }
      int lowestCardValue = handValues[0]; //begin with first value
      int lowestCardIndex = 0;
      int highestCardValue = handValues[0]; //begin with first value
      int highestCardIndex = 0;
      int highestCardThatCanWinValue = -1; //begin with low value
      //Assume we can't win until we determine that we can. -1 indicates that
      // we can't win. It will be set to another value if we find we can win.
      int highestCardThatCanWinIndex = -1;
      // Begin comparing cards in hand to identify the highest, lowest, and
      // highest that can beat the opponent's play (if any)
      for (int i = 0; i < handValues.length; i++)
      {
         // Check if current card is computer's lowest or highest card in hand
         // and if so, remember it
         if (handValues[i] < lowestCardValue)
         {
            lowestCardValue = handValues[i];
            lowestCardIndex = i;
         }
         if (handValues[i] > highestCardValue)
         {
            highestCardValue = handValues[i];
            highestCardIndex = i;
         }
         // If computer is going second, check if it could win with current card
         // and if so, remember it
         if (!computerGoingFirst)
         {
            humanCardValue = valueMap.get(lastCardPlayedByHuman.getValue());
            if ( handValues[i] < humanCardValue && handValues[i] > highestCardThatCanWinValue)
            {
               highestCardThatCanWinValue = handValues[i];
               highestCardThatCanWinIndex = i;
            }
         }
      }
   
      // Decide whether to play lowest, highest, or highest winning card
      if (computerGoingFirst)
      {
         System.out.println(
               "selectComputerCardToPlay(): Computer is playing first and " +
               "will play the lowest card it has."
         );
         selectedCardIndex = lowestCardIndex;
      }
      else
      {
         //return highest card that can win. If can't win, return highest card.
         if (highestCardThatCanWinIndex > -1) // if we found a card that can win
         {
            System.out.println(
                  "selectComputerCardToPlay(): Computer will play the " +
                  "highest card it can win with."
            );
            selectedCardIndex = highestCardThatCanWinIndex;
         }
         else // if we can't win this round...
         {
            System.out.println(
                  "selectComputerCardToPlay(): Computer determined it can't " +
                  "win this round. Selecting highest card."
            );
            selectedCardIndex = highestCardIndex; // ...don't waste a low card
         }
      }
      System.out.println(
            "selectComputerCardToPlay(): Computer selected card " +
            cardGameFramework.getHand(COMPUTER).inspectCard(selectedCardIndex) +
            " at hand index " + selectedCardIndex + "."
      );
      return selectedCardIndex;
   }

   public void updateHand(int player)
   {
      if (player == PLAYER)
      {
         for (int i = 0; i < pnlHumanHand.getComponentCount(); i++)
         {
            JButton button = (JButton)pnlHumanHand.getComponent(i);
            button.setIcon(GUICard.getIcon(cardGameFramework.getHand(PLAYER).inspectCard(i)));
            button.setDisabledIcon(GUICard.getIcon(cardGameFramework.getHand(PLAYER).inspectCard(i)));
            button.setActionCommand("" + i);
            button.setVisible(true);
         }
         return;
      }
   
      for (int i = 0; i < pnlComputerHand.getComponentCount(); i++)
      {
   
         JButton button = (JButton)pnlComputerHand.getComponent(i);
         //button.setIcon(GUICard.getIcon(cardGameFramework.getHand(COMPUTER).inspectCard(i)));
         //button.setDisabledIcon(GUICard.getIcon(cardGameFramework.getHand(COMPUTER).inspectCard(i)));
         button.setIcon(GUICard.getBackCardIcon());
         button.setDisabledIcon(GUICard.getBackCardIcon());
         button.setVisible(true);
      }
   
   
   }

   public int getFirstPlayer()
   {
      return firstPlayer;
   }

   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   public int getNumPlayers()
   {
      return numPlayers;
   }

   private int getWinnerOfRound(Card humanCard, Card computerCard, boolean updateLabel)
   {
      String comWinString = "computer won this round!";
      String playerWinString = " player won this round!";
      
      HashMap<Character, Integer> valueMap = generateValueMap();
      
      if (valueMap.get(humanCard.getValue()) > valueMap.get(computerCard.getValue()))
      {
         if (updateLabel)
         {
            setTurnLabel(comWinString);
         }
         return COMPUTER;
      }
      else if(valueMap.get(humanCard.getValue()) == valueMap.get(computerCard.getValue()))
      {
         int player = humanCard.getSuit().ordinal() < computerCard.getSuit().ordinal() ? PLAYER : COMPUTER;
         if (updateLabel)
         {
            setTurnLabel(player == PLAYER ? playerWinString : comWinString);
         }
         return player;
      }
      else
      {
         if (updateLabel)
         {
            setTurnLabel(playerWinString);
         }
         return PLAYER;
      }
   }

   private void setComputerPlayTimer(int time)
   {
      setTurnLabel("Computer is thinking...");
      Timer timer = new Timer(time, new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            playComputerCard();
         }
      });
      timer.setRepeats(false);
      timer.start();
   }

   private void setFirstPlayer(int n)
   {
      turn = 0;
      if (n == 1)
      {
         firstPlayer = PLAYER;
         secondPlayer = COMPUTER;
         setPlayerCardsPlayable();
         setTurnLabel("Player goes first!");
         return;
      }
      firstPlayer = COMPUTER;
      secondPlayer = PLAYER;
   
      setTurnLabel("Computer goes first!");
      setComputerPlayTimer(MEDDELAY);
   }

   private void setPlayerCardsPlayable()
   {
       for (int i = 0; i < pnlHumanHand.getComponentCount(); i++)
           pnlHumanHand.getComponent(i).setEnabled(true);
   }

   private void setPlayerCardsUnplayable()
   {
       for (int i = 0; i < pnlHumanHand.getComponentCount(); i++)
           pnlHumanHand.getComponent(i).setEnabled(false);
   }

   private void setResetTimer(int time)
   {
      Timer timer = new Timer(time, new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            resetPlayArea();
            setFirstPlayer(getWinnerOfRound(lastCardPlayedByHuman, lastCardPlayedByComputer, true));
         }
      });
      timer.setRepeats(false);
      timer.start();
   }

   private void setTimer(int time)
   {
      Timer timer = new Timer(time, new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent actionEvent)
         {
            nextTurn();
         }
      });
      timer.setRepeats(false);
      timer.start();
   }

  private void setTurnLabel(String addedString)
   {
      String start = "<html>";
      String end = "</html>";
      counter++;
      String s;
      if (counter > 18)
      {
         turnLabel.setText(start +  addedString + end);
         counter = 0;
         return;
      }
         s = turnLabel.getText().substring(6,turnLabel.getText().length()-7);

      turnLabel.setText(start+  s +"<br>" +  addedString + end);
   }

   public static void main(String[] args)
   {
      int nppd = 1;
      int njpd = 2;
      int nucpp = 0;
      Card [] ucpp = null;
      CardTable myCardTable = new CardTable("IhasCards:3",5,2);
      myCardTable.setSize(new Dimension(800,600));
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}

//
//.__(.)< (MEOW)
// \___)
//~~~~~~~~~~~~~~~~~~
