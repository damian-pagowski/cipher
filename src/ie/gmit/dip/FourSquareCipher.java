package ie.gmit.dip;

public class FourSquareCipher {

    public char[][] topLeftSquare = new char[5][5];
    public char[][] topRightSquare = new char[5][5]; // Top Right Quadrant
    public char[][] bottomLeftSquare = new char[5][5]; // Bottom Left Quadrant:
    public char[][] bottomRightSquare = new char[5][5];
    public final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    public String key1;
    public String key2;

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

    public String encrypt(String text) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i += 2) {
            sb.append(encryptBigram(text.substring(i, i + 2)));
        }
        return sb.toString();
    }

    public String decrypt(String text) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i += 2) {
            sb.append(decryptBigram(text.substring(i, i + 2)));
        }
        return sb.toString();
    }

    private String encryptBigram(String s) {
        int[] position1 = findPosition(s.charAt(0), topLeftSquare);
        int[] position2 = findPosition(s.charAt(1), bottomRightSquare);
        char encodedChar1 = topRightSquare[position1[0]][position2[1]];
        char encodedChar2 = bottomLeftSquare[position2[0]][position1[1]];
        return "" + encodedChar1 + encodedChar2;
    }

    private String decryptBigram(String s) {
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