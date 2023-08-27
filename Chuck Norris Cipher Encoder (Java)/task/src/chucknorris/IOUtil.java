package chucknorris;

import java.util.Scanner;

public class IOUtil {

    private IOUtil() {
        // nothing
    }

    public static String scanInput() {
        System.out.println("Input string:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void showConvertedResult(String userInput) {
        System.out.println("The result:");
        System.out.println(Parser.encryptByChuckNorrisCipher(userInput));
    }
}
