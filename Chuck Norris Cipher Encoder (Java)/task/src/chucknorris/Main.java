package chucknorris;

public class Main {

    public static void main(String[] args) {

//        final String encryptedMessage = IOUtil.showEncryptedResult(IOUtil.scanInputToEncrypt());
//        System.out.println("Odszyfrowane: " + Parser.decryptChuckNorrisCipher(encryptedMessage));
        IOUtil.showDecryptedMessage(IOUtil.scanEncodedInput());
    }
}