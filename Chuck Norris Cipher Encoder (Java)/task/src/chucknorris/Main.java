package chucknorris;

public class Main {
    public static void main(String[] args) {

        final String userInput = Util.scanInput();
        Parser.showCharsAndTheirBytes(userInput);
    }
}