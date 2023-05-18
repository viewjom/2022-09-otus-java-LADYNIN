package ru.otus.service;

import org.bouncycastle.jcajce.provider.digest.GOST3411;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class ServiceGost3411 {

    public String getHex(byte[] fileBytes) throws NoSuchAlgorithmException, NoSuchProviderException {
        String hash = "";
        var digest = new GOST3411.Digest2012_512().digest(fileBytes);

        for (byte b : digest) {
            int iv = (int) b & 0xFF;
            if (iv < 0x10) {
                hash = hash + "0";
            }
            hash = hash + Integer.toHexString(iv).toUpperCase();
        }

        return hash;
    }

    private static byte[] reverse(byte[] ba) {
        byte[] result = new byte[ba.length];
        for (int i = ba.length - 1; i >= 0; i--) {
            result[ba.length - 1 - i] = ba[i];
        }
        return result;
    }
}