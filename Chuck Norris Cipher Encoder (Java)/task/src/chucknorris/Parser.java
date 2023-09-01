package chucknorris;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Parser {

    private static final String ERROR_ENCODED_MESSAGE_INVALID = "Encoded string is not valid.";

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
    public static String encryptWithChuckNorrisCipher(String input) {
        final List<String> binary7sList = convertStringIntoBinary7sList(input);
        final String binariesConcatenated = String.join("", binary7sList);
        return parseBinariesToChuckBlock(binariesConcatenated);
    }

    /**
     * Method decrypts message encrypted with Chuck Norris cipher:
     * firstly divides an encrypted string into pairs of zeros blocks (parsed into a list of DigitBlocks -
     * each object represents 0 and zeros count or 1 and ones count);
     * the list is converted into one string of zeroes and ones;
     * this string is cut into 7-digit-long parts;
     * each part is converted into an integer number (as its binary representation);
     * an integer number represents a char in ASCII
     *
     * @param encryptedMessage a string message encrypted with Chuck Norris cipher
     * @return a string encrypted message
     */
    public static String decryptChuckNorrisCipher(String encryptedMessage) {
        if (encryptedMessage == null || encryptedMessage.isEmpty() || encryptedMessage.matches(".*1.*")) {
            throw new EncodedFormatException(ERROR_ENCODED_MESSAGE_INVALID + "1");
        }
        final List<DigitBlock> digitBlocksList = parseEncryptedMessageToDigitBlocksList(encryptedMessage);
        final StringBuilder sb = new StringBuilder();
        digitBlocksList.forEach(db -> sb.append(db.getDigit().repeat(db.digitsCount)));
        // pociąć na siódemki
        if (sb.length() % 7 != 0) {
            throw new EncodedFormatException(ERROR_ENCODED_MESSAGE_INVALID + "2");
        }
        final List<String> sevens = new ArrayList<>(); // chars in binary system
        for (int i = 0; i < sb.length(); i += 7) {
            sevens.add(sb.substring(i, i + 7));
        }
        return parseBinarySevensListToStringMessage(sevens);
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

    private static List<DigitBlock> parseEncryptedMessageToDigitBlocksList(String encryptedMessage) {
        final String[] blocks = encryptedMessage.split(" ");
        if (blocks.length % 2 != 0) {
            throw new EncodedFormatException(ERROR_ENCODED_MESSAGE_INVALID + "3");
        }
        final List<DigitBlock> digitBlocksList = new ArrayList<>();
        for (int i = 0; i < blocks.length; i += 2) {
            digitBlocksList.add(
                    new DigitBlock(
                            switch (blocks[i]) {
                                case "0" -> "1";
                                case "00" -> "0";
                                default -> throw new EncodedFormatException(ERROR_ENCODED_MESSAGE_INVALID + "4");
                            },
                            blocks[i + 1].length())
            );
        }
        return digitBlocksList;
    }

    private static String parseBinarySevensListToStringMessage(List<String> sevens) {
        StringBuilder sb = new StringBuilder();
        sevens.stream()
                .map(sev -> {
                            final String[] digitsArray = sev.split("");
                            final int len = digitsArray.length;
                            final List<Integer> intValues = new ArrayList<>();
                            IntStream.rangeClosed(0, 6)
                                    .forEach(i -> intValues.add(Integer.parseInt(digitsArray[len - i - 1]) * powerOfNumTwo(i)));
                            return intValues.stream().mapToInt(d -> d).sum();
                        }
                )
                .mapToInt(i -> i)
                .mapToObj(i -> (char) i)
                .forEach(sb::append);
        return sb.toString();
    }

    // only for i >= 0
    private static int powerOfNumTwo(int n) {
        if (n == 0)
            return 1;
        if (n < 0)
            throw new IllegalArgumentException("Exponent cannot be negative");
        return 2 * powerOfNumTwo(n - 1);
    }
}