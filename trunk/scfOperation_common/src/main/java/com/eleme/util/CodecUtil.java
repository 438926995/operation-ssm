package com.eleme.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * Codec utils
 * 
 * @author penglau
 */
public class CodecUtil {

	/**
	 * log实例
	 */
	private static final Log log = LogFactory.getLog(CodecUtil.class);

	/**
	 * @return an UUID String
	 */
	public static String UUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * BASE64转换为字节数组
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return org.apache.commons.codec.binary.Base64.decodeBase64(key.getBytes());

	}

	/**
	 * Encode a String to base64
	 * 
	 * @param value
	 *            The plain String
	 * @return The base64 encoded String
	 */
	public static String encodeBASE64(String value) {
		try {
			return new String(Base64.encodeBase64(value.getBytes("utf-8")));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Encode binary data to base64
	 * 
	 * @param value
	 *            The binary data
	 * @return The base64 encoded String
	 */
	public static String encodeBASE64(byte[] value) {
		return new String(Base64.encodeBase64(value));
	}

	/**
	 * Decode a base64 value
	 * 
	 * @param value
	 *            The base64 encoded String
	 * @return decoded binary data
	 */
	public static String decodeBASE64(byte[] value) {
		return new String(Base64.encodeBase64(value));
	}

	public static String decodeBASE64(String value) {
		try {
			return new String(Base64.decodeBase64(value.getBytes("utf-8")));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Build an hexadecimal MD5 hash for a String
	 * 
	 * @param value
	 *            The String to hash
	 * @return An hexadecimal Hash
	 */
	public static String hexMD5(String value) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(value.getBytes("utf-8"));
			byte[] digest = messageDigest.digest();
			return byteToHexString(digest);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Build an hexadecimal MD5 hash for a String
	 * 
	 * @param value
	 *            The String to hash
	 * @return An hexadecimal Hash
	 */
	public static String hexMD5(String value, String charsetName) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(value.getBytes(charsetName));
			byte[] digest = messageDigest.digest();
			return byteToHexString(digest);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Build an hexadecimal SHA1 hash for a String
	 * 
	 * @param value
	 *            The String to hash
	 * @return An hexadecimal Hash
	 */
	public static String hexSHA1(String value) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-1");
			md.update(value.getBytes("utf-8"));
			byte[] digest = md.digest();
			return byteToHexString(digest);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Write a byte array as hexadecimal String.
	 */
	public static String byteToHexString(byte[] bytes) {
		return String.valueOf(Hex.encodeHex(bytes));
	}

	/**
	 * Transform an hexadecimal String to a byte array.
	 */
	public static byte[] hexStringToByte(String hexString) {
		try {
			return Hex.decodeHex(hexString.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取文件的MD5值，返回若为空，表示获取异常
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getFileMd5(byte[] bytes) {
		String fileMD5 = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(bytes);
			byte[] bs = messageDigest.digest();
			// 转换成十六制字符串，形成最终密文
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < bs.length; i++) { // 字节数组转换成十六进制字符串，形成最终的密文
				int v = bs[i] & 0xff;
				if (v < 16) {
					sb.append(0);
				}
				sb.append(Integer.toHexString(v));
			}
			fileMD5 = String.valueOf(sb);
		} catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(e));
		}
		return fileMD5;

	}
}
