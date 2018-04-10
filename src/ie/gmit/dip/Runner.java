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
        String mainMenuPrompt = "[1] Set keywords\n[2] Generate keywords\n[3] Use default keywords\n[4] Encrypt\n[5] Decrypt\n[0] Exit";
        int[] choiceAvailable = {0, 1, 2, 3, 4};
        Runner runner = new Runner();
        FileUtil fileUtil = new FileUtil();
        int choice = runner.menu(choiceAvailable, mainMenuPrompt);
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
                case 3:
                    cipher = new FourSquareCipher();
                    printKeysSet(cipher);
                    break;
                case 4:
                    if (cipher != null) {
                        System.out.println("Encryption");
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
                        System.out.println("Decryption");
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
                default:
                    System.out.println("Invalid input. Hit enter and try again");
            }
            choice = runner.menu(choiceAvailable, mainMenuPrompt);
        }
    }

    private static void printKeysSet(FourSquareCipher cipher) {
        System.out.println("Encryption keys set!");
        System.out.println("Save keys for future use:");
        System.out.println("Key 1: "+ cipher.getKey1());
        System.out.println("Key 2: "+ cipher.getKey2());
    }

    private void getUserInputPathsToFiles() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert path to input file and hit enter: ");
        this.pathToInput = scanner.nextLine();
        System.out.println("Insert path to output file and hit enter: ");
        this.pathToOutput = scanner.nextLine();
    }

    private void initKeywords() {
        this.key1 = getKeyInput("Insert first key (25 unique letters combination) and hit enter");
        this.key2 = getKeyInput("Insert second key (25 unique letters combination) and hit enter");
    }

    private String getKeyInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (input.length() != 25) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

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
                System.out.println("Selected: " + value);
                break;
            }
        }
        return value;
    }

    private void displayMainMenu(String prompt) {
        System.out.println("Insert item number and hit enter:");
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
