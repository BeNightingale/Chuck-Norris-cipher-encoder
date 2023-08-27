package chucknorris;

public class Parser {

    private Parser() {
        // do nothing
    }

    public static void showCharsAndTheirBytes(String input) {
        System.out.println("The result:");
        input.chars().forEach(c -> {
            String bin = Integer.toBinaryString(c);
            final int length = bin.length();
            final int lenDif = 7 - length;
            if (lenDif > 0) {
                bin = String.format("%s%s", "0".repeat(lenDif), bin);
            }
            System.out.println(Character.toString(c) + " = " + bin);
        });
    }
}
