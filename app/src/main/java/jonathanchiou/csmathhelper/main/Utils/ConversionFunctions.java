package jonathanchiou.csmathhelper.main.Utils;

//To be honest, I ripped the methods in this class from my 1st android app
//Yes the app sucked, but some of the code behind it was good enough to re-use.
public class ConversionFunctions {

    public static String decToBin(int decInt) {
        StringBuilder binStr = new StringBuilder();
        int rem;
        if (decInt == 0)
            return "0";

        while (decInt > 0) {
            rem = decInt % 2;
            binStr.append(rem);
            decInt = decInt / 2;
        }
        binStr.reverse(); //NOTE TO SELF: APPEND ATTACHES TO THE RIGHT

        return binStr.toString();
    }

    public static char decToHexDigit(int dec) {
        switch (dec) {
            case 0:
                return '0';
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            case 9:
                return '9';
            case 10:
                return 'A';
            case 11:
                return 'B';
            case 12:
                return 'C';
            case 13:
                return 'D';
            case 14:
                return 'E';
            case 15:
                return 'F';
            default:
                return 'Z';
        }
    }

    public static String decToHex(int decINT) {
        int i = 0;
        while (Math.pow(16, i + 1) <= decINT)
            ++i;
        //above code works

        int hexDigit = 0;
        char c;
        StringBuilder hexNum = new StringBuilder();
        for (; i >= 0; --i) {
            hexDigit = decINT / ((int) Math.pow(16, i));
            decINT = decINT - (hexDigit * ((int) Math.pow(16, i)));
            //The reason I need to do this subtraction is so that I can "shrink" decINT to the
            //next lowest power of 16. *shrink as in get the next digit
            c = decToHexDigit(hexDigit); //need to hold the digit to check if I have Z/Error char
            if (c != 'Z')
                hexNum.append(c);
            else
                return "Error @public String decToHex(String dec) function.";
        }

        return hexNum.toString();
    }

}
