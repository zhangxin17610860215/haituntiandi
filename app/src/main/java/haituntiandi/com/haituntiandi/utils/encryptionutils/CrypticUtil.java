package haituntiandi.com.haituntiandi.utils.encryptionutils;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @ClassName: CrypticUtil
 * @Description:加/解密工具类
 * @author lsc
 *
 */
public class CrypticUtil {

//	public static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcOCgBt7mAT/b7iawpmnMvGuO1RfmC4tQFfVUGfs2tumMLVXjpmBc/o7kE91b3DyPHD+EOHST9YMiokl9lpvLoEfgVOF7fIgpuZxkVMDcaniopBV+vFAp/k/W+TbMfXRPpLZHnxEaGRTFC3f9TSg0jAdCXJ2HKp9H/dIEeI7vQiQIDAQAB";
//
//	public static String privateKeyString = "MIICxjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQI/U3ZNedFXJgCAggAMBQGCCqGSIb3DQMHBAiskf+W4O/dmASCAoBzmPULaORA4f5if3F88VNXt4GK2bYouX1P+69qBQR7cIfYVqeJ3qZS7aiuQwX9UO4i8F/B1ZJqZEkWiHGl6Rm+PqoTNrLjiKLJvhNF406rB82vmkZKjDMF+rJWDsb7mev05bR7oHagmq8M8R6cWs2iDFyQygiCjAQ09mgaDdu9OMOGvej2+Di13+jeQaS0DaZXElnQKli8DlPnkThrUMXPW5DJcWaXA2op67S4hu3fU9FxzAlBduhcSQgtICY9xEQ9jo2tlW8aWomxCdYA0dDpGxXXKhPgos6aXUQvrAN7HKZG9x7d/dL1N8hLdDwTwBkDsDgtCkFFZOB1tOugGQlBkbWn2eAlXfMxQfJcNkyWPJf0th2GdNIU4JtVhDac2A4jzxUGYXGeICYkZDh/wjFbOMJhzl/ED+QSjXYvimqlFHjt/grZkjKWLfcftgF01/fc/6ZAW4Cj1Fj3B645ED5vGhWVPlY6y5lNLaAD50cTezWEmfWUvTjIXDfg9Y1d++bmqp//UCNlZnWlJ5KHsAVCrn+Iv3uALSsJuW2Agsc23eYyI9t71v5e6a7uxz9hIzbIn6BwncByzGGwLkriH5ZNe0UTVe2VR1gbVJZ6DrsuBYxhS/jrko4QDax4T3VdrAHtVY+7Tnmqci/sQTlYJLToT2Qva1CXd660Y13wcgryMXBtHqvlsj4zSAx9nEz1ypScmeW0IQRuLDsbCu6YOqiOtFqLrEjECwUPHsfLNfhaoCtYsN8IWH0K/vV4s9cj4DnUcqC9h//JVyVdFrEC2FP2XE9Sv3PA76tmLwEqlBg1t9dU4fF5SGuaKelE2+vPcxZrbo5x/ZbXciKIiUX0J82B";

	public static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIV2dOPjaaTV88WrYE7TmBJSgtuKJGtPlaIm9vW4WOf9HgULAFpdd25J6AOqulO1J1FJsJbCWckCHavbWykKia5Ao+xeJ8IP4QXvdTa98B8zapooenI+OwqBUMmY7T/mC/QXoLZPgigvoK9myUCmIcmT3E9l6RHTbk10PviwLF+wIDAQAB";

