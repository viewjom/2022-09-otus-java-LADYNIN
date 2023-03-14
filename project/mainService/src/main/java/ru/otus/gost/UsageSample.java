package ru.otus.gost;

import org.springframework.util.Base64Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class UsageSample {

    public String getHex(byte[] fileBytes) throws NoSuchAlgorithmException, NoSuchProviderException {
        String hash = "";

        if (Security.getProvider("JStribog") == null) {
            Security.addProvider(new StribogProvider());
        }

      //  MessageDigest md = MessageDigest.getInstance("Stribog512");
        MessageDigest md = MessageDigest.getInstance("StribogB512");

        byte[] digest = md.digest( reverse(Base64Utils.encode(fileBytes)));

       // byte[] digest = reverse(md.digest(fileBytes));


       // byte[] digest = md.digest(reverse(fileBytes));
       /// byte[] digest = md.digest(fileBytes);
                //byte[] digest = reverse(md.digest(reverse(fileBytes)));

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
