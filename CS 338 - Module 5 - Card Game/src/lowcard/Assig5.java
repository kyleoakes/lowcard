//package lowcard;
//
//import javax.swing.*;
//
//import java.io.File;
//
///**
// * The Assig5 program emulates a "Low Card" card game. For Cal State Monterey
// * Bay course CST338:Software Design.
// * 
// * @author Nate Carrasco
// * @author Nick D'Orazio
// * @author Kyle Oakes
// * @author Christal O'Connell
// * @version 1.0
// * @since 2019-11-20
// */
//public class Assig5
//{
//   static int NUM_CARDS_PER_HAND = 7;
//   static int NUM_PLAYERS = 2;
//
//   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card
//                                          // image
//   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
//   private final static String IMGLOCATION = "images";
//
//   static void loadCardIcons()
//   {
//      File folderFile = new File(IMGLOCATION);
//
//      File[] listFiles = folderFile.listFiles();
//      for (int i = 0; i < listFiles.length; i++)
//      {
//         String location = listFiles[i].getAbsolutePath();
//         if (listFiles[i].isFile())
//         {
//            icon[i] = new ImageIcon(location);
//         }
//      }
//   }
//
//   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
//   static String turnIntIntoCardValue(int k)
//   {
//      String[] arr =
//      { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
//
//      if (k >= 0 && k < arr.length)
//      {
//         return arr[k];
//      }
//      return "invalid value";
//   }
//
//   // turns 0 - 3 into "C", "D", "H", "S"
//   static String turnIntIntoCardSuit(int j)
//   {
//      String[] arr =
//      { "C", "D", "H", "S" };
//      if (j >= 0 && j < arr.length)
//      {
//         return arr[j];
//      }
//      return "invalid value";
//   }
//
//   // a simple main to throw all the JLabels out there for the world to see
//   public static void main(String[] args)
//   {
//      int k;
//      Icon tempIcon;
//
//      // prepare the image icon array
//      //loadCardIcons();
//
//      // establish main frame in which program will run
//      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND,
//            NUM_PLAYERS);
//      myCardTable.setSize(800, 600);
//      myCardTable.setLocationRelativeTo(null);
//      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//      // prepare the image label array
//      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
//      for (k = 0; k < NUM_CARD_IMAGES; k++)
//      {
//         labels[k] = new JLabel(icon[k]);
//      }
//      
//      /*
//      // place your 3 controls into frame
//      for (k = 0; k < NUM_CARD_IMAGES; k++)
//         myCardTable.add(labels[k]);
//         */
//
//      // show everything to the user
//      myCardTable.setVisible(true);
//   }
//
//   // returns a new random card for the main to use in its tests.
//   static Card randomCardGenerator()
//   {
//      return null;
//   }
//}
//
