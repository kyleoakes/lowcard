package lowcard;

public class Card
{

    public enum Suit
    {
        CLUBS,DIAMONDS,HEARTS,SPADES
    }

    char value;
    Suit suit;
    boolean errorFlag;
    
    public Card()
    {
        this('A',Suit.SPADES);
    }

    public Card(char value, Suit suit)
    {
        set(value, suit);

    }


    public boolean set(char value, Suit suit)
    {
        this.suit = suit;
        errorFlag = !isValid(value, suit);
        if (!errorFlag)
        {
            this.value = charVal(value);
            return true;
        }

        return false;
    }

    private boolean isValid(char value, Suit suit)
    {
        //turns lowercase chars to uppercase
        //this assumes that the value will be a letter
        //characters that are not part of the alphabet will be accepted
        value = charVal(value);
        if (value >49 && value < 58)
        {
            return true;
        }
        switch (value)
        {
            case 'A':
                return true;
            case 'J':
                return true;
            case 'K':
                return true;
            case 'Q':
                return true;
            case 'T':
                return true;
            case 'X':
                return true;

            default:
                return false;
        }

    }

    public static char[] valueRanks()
    {
        return new char[] {'A', '2', '3', '4', '5', '6', '7','8', '9', 'T', 'J', 'Q', 'K', 'X'};
    }

    public static void arraySort(Card [] arr, int arraySize)
    {
        for(int i = 0; i < arraySize -1; i++)
        {
            for (int j = 0; j < arraySize -i -1; j++)
            {
                int val1 = getIntValue(arr[j].getValue());
                int val2 = getIntValue(arr[j+1].getValue());
                if(val1 > val2)
                {
                    Card temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;

                }
            }
        }
    }

    static int getIntValue(char value)
    {
        for (int i = 0; i < valueRanks().length; i++)
        {
            if (valueRanks()[i] == value)
                return i;
        }
        return -1;
    }
    public String toString()
    {
        if (errorFlag)
        {
            return "[invalid]";
        }
        return "[" + ((value == 'X')?"JOKER":value) + " " +  suit + "]";
    }

    public char charVal(char value)
    {
        if (value > 90)
        {
            return (char)(value - 32);
        }

        return value;
    }

    public Suit getSuit()
    {
        return suit;
    }

    public char getValue()
    {
        return value;
    }

    public boolean getErrorFlag()
    {
        return errorFlag;
    }

    public boolean equals(Card card)
    {
        if (card == null)
        {
            return false;
        }

        return value == card.getValue() && suit == card.getSuit() &&
                errorFlag == card.getErrorFlag();
    }
}




