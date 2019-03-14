package haituntiandi.com.haituntiandi.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    //        public static void main(String[] args) {
//            // 密钥的种子，可以是任何形式，本质是字节数组
//            String strKey = "aaaa";
//            // 密钥数据
//            byte[] rawKey = getRawKey(strKey.getBytes());
//            // 密码的明文
//            String clearPwd = "My world is full of wonders! No body can match my spirit";
//            // 密码加密后的密文
//            byte[] encryptedByteArr = encrypt(rawKey, clearPwd);
//            String encryptedPwd = new String(encryptedByteArr);
//            System.out.println(encryptedPwd);
//            // 解密后的字符串
//            String decryptedPwd = decrypt(encryptedByteArr, rawKey);
//            System.out.println(decryptedPwd);
//
//        }
    public static void main(String[] args) {
//        String strSourceData = "{\"requestTime\":\"1534924587822\",\"code\":\"cfd3fc237266b313db3767f45e2bfe76\",\"clientInfo\":\"{\\\"channel\\\":\\\"TAMC-APP\\\",\\\"clientVersion\\\":\\\"10034\\\",\\\"deviceId\\\":\\\"a94839789e57cbfa3c21d4302db61059\\\",\\\"osVersion\\\":\\\"8.0.0\\\",\\\"platform\\\":\\\"A\\\"}\",\"source\":\"0\",\"userId\":\"not_login\",\"recommendStatus\":\"1\"}";
        String strSourceData = "1234abcd";
        String key = "!t@mc#mania%";
        String base64 = AESUtil.encrypt(AESUtil.getRawKey(key.getBytes()), strSourceData);
        System.out.println("\nbase64之后:\n" + base64);

        String encodeData = base64;

        // 解密后的字符串

//        encodeData = "Hnjvv73vv70K77+977+9HAIjM++/ve+/ve+/ve+/vWsQaUTvv73vv73Rqgrvv73vv71pMFfvv707Uu+/vQbHre+/vWRuGu+/vQPvv73vv71qHe+/vTlW1IPvv73vv71P77+977+9HH4TEu+/ve+/ve+/ve+/vXPvv71e77+9QDxfE3rvv70Nfu+/vU9MMO+/ve+/vUrvv73vv70e77+9GD8iakga77+977+9Ue+/vWnvv70lGu+/vULOvRccGe+/ve+/ve+/vR7Ht++/vWI1Ujbvv719Vu+/vWXvv73vv70x77+9P++/vRPvv73vv70SBu+/vXFz77+9dApR77+9DVZb77+9fkd0bjo1WO+/vQNgV++/vRRG77+977+977+977+9C3/vv70vbgrvv71H77+977+9ZjzenO+/ve+/vR9X77+977+9Su+/ve+/vSDvv70577+977+9GXLvv73vv73vv73vv71T77+9a0AFEEdO77+9T++/vTzvv70dQTFv77+9C1vNiWnvv73vv73vv73Ygd6FKe+/ve+/vT3vv70P77+9FmB/77+977+9e++/vUrvv70c2aN377+9IcWoJO+/vQo777+977+9NV/vv73vv70YW++/ve+/vXhI77+9Hu+/vQDvv71HCgx4Czbvv73vv70mQQUgR3nvv70tDUkg77+9PFnvv71N";
//        encodeData = "1b8/uPrcCXWt3RY++0m8QWLDKPEGv38tia/VQna4bfRrICUW3u+PUMOtCrI48vBYdiUO/a4oNBTNRXSAtid6xFMN7Fr9pD/WGwI3/sWraI7OYiKd+US2gOEXQXyHw4vstHKHRVuqELB0aET9SZBrRlSi+P2TqzuhLHZzi/guRrbofkb0c/ExuyFxIJFNe+KYzGuxRbKimztfN779CT12pAH4H7j6pNibv1BxS7Gm24qupIme+JwX/drQm6VDdK2jSWcZG8PoRh9JhczCIhUZoYIF44s0IBOJ8NEfvmw38BWGtGoOSxEK8buxDrRplVKRdCQfn5yaY0rQpwWK3Zyu/uuVYabO4/LcvObhU4wEkyN2qLw74H1gM1NGO0ZkczDryKQs3Pn4Fzc+lfnP1u6kIBx1eP0jJNHq8c8WS//37CZPUdX5iva+LbvJ1ZBiWsP/N9TXDh6w5Tma9EGDQAM/Bw==";
//        encodeData = "fDxyowLTJvE77ctsIBFb/iyNKgLxmw+nCDBtmWSb+DZHXDRw1mvxHFmapxVVw4TZYWVt6YJEU8mqUoI3j7FbwSkksLC4niBT66rhmJHY3PX+lyiwdxQrxh+JTwFGfgJ7cMQzDSbHc69DuuvsULUfhqx+uyektn4ZycZsjQriTomB0ZaYpGBIEMjJNK01jgVe0QJ6VdxcQ+dwW8WPbBWDiMRGoONxojphd0opJnZrPSY21E4pYAG5y28L1rW7A6ouenvrtAei7yta1MnU5NpdxTw0Qi3cNNZ4XfqAnRkfSbWzdFpkVW57SluJXiLvjRGYsX5H5Jv467fPRt6D3Wkm1THHb6V2gmHs82mxZ2vSqZ8EaNJBO43RzTEc2w9r9wjI/Ddqy6CRpFUvQtLi2MPt9Ln+x94x3I0OhTd4/FcAm85w5hnghar7JhG769/d+dRaywwfhk/Qewj0tkxVQRSEcA==";

        String ssssss = "uTHgUiEmxdwfK+CH2VDbug=="; // base64

        System.out.println("base64, \nbase64="+ base64+ "\nssssss="+ ssssss);
        System.out.println("comp, "+ ssssss.equals(base64));


        byte[] bytesData = Base64Utils.decodeByte(ssssss);
//        System.out.println("jiemi, base64解密之后: " + str);
        String decrypted = decrypt(bytesData, AESUtil.getRawKey(key.getBytes()));
        System.out.println("jiemi, aes解密之后: " + decrypted);

    }

    /**
     * @param rawKey   密钥
     * @param clearPwd 明文字符串
     * @return 密文字节数组
     */
    public static String encrypt(byte[] rawKey, String clearPwd) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
//            Cipher cipher = Cipher.getInstance("AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encypted = cipher.doFinal(clearPwd.getBytes());
            return Base64Utils.encodeByte(encypted);
//            return encypted;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param encrypted 密文字节数组
     * @param rawKey    密钥
     * @return 解密后的字符串
     */
    public static String decrypt(byte[] encrypted, byte[] rawKey) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
//            Cipher cipher = Cipher.getInstance("AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            return ""+ e.getMessage();
        }
    }

    /**
     * @param seed 种子数据
     * @return 密钥数据
     */
    public static byte[] getRawKey(byte[] seed) {
        byte[] rawKey = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed);
            // AES加密数据块分组长度必须为128比特，密钥长度可以是128比特、192比特、256比特中的任意一个
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            rawKey = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
        }
        return rawKey;
    }

}
