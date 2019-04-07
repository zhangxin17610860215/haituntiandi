package haituntiandi.com.haituntiandi.utils.encryptionutils;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * 前后端数据传输加密工具类
 * 
 * @author monkey
 *
 */
public class AesUtils {

	//length用户要求产生字符串的长度
//    获取16位随机字符串
	public static String getRandomString(int length){
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			int number=random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	// 参数分别代表 算法名称/加密模式/数据填充方式
	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

	/**
	 * 加密
	 * 
	 * @param content
	 *            加密的字符串
	 * @param encryptKey
	 *            key值
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
		byte[] b = cipher.doFinal(content.getBytes("utf-8"));
		// 采用base64算法进行转码,避免出现中文乱码
//		return Base64.encodeBase64String(b);
		return Base64Util.encodeMessage(b);

	}

	/**
	 * 解密
	 * 
	 * @param encryptStr
	 *            解密的字符串
	 * @param decryptKey
	 *            解密的key值
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptStr, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
		// 采用base64算法进行转码,避免出现中文乱码
//		byte[] encryptBytes = Base64.decodeBase64(encryptStr);
		byte[] encryptBytes = Base64Util.decodeMessage(encryptStr);
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return new String(decryptBytes);
	}
}