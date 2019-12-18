package lowcard;



import java.awt.*;
import java.io.File;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GUICard
{
    private static Icon [] [] iconCards = new ImageIcon[14][4];
    static Icon[] icon = new ImageIcon[57];
    private static Icon iconBack;
    private static HashMap<Character,Integer> map = new HashMap<Character, Integer>();

    static
    {
        map.put('A', 8);map.put('2', 0);map.put('3',1);
        map.put('4',2);map.put('5', 3);map.put('6',4);
        map.put('7', 5);map.put('8',6);map.put('9',7);
        map.put('T',12);map.put('J',9);map.put('Q',11);
        map.put('K',10);map.put('X',13);
    }
    static boolean iconsLoaded = false;

    public GUICard()
    {
        loadCardIcons();
    }


    static void loadCardIcons()
    {
        if (!iconsLoaded)
        {
            iconsLoaded = true;
            File folderFile = new File("images");

            File [] listFiles = folderFile.listFiles();

            sortFiles(listFiles);

            int saveIndex = 0;
            boolean skipped = false;
            for (int i = 0; i < listFiles.length; i++)
            {
                if (listFiles[i].isFile())
                {
                    if (listFiles[i].getName().contains("BK"))
                    {
                        skipped = true;
                        saveIndex = i;
                        i++;
                    }
                    if (!skipped)
                    {
                        icon[i] =  new ImageIcon(listFiles[i].getAbsolutePath());
                    }
                    else
                    {
                        icon[i-1] =  new ImageIcon(listFiles[i].getAbsolutePath());
                    }
                }
            }
            icon[icon.length-1] = new ImageIcon(listFiles[saveIndex].getAbsolutePath());

            for (int i = 0; i < icon.length-1; i++)
            {
                iconCards[i/4][i%4] = icon[i];
            }
            iconBack = icon[icon.length-1];
        }
    }

    public static Icon getIcon(Card card)
    {
       int value = valueAsInt(card);
       if (value > -1) // if valid card
       {
          return iconCards[value][suitAsInt(card)];
       }
       else
       {
          return getBackCardIcon();
       }
    }

    private static int valueAsInt(Card card)
    {
        if (map.containsKey(card.getValue()))
        {
            return map.get(card.getValue());
        }

        return -1;
    }

    private static int suitAsInt(Card card)
    {
        return card.getSuit().ordinal();
    }

    public static Icon getBackCardIcon()
    {
        return iconBack;
    }

    private static void sortFiles(File [] arr)
    {
        for (int i = 0; i < arr.length-1; i++)
        {
            int minIndex = i;
            for (int j = i+1; j < arr.length; j++)
            {
                if (arr[j].getName().compareTo(arr[minIndex].getName()) < 0)
                {
                    minIndex = j;
                }
            }
            File temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }

    }

    public static void main(String [] args)
    {
        int k;

        // prepare the image icon array
        loadCardIcons();

        // establish main frame in which program will run
        JFrame frmMyWindow = new JFrame("Card Room");
        frmMyWindow.setSize(1150, 650);
        frmMyWindow.setLocationRelativeTo(null);
        frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up layout which will control placement of buttons, etc.
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
        frmMyWindow.setLayout(layout);

        JLabel jLabel;
        for (int i = 0; i < iconCards.length; i++)
        {
            for (int j = 0; j < iconCards[i].length; j++)
            {
                jLabel = new JLabel(iconCards[i][j]);
                frmMyWindow.add(jLabel);
            }
        }
//        // prepare the image label array
//        JLabel[] labels = new JLabel[57];
//        for (k = 0; k < 57; k++)
//        {
//            labels[k] = new JLabel(icon[k]);
//        }
//
//
//        // place your 3 controls into frame
//        for (k = 0; k < 57; k++)
//            frmMyWindow.add(labels[k]);

        // show everything to the user
        frmMyWindow.setVisible(true);

    }
}
