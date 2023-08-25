package chucknorris;

import java.util.Arrays;
import java.util.Scanner;

public class Util {
    private Util() {
        // nothing
    }

    public static String scanInput() {
        System.out.println("Input string:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String parseInputToSigns(String input) {
        final String[] signs = input.split("");
        final StringBuilder sb = new StringBuilder();
        Arrays.stream(signs).forEach(x -> sb.append(x).append(" "));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
