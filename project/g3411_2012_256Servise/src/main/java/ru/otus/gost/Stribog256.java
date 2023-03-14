package ru.otus.gost;

/**
 *
 * @author sarin
 */
public class Stribog256 extends Stribog{

    @Override
    protected byte[] engineDigest() {
        byte[] digest = getDigest(IV);
        byte[] result = new byte[32];
        System.arraycopy(digest, 0, result, 0, result.length);
        return result;
    }
    public static final int[] IV = new int[64];

    static {
        for (int i = 0; i < IV.length; i++) {
            IV[i] = 0x01;
        }
    }

}