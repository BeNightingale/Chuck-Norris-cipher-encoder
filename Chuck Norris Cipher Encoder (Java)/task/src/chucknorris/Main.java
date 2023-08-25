package chucknorris;

public class Main {
    public static void main(String[] args) {

        final String userInput = Util.scanInput();
        final String output = Util.parseInputToSigns(userInput);
        System.out.println(output);
    }
}