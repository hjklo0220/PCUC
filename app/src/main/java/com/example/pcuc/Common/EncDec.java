package com.example.pcuc.Common;

import android.util.Base64;

import org.spongycastle.util.Arrays;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncDec {
    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(),1);
    }

    private static String KEY = "DE0339B16467BC3654DCCAEC9428C79CA5D42D67D9DED156B0D2136C042FB53E";
    private static String instance = "AES/CBC/PKCS7Padding";
    private static String algo = "AES";
    private static String charset = "UTF-8";
    private static String errMsg = "";

    public static String hash(String str){// 사용법 EncDec.hash(문자열);
        String SHA = errMsg;
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();

        }catch(NoSuchAlgorithmException ignored){
        }
        System.out.println("SHa : " + SHA);
        return SHA;
    }

    public static String Encrypt(String plain){ // 사용법 EncDec.Encrypt("abc");
        try {
            return Encrypt(plain,KEY.getBytes(charset));
        } catch (Exception e) {
            return errMsg;
        }
    }

    public static String Encrypt(String plain, byte[] s_key) throws Exception {
        //String key = new String(s_key);
        byte[] iv = Arrays.copyOfRange(s_key,0,16); //s_key.substring(0, 16).getBytes();
        SecretKeySpec newKey = new SecretKeySpec(s_key,0,256/8, algo);
        Cipher cipher = Cipher.getInstance(instance);
        cipher.init(Cipher.ENCRYPT_MODE, newKey, new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(plain.getBytes());
        return Base64.encodeToString(encrypted,Base64.NO_WRAP);
    }

    public static String Decrypt(String Encrypt_text){ //사용법 EncDec.Decrypt(Encrypt_and_Encode_text);
        try {
            return Decrypt(Encrypt_text,KEY.getBytes(charset));
        } catch (Exception e) {
            return errMsg;
        }
    }

    public static String Decrypt(String Encrypt_text, byte[] s_key) throws Exception {
        byte[] enc = Base64.decode(Encrypt_text,Base64.NO_WRAP);
        byte[] iv = Arrays.copyOfRange(s_key,0,16); //s_key.substring(0, 16).getBytes();
        SecretKeySpec newKey = new SecretKeySpec(s_key,0,256/8, algo);
        Cipher cipher = Cipher.getInstance(instance);
        cipher.init(Cipher.DECRYPT_MODE, newKey, new IvParameterSpec(iv));
        byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }


}
