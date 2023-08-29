package chucknorris;

public class DigitBlock {
    private final String digit;
    public final int digitsCount;

    public DigitBlock(String digit, int digitsCount) {
        this.digit = digit;
        this.digitsCount = digitsCount;
    }

    public String getDigit() {
        return digit;
    }
}