package com.example.forest.quickguess.Utilities;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
    private static String AES = "AES";
    private static String passwordKey = "password";

    public static String encryptMethod(String s) throws Exception
    {
        SecretKeySpec key = generateKey();
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encValue = c.doFinal(s.getBytes());
        return  Base64.encodeToString(encValue,Base64.DEFAULT);
    }

    public static String decryptMethod(String outputString) throws Exception {
        SecretKeySpec key = generateKey();
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeValue = Base64.decode(outputString,Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodeValue);
        return new String(decValue);
    }

    private static SecretKeySpec generateKey() throws  Exception
    {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = passwordKey.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        return new SecretKeySpec(key,AES);
    }

}
