package com.open.abddf.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public final class Security {

	String characterEncoding = "UTF-8";
	String cipherTransformation = "AES/CBC/PKCS5PADDING";
	String aesEncryptionAlgorithem = "AES";
	String key1 = "ABCDEFGH";
	String key2 = "IJKLMNOP";

	public Security(){}

	public static void main(String[] args){
		String password = "India@123";
		Security security = new Security();
		String encryptPasswordValue = security.encryptPassword(password);
		System.out.println("Encrypt Password: " + encryptPasswordValue);
	}
	public String encryptPassword(String plainText){
		String encryptedText = "";
		try{
			String encryptionkey = key1 + key2;
			Cipher cipher = Cipher.getInstance(cipherTransformation);
			byte[] key = encryptionkey.getBytes(characterEncoding);
			SecretKeySpec  secretkey  = new SecretKeySpec(key, aesEncryptionAlgorithem);
			IvParameterSpec ivparameterspec = new IvParameterSpec(key);
			cipher.init(Cipher.ENCRYPT_MODE, secretkey, ivparameterspec);
			byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
			Base64.Encoder encoder = Base64.getEncoder();
			encryptedText = encoder.encodeToString(cipherText);
			return encryptedText;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}

	}

	public String decryptPassword(String encryptedText){
		String decryptedText = "";
		try{
			String encryptionkey = key1 + key2;
			Cipher cipher = Cipher.getInstance(cipherTransformation);
			byte[] key = encryptionkey.getBytes(characterEncoding);
			SecretKeySpec  secretkey  = new SecretKeySpec(key, aesEncryptionAlgorithem);
			IvParameterSpec ivparameterspec = new IvParameterSpec(key);
			cipher.init(Cipher.DECRYPT_MODE, secretkey, ivparameterspec);
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF-8"));
			decryptedText = new String(cipher.doFinal(cipherText));
			return decryptedText;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
}
