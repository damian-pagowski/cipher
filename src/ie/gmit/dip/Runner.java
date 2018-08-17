package ie.gmit.dip;

import java.io.IOException;
import java.util.Scanner;

public class Runner {
    private String key1;
    private String key2;
    private String pathToInput;
    private String pathToOutput;

    public static void main(String[] args) {
        FourSquareCipher cipher = null;
        // below prompt text that will be displayed to user
        String mainMenuPrompt = "[1] Set keywords\n[2] Generate keywords\n[3] Use default keywords\n[4] Encrypt File\n[5] Decrypt File\n[6] Encrypt Phrase\n[7] Decrypt Phrase\n[0] Exit";
        int[] choiceAvailable = {0, 1, 2, 3, 4, 5, 6, 7}; // list of possible choice  - should match prompt defined above
        Runner runner = new Runner();
        FileUtil fileUtil = new FileUtil();
        // initial call of menu method - later its called at the end of loop
        int choice = runner.menu(choiceAvailable, mainMenuPrompt);
        // in loop -  until user enters 0 (exit) reads user input, execute code to complete action user selected
        while (choice != 0) {
            switch (choice) {
                case 1:
                    runner.initKeywords();
                    cipher = new FourSquareCipher(runner.key1, runner.key2);
                    break;
                case 2:
                    cipher = new FourSquareCipher();
                    cipher.initKeysWithGeneratedValues();
                    printKeysSet(cipher);
                    break;
                case 3:
                    cipher = new FourSquareCipher();
                    printKeysSet(cipher);
                    break;
                case 4:
                    if (cipher != null) {
                        System.out.println("Encrypt File");
                        runner.getUserInputPathsToFiles();
                        try {
                            String content = fileUtil.readFile(runner.pathToInput);
                            String contentEncrypted = cipher.encrypt(content);
                            fileUtil.writeToFile(contentEncrypted, runner.pathToOutput);
                            System.out.println("Done.");
                            System.exit(0);
                        } catch (IOException e) {
                            System.out.println("Please check if paths to files you provided are correct and try again");
                        }
                    } else {
                        System.out.println("Please provide encryption keys first");
                    }
                    break;
                case 5:
                    if (cipher != null) {
                        System.out.println("Decrypt File");
                        runner.getUserInputPathsToFiles();
                        try {
                            String content = fileUtil.readFile(runner.pathToInput);
                            String contentEncrypted = cipher.decrypt(content);
                            fileUtil.writeToFile(contentEncrypted, runner.pathToOutput);
                            System.out.println("Done.");
                            System.exit(0);
                        } catch (IOException e) {
                            System.out.println("Please check if paths to files you provided are correct and try again");
                        }
                    } else {
                        System.out.println("Please provide encryption keys first");
                    }
                    break;
                case 6:
                    if (cipher != null) {
                        System.out.println("Encrypt Phrase");
                        String phrase = runner.getUserInput("Please insert your phrase and hit enter.");
                        phrase = clearText(phrase);
                        String phraseEncrypted = cipher.encrypt(phrase);
                        System.out.println("Your phrase encrypted:\n");
                        System.out.println(phraseEncrypted);
                    } else {
                        System.out.println("Please provide encryption keys first");
                    }
                    break;
                case 7:
                    if (cipher != null) {
                        System.out.println("Decrypt Phrase");
                        String phrase = runner.getUserInput("Please insert phrase you want decrypt and hit enter.");
                        phrase = clearText(phrase);
                        String phraseDecrypted = cipher.decrypt(phrase);
                        System.out.println("Your phrase decrypted:\n");
                        System.out.println(phraseDecrypted);
                    } else {
                        System.out.println("Please provide encryption keys first");
                    }
                    break;
                default:
                    System.out.println("Invalid input. Hit enter and try again");
            }
            choice = runner.menu(choiceAvailable, mainMenuPrompt);
        }
    }

    /* helper function, removes all non alphabetic characters and transforms to uppercase*/
    private static String clearText(String phrase) {
        phrase = phrase.replaceAll("[^A-Za-z]", "").toUpperCase();
        return phrase;
    }

    /* print keys used for encryption / decryption*/
    private static void printKeysSet(FourSquareCipher cipher) {
        System.out.println("Encryption keys set!");
        System.out.println("Key 1: " + cipher.getKey1());
        System.out.println("Key 2: " + cipher.getKey2());
    }

    /*display prompt and read path to files from user (console)*/
    private void getUserInputPathsToFiles() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert path to input file and hit enter: ");
        this.pathToInput = scanner.nextLine();
        System.out.println("Insert path to output file and hit enter: ");
        this.pathToOutput = scanner.nextLine();
    }

    /*helper function that initializes fields with keywords */
    private void initKeywords() {
        this.key1 = getKeysFromUser("Insert first key (25 unique letters combination) and hit enter");
        this.key2 = getKeysFromUser("Insert second key (25 unique letters combination) and hit enter");
    }

    /* helper function used to get keyword from user. does validation for keyword lenghth*/
    private String getKeysFromUser(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (input.length() != 25) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    /* display prompt and get input from user*/
    private String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /* display menu and get input from user. Return user choice(number) */
    private int menu(int[] choiceAvailable, String prompt) {
        Scanner scanner = new Scanner(System.in);
        displayMainMenu(prompt);
        int value = 0;
        while (true) {
            value = scanner.nextInt();
            if (!contains(value, choiceAvailable)) {
                System.out.println("Invalid input, please try again");
                displayMainMenu(prompt);
            } else {
                System.out.println("Selected:" + value);
                break;
            }
        }
        return value;
    }

    /*
    helper function that actually prints text to console
     */
    private void displayMainMenu(String prompt) {
        System.out.println("Insert item number and hit enter:");
        System.out.println(prompt);
    }

    /* helper function check if array contains given character*/
    private boolean contains(int i, int[] arr) {
        for (int elem : arr) {
            if (elem == i) {
                return true;
            }
        }
        return false;
    }
}
