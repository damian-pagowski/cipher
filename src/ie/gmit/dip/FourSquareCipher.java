package ie.gmit.dip;

import java.io.IOException;

public class FourSquareCipher {

    public char[][] topLeftSquare = new char[5][5];
    public char[][] topRightSquare = new char[5][5]; // Top Right Quadrant
    public char[][] bottomLeftSquare = new char[5][5]; // Bottom Left Quadrant:
    public char[][] bottomRightSquare = new char[5][5];

    public static void main(String[] args) throws IOException {
        String key1 = "ZGPTFOIHMUWDRCNYKEQAXVSBL";
        String key2 = "MFNBDCRHSAXYOGVITUEWLQZKP";
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        FourSquareCipher cipher = new FourSquareCipher();
        cipher.topLeftSquare = cipher.buildSquare(alphabet); // UPPER LEFT
        cipher.topRightSquare = cipher.buildSquare(key1); // UPPER RIGHT
        cipher.bottomLeftSquare = cipher.buildSquare(key2); // LOWER LEFT
        cipher.bottomRightSquare = cipher.buildSquare(alphabet); // LOWER RIGHT

        FileUtil file = new FileUtil();
        String s = file.readFile("./input_test.txt");
        System.out.println(s);

        String upperCaseText = s.toUpperCase();
        if (upperCaseText.length() % 2 == 1) {
            upperCaseText += "X"; //enxure even length
        }
        String encoded = cipher.encode(upperCaseText);
        System.out.println(encoded);
        String decoded = cipher.decode("ESPDKWUMBTWGRIESFANNWXWSWDQTHGMFTL");
        System.out.println(decoded);
    }

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

    public String encode(String text) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i += 2) {
            sb.append(encodeBigram(text.substring(i, i + 2)));
        }
        return sb.toString();
    }

    public String decode(String text) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i += 2) {
            sb.append(decodeBigram(text.substring(i, i + 2)));
        }
        return sb.toString();
    }

    private String encodeBigram(String s) {
        int[] position1 = findPosition(s.charAt(0), topLeftSquare);
        int[] position2 = findPosition(s.charAt(1), bottomRightSquare);
        char encodedChar1 = topRightSquare[position1[0]][position2[1]];
        char encodedChar2 = bottomLeftSquare[position2[0]][position1[1]];
        return "" + encodedChar1 + encodedChar2;
    }

    private String decodeBigram(String s) {
        int[] position1 = findPosition(s.charAt(0), topRightSquare);
        int[] position2 = findPosition(s.charAt(1), bottomLeftSquare);
        char encodedChar1 = topLeftSquare[position1[0]][position2[1]];
        char encodedChar2 = bottomRightSquare[position2[0]][position1[1]];
        return "" + encodedChar1 + encodedChar2;
    }

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
