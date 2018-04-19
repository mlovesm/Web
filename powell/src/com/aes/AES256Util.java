package com.aes;

import java.io.UnsupportedEncodingException;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class AES256Util
{

    public AES256Util(String key)
        throws UnsupportedEncodingException
    {
        iv = key.substring(0, 16);
        byte keyBytes[] = new byte[16];
        byte b[] = key.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        this.keySpec = keySpec;
    }

    public String aesEncode(String str)
        throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(1, keySpec, new IvParameterSpec(iv.getBytes()));
        byte encrypted[] = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }

    public String aesDecode(String str)
        throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(2, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
        byte byteStr[] = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), "UTF-8");
    }

    private String iv;
    private Key keySpec;
}
