package com.akabetech.belaundryondemand.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by akbar.pambudi on 8/11/2016.
 */
public class AESUtil {
    private static final String AESIV = "AAAAAAAAAAAAAAAA";
    private static final String ALFA = "abcdefghij";
    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
        if(plainText.length()%16!=0){
            int val = 16;
            int rawLenght = plainText.length();
            while(true){
                if(val>=plainText.length()){
                    break;
                }
                val*=2;
            }

            for(int i=0;i<(val-rawLenght);i++){
                plainText+="\0";
            }
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(AESIV.getBytes("UTF-8")));
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(AESIV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText),"UTF-8").trim();
    }

    public static String generateKey(){

        String digit = String.valueOf((long) (Math.abs(Math.random() * 10000000000000000L)));
        System.out.println(digit);
        String result = "";
        for(int i =0;i<digit.length();i++){
            String target = digit.substring(i,i+1);
            if(i%2==0){
                int targetIndex = Integer.valueOf(target);
                target = ALFA.substring(targetIndex,targetIndex+1);
            }
            result+=target;
        }
    return result;
    }
}
