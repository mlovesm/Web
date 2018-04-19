package com.aes;


// Referenced classes of package com.aes:
//            AES256Util

public class MainAES
{

    public MainAES()
    {
    }

    public static void main(String args[])
        throws Exception
    {
        String key = "aes256-test-key!!";
        AES256Util aes256 = new AES256Util(key);
        String text = "암호화되지 않은 문자입니다.";
        String encText = aes256.aesEncode(text);
        String decText = aes256.aesDecode(encText);
        System.out.println((new StringBuilder("암호화할 문자 : ")).append(text).toString());
        System.out.println((new StringBuilder("암호화된 문자 : ")).append(encText).toString());
        System.out.println((new StringBuilder("복호화된 문자 : ")).append(decText).toString());
    }
}
