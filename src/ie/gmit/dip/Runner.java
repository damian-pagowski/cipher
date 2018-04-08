package ie.gmit.dip;

import java.io.IOException;
import java.util.Scanner;

public class Runner {

//    public static void main(String[] args) {
//        // main menu
//        String mainMenuPrompt = "[1] Set keywords\n[2] Encrypt\n[3] Decrypt\n[0] Exit";
//        int[] choiceAvailable = {0, 1, 2, 3};
//        Runner runner = new Runner();
//        int choice = -1;
//        while (choice != 0)
//            choice = runner.buildMenu(choiceAvailable, mainMenuPrompt);
//        switch (choice) {
//            case 1:
//                getKeywords();
//                break;
//            case 2:
//                getEncryptionDetails();
//                break;
//            case 3:
//                getDecryptionDetails();
//                break;
//        }
//
//    }

    public static void main(String[] args) throws IOException {
        FourSquareCipher cipher = new FourSquareCipher();
        FileUtil file = new FileUtil();
        String s = file.readFile("./input_test.txt");
        System.out.println(s);
        String upperCaseText = s.toUpperCase();
        if (upperCaseText.length() % 2 == 1) {
            upperCaseText += "X"; //enxure even length
        }
        String encoded = cipher.encrypt(upperCaseText);
        System.out.println(encoded);
        String decoded = cipher.decrypt("ESPDKWUMBTWGRIESFANNWXWSWDQTHGMFTL");
        System.out.println(decoded);
        file.writeToFile(decoded, "./input_test_decoded.txt");

    }


    private static void getDecryptionDetails() {
    }

    private static void getEncryptionDetails() {

    }

    private static void getKeywords() {


    }

    private int buildMenu(int[] choiceAvailable, String prompt) {
        Scanner scanner = new Scanner(System.in);
        displayMainMenu(prompt);
        int value = 0;
        while (true) {
            value = scanner.nextInt();
            if (!contains(value, choiceAvailable)) {
                System.out.println("Invalid input, please try again");
                displayMainMenu(prompt);
            } else {
                System.out.println("Selected: " + value);
                break;
            }
        }
        return value;

    }

    private void displayMainMenu(String prompt) {
        System.out.println("please select menu item");
        System.out.println(prompt);
    }

    private boolean contains(int i, int[] arr) {
        for (int elem : arr) {
            if (elem == i) {
                return true;
            }
        }
        return false;
    }
}
