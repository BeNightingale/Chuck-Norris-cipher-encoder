package chucknorris;

import java.util.*;

public class OperationManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Runnable encodeMessage = OperationManager::encode;
    private static final Runnable decodeMessage = OperationManager::decode;
    private static final Runnable exit = OperationManager::exit;
    private static final Map<String, Runnable> managerMap = Map.of(
            "encode", encodeMessage,
            "decode", decodeMessage,
            "exit", exit
    );

    private OperationManager() {
        // nothing
    }

    public static void performUserOrders() {
        String userOrder = getUserDecisionAboutOperation();
        while (!Objects.equals(userOrder, "exit")) {
            try {
                managerMap.get(userOrder).run();
            } catch (EncodedFormatException enEx) {
                System.out.println(enEx.getMessage());
            }
            userOrder = getUserDecisionAboutOperation();
        }
        managerMap.get(userOrder).run();
    }

    public static String getUserDecisionAboutOperation() {
        System.out.println("Please input operation (encode/decode/exit):");
        String userOptionChoice = scanner.nextLine();
        while (!isOperationChoiceValid(userOptionChoice)) {
            System.out.printf("There is no '%s' operation%n%n", userOptionChoice);
            System.out.println("Please input operation (encode/decode/exit):");
            userOptionChoice = scanner.nextLine();
        }
        return userOptionChoice;
    }

    private static boolean isOperationChoiceValid(String operationChoice) {
        if (operationChoice == null || operationChoice.isEmpty())
            return false;
        final List<String> operationChoicesList = Arrays.asList("encode", "decode", "exit");
        return operationChoicesList.contains(operationChoice);
    }

    private static void exit() {
        System.out.println("Bye!");
    }

    private static void encode() {
        final String messageToEncode = scanInput("Input string:");
        showEncryptedMessage(messageToEncode);
    }

    private static void decode() {
        final String messageToDecode = scanInput("Input encoded string:");
        showDecryptedMessage(messageToDecode);
    }

    private static String scanInput(String infoForUser) {
        System.out.println(infoForUser);
        return scanner.nextLine();
    }

    private static void showEncryptedMessage(String userInput) {
        System.out.printf(
                "Encoded string:%n%s%n",
                Parser.encryptWithChuckNorrisCipher(userInput)
        );
    }

    public static void showDecryptedMessage(String encryptedMessage) {
        System.out.printf(
                "Decoded string:%n%s%n",
                Parser.decryptChuckNorrisCipher(encryptedMessage)
        );
    }
}