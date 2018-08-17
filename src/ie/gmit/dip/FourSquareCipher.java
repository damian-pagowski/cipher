package ie.gmit.dip;

import java.util.Random;

public class FourSquareCipher {

    private char[][] topLeftSquare = new char[5][5];
    private char[][] topRightSquare = new char[5][5]; // Top Right Quadrant
    private char[][] bottomLeftSquare = new char[5][5]; // Bottom Left Quadrant:
    private char[][] bottomRightSquare = new char[5][5];
    private final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    private String key1;
    private String key2;

    /*
    default keys, great for testing
     */
    public FourSquareCipher() {
        this("ZGPTFOIHMUWDRCNYKEQAXVSBL", "MFNBDCRHSAXYOGVITUEWLQZKP");
    }

    public FourSquareCipher(String key1, String key2) {
        this.key1 = key1;
        this.key2 = key2;
        this.topLeftSquare = buildSquare(ALPHABET); // UPPER LEFT
        this.topRightSquare = buildSquare(key1); // UPPER RIGHT
        this.bottomLeftSquare = buildSquare(key2); // LOWER LEFT
        this.bottomRightSquare = buildSquare(ALPHABET); // LOWER RIGHT
    }

    public String getKey1() {
        return key1;
    }

    public String getKey2() {
        return key2;
    }

    public void initKeysWithGeneratedValues() {
        key1 = generateKey();
        key2 = generateKey();
    }

    /*
    generates randomized string that can be used as key.
     */
    private String generateKey() {
        Random rand = new Random();
        char[] chars = ALPHABET.toCharArray();
        char[] charsRandom = new char[chars.length];
        copyArray(chars, charsRandom);
        int swapCount = 10;
        while (swapCount >= 0) {
            int i = rand.nextInt(chars.length);
            int j = rand.nextInt(chars.length);
            char tmp = charsRandom[i];
            charsRandom[i] = charsRandom[j];
            charsRandom[j] = tmp;
            swapCount--;
        }
        return String.copyValueOf(charsRandom);
    }

    /*
    helper function to copy array
     */
    private void copyArray(char[] from, char[] to) {
        for (int i = 0; i < from.length; i++) {
            to[i] = from[i];
        }
    }

    /*
    builds 2D array called here - square - using key (that is passed by user)
     */
    private char[][] buildSquare(String key) {
        char[][] square = new char[5][5];
        int idx = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                square[i][j] = key.charAt(idx);
                idx++;
            }
        }
        return square;
    }

    /*
    encrypts text passed as param
     */
    public String encrypt(String text) {
        text = addExtraCharIfLengthNotEven(text);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= text.length() - 2; i += 2) {
            String biagram = text.substring(i, i + 2);
            sb.append(encryptBigram(biagram));
        }
        return sb.toString();
    }

    /*
    appending X to text if length is not even
     */
    private String addExtraCharIfLengthNotEven(String text) {
        if (text.length() / 2 != 0) {
            text += "X";
        }
        return text;
    }

    /*
    decrypt text by splitting it into biagrams, then encode each and appents to StringBuffer object.
    If number of characters is not even - calls method addExtraCharIfLengthNotEven that appends X to text.
     */
    public String decrypt(String text) {
        text = addExtraCharIfLengthNotEven(text);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= text.length() - 2; i += 2) {
            String biagram = text.substring(i, i + 2);
            sb.append(decryptBigram(biagram));
        }
        return sb.toString();
    }

    /*
    encrypt biagram
     */
    private String encryptBigram(String s) {
        int[] position1 = findPosition(s.charAt(0), topLeftSquare);
        int[] position2 = findPosition(s.charAt(1), bottomRightSquare);
        char encodedChar1 = topRightSquare[position1[0]][position2[1]];
        char encodedChar2 = bottomLeftSquare[position2[0]][position1[1]];
        return "" + encodedChar1 + encodedChar2;
    }

    /*
    decrypt biagram
     */
    private String decryptBigram(String s) {
        int[] position1 = findPosition(s.charAt(0), topRightSquare);
        int[] position2 = findPosition(s.charAt(1), bottomLeftSquare);
        char encodedChar1 = topLeftSquare[position1[0]][position2[1]];
        char encodedChar2 = bottomRightSquare[position2[0]][position1[1]];
        return "" + encodedChar1 + encodedChar2;
    }

    /*
    find position (number of row and column) of character in square (2D array)
     */
    public int[] findPosition(char c, char[][] square) {
        int[] pos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (square[i][j] == c) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        return pos;
    }
}