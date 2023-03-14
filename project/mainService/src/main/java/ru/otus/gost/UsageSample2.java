package ru.otus.gost;

public class UsageSample2 {
/*
    private static final String[] hashNames = {"Stribog512"};
    private static final String[] reversedHashNames = {"Stribog512"}; //Use this digests for loooong messages from streams

    private static final byte[] message = new byte[]{
            0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30
    };

    public static void main(String[] argv) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {

        Path path = Paths.get("g:/CRYPTO/altay.txt");
        byte[] messageF = Files.readAllBytes(path);

        if (Security.getProvider("JStribog") == null) {
            Security.addProvider(new StribogProvider());
        }
        for (String hashName : hashNames) {
            MessageDigest md = MessageDigest.getInstance(hashName);
            byte[] digest = md.digest(messageF);
            printHex(reverse(digest));
        }


    }

    private static void printHex(byte[] digest) {
        for (byte b : digest) {
            int iv = (int) b & 0xFF;
            if (iv < 0x10) {
                System.out.print('0');
            }
            System.out.print(Integer.toHexString(iv).toUpperCase());
        }
        System.out.println();
    }

    private static byte[] reverse(byte[] ba) {
        byte[] result = new byte[ba.length];
        for (int i = ba.length - 1; i >= 0; i--) {
            result[ba.length - 1 - i] = ba[i];
        }
        return result;
    }

 */
}
