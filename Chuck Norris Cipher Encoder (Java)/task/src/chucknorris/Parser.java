package chucknorris;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Parser() {
        // do nothing
    }

    /**
     * Method converts a String object into a list - its elements are chars of input converted to binary
     * and completed with zeroes at the beginning when representation is too short (has less than 7 characters).
     * This list is concatenated into one String object which is later encrypted: converted into zeroes and spaces.
     *
     * @param input a String which user wants to encrypt
     * @return encrypted String
     */

    public static String encryptByChuckNorrisCipher(String input) {
        final List<String> binary7sList = convertStringIntoBinary7sList(input);
        final String binariesConcatenated = String.join("", binary7sList);
        return parseBinariesToChuckBlock(binariesConcatenated);
    }

    private static List<String> convertStringIntoBinary7sList(String input) {
        final List<String> binary7sList = new ArrayList<>();
        input.chars()
                .mapToObj(Parser::parseCharToStringBinary7)
                .forEach(binary7sList::add);
        return binary7sList;
    }

    private static String parseCharToStringBinary7(int ch) {
        String bin = Integer.toBinaryString(ch); // change one int-char into binary written as String
        final int length = bin.length(); // bin can be too short; representation is to have 7 characters (0-1)
        final int lenDif = 7 - length;
        if (lenDif > 0) {
            bin = String.format("%s%s", "0".repeat(lenDif), bin); // I complete too short representation with zeroes at the beginning
        }
        return bin;
    }

    private static String parseBinariesToChuckBlock(String binaries7) {
        final StringBuilder sb = new StringBuilder();
        final int len = binaries7.length();
        int move;
        for (int i = 0; i < len; i += move) {
            if (binaries7.charAt(i) == '1') {
                sb.append("0 ");
                final int onesNumber = countSameSignsAndAddZeroes(sb, binaries7, '1', i, len);
                sb.append(" ");
                move = onesNumber;
            } else {
                sb.append("00 ");
                final int zeroesNumber = countSameSignsAndAddZeroes(sb, binaries7, '0', i, len);
                sb.append(" ");
                move = zeroesNumber;
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static int countSameSignsAndAddZeroes(StringBuilder sb, String binaries7, char ch, int i, int len) {
        int counter = 1;
        for (int j = i + 1; j < len; j++) {
            if (binaries7.charAt(j) == ch) {
                counter++;
            } else {
                break;
            }
        }
        sb.append("0".repeat(counter));
        return counter;
    }
}