	public static String privateKeyString = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAKE7COIk7V+lwfG38dkQ1Gen0EmnA1cLWSyhgg/IaA+o6H7bWxJ02M9jHhwnQVF4S7ND6+FA5E2urPcfl3yCpLUweU0eRKMaRMZu1Tn9J7FBP4qFRzPN+8wQNtk39UEYa4Ao1wd19XJb0GtwluPwcSAIiCj/VFm/RN/sli9YJKrlAgMBAAECgYEAljx4TqGyPwop6p9I0MeJ5PzObZMe3aFJevkURLitnsn5uxuThWM0FCt8ByfdCZlakLSdu8vH8UrvCWd6tR7XcjPwNVqzlHAqFUljPG1nKWpXSy1UGiCHikOTRcnY7lzV1q0rGjPjhX6aELwHIS8R0MIubocgshb9nUJQ/FOtEYECQQDQ4Bg8hn3LhsSX/udD8dI6udYbGImFGV5gJlej0sqBSZZnuc84pEY1ZEaW66sY2z9sfZfcZyCBNJw0cfQLe56tAkEAxZslcWAL1nhc4D3xZfD5w7K0Ryxexl7jFeh1P9RbKr31v24LheHxeCK8Tv8XafWOsmnXQSVs7F7BYWPn+bxcGQJBAIfqWjsANNj7/+770lbXAzptdJG7HLk8MboVGRAj+Nu6G6GxOdMcqgbSElDgVzxcZl/dNbUYcgFBT6ecwvW54h0CQQCWYRVDjIY1tu9nL3yPya0pgsuzBk8xw3ApcfgYlTCz/FASWFqojmPGuxh3PaXB2z+3AtjvGN4lm46q4O7enLuxAkEAs0qyH+LSFFnLV9VWp15puTmmaBZPtouQ3GY6X7CUKnZ2xSaal1w7lHnr4MSFHPJjXRKN9d0wYI99hBjv97WKNA==";

	public static final String XML_ENCODE_UTF8 = "UTF-8";

	public static final String XML_ENCODE_GBK = "GBK";

	/**
	 * RSA加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String RSAEncrypt(String key) throws Exception {
		// 获取公钥
		PublicKey publicKey = getPublicKey(publicKeyString);
		// 公钥加密
		return encrypt1(key.getBytes(), publicKey);
	}

	/**
	 * RSA解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String RSADecrypt(String key) throws Exception {
		// 获取私钥
		PrivateKey privateKey = getPrivateKey(privateKeyString);
		byte[] decryptedBytes2 = decrypt1(key, privateKey);
		return new String(decryptedBytes2);
	}

	/**
	 * 对字符串进行SHA1加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getSha1(String str) {
		if (null == str || 0 == str.length()) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param text
	 *            明文
	 * @return 密文
	 */
	public static String md5(String text) {
		if (null == text || 0 == text.length()) {
			return null;
		}
		MessageDigest msgDigest = null;

		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}

		msgDigest.update(text.getBytes());

		byte[] bytes = msgDigest.digest();

		byte tb;
		char low;
		char high;
		char tmpChar;

		String md5Str = new String();

		for (int i = 0; i < bytes.length; i++) {
			tb = bytes[i];

			tmpChar = (char) ((tb >>> 4) & 0x000f);

			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}

			md5Str += high;
			tmpChar = (char) (tb & 0x000f);

			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}

			md5Str += low;
		}

		return md5Str;
	}

	/**
	 * @param text
	 *            明文
	 * @param salt
	 *            盐值
	 * @return 加盐加密后结果
	 */
	public static String md5(String text, Object salt) {
		StringBuffer sb = new StringBuffer();
		sb.append(text == null ? "" : text);
		if (null != salt && !"".equals(salt))
			sb.append("{" + salt.toString() + "}");
		return md5(sb.toString());
	}

	// 将base64编码后的公钥字符串转成PublicKey实例
	private static PublicKey getPublicKey(String publicKey) throws Exception {
		byte[] keyBytes = Base64Util.decodeMessage(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	// 将base64编码后的私钥字符串转成PrivateKey实例
	private static PrivateKey getPrivateKey(String privateKey) throws Exception {
		byte[] keyBytes = Base64Util.decodeMessage(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	// 公钥加密之后用base64编码
	private static String encrypt1(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return Base64Util.encodeMessage(cipher.doFinal(content));
	}

	// 先把内容base64解码然后私钥解密
	private static byte[] decrypt1(String content, PrivateKey privateKey) throws Exception {
		byte[] c = Base64Util.decodeMessage(content);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(c);
	}
}
