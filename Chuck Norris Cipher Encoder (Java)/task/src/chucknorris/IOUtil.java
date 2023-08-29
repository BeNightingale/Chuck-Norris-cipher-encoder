package chucknorris;

import java.util.Scanner;

public class IOUtil {

    private IOUtil() {
        // nothing
    }

    public static String scanInputToEncrypt() {
        System.out.println("Input string:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String showEncryptedResult(String userInput) {
        System.out.println("The result:");
        final String encryptedMessage = Parser.encryptWithChuckNorrisCipher(userInput);
        System.out.println(encryptedMessage);
        return encryptedMessage;
    }

    public static String scanEncodedInput() {
        System.out.println("Input encoded string:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void showDecryptedMessage(String encryptedMessage) {
        System.out.println("The result:");
        System.out.println(Parser.decryptChuckNorrisCipher(encryptedMessage));
    }
}