package kr.or.ddit.crypto;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * encode/decode
 * 	encoding(부호화) : 전송이나 저장을 위해 매체(media)가 이해할 수 있는 방식으로 데이터의 표현 방식을 바꾸는 작업.
 * 						ex) Base64, URLEncoding(percent encoding)
 * encrypt/decrypt
 * 	encrypting(암호화) : 권한(key) 없는 사용자가 snipping 하거나 spooping 하는 걸 막기 위해 데이터 표현을 바꾸는 작업.
 *	- 단방향 암호화(hash 함수) : 암호화된 이후 평문 복원이 불가능한 방식(비밀번호 암호화에 활용).
 *						  : 다양한 길이의 입력 데이터에 만들어지는 결과 데이터가 길이가 동일한 경우.
 *							크기 작을 떄 해시값이 충돌될 수 있음 -> 길이 늘려야 함(그래서 비밀번호 제한 걸리는 것)
 *			ex) SHA-512 (숫자 : 암호문의 길이 512비트 = 64바이트, 평문의 길이와 상관 없이)
 *	- 양방향 암호화 : 암호문에서 원래 평문으로 복호화가 가능한 방식
 *		- 대칭키 방식 : 하나의 비밀키로 암호화와 복호화에 모두 사용(ebook-이북 리더 계정으로 구매자만 볼 수 있도록 키 존재).
 *				ex) AES(128, 256)
 *		- 비대칭키 방식 : 공개키와 개인키, 한 쌍의 키로 암호화와 복호화에 다른 키를 사용하는 방식(전자서명,지문,otp 등, 내가 암호화를 한 당사자라는 것을 개인키로 증명).
 *				ex) RSA(2048)
 *
 */
public class EncryptionDesc {
	public static void main(String[] args)throws Exception {
		String plain = "java";
//		encryptAESTest(plain);
		
		KeyPairGenerator pairGen = KeyPairGenerator.getInstance("RSA");
		pairGen.initialize(2048);
		KeyPair keyPair = pairGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] input = plain.getBytes();
		byte[] encrypted = cipher.doFinal(input);
		
		String encoded = Base64.getEncoder().encodeToString(encrypted);
		System.out.println(encoded);
		
		byte[] decoded = Base64.getDecoder().decode(encoded);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decrypted = cipher.doFinal(decoded);
		System.out.println(new String(decrypted));
	}

	private static void encryptAESTest(String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String ivValueText = "초기화벡터";
		
		//해시함수 사용
		MessageDigest md = MessageDigest.getInstance("MD5"); //128bit
		byte[] iv = md.digest(ivValueText.getBytes()); //암호와와 복호화에 둘 다 사용
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  //JCA
		KeyGenerator keyGen = KeyGenerator.getInstance("AES"); // AES : 블럭 암호화 방식
//		keyGen.init(128);
		keyGen.init(256); //JDK에 제약 걸려있으면 예외 발생
		SecretKey key = keyGen.generateKey();
		//cipher암호화에 쓸 건지 복호화에 쓸 건지 결정
		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		
		byte[] input = plain.getBytes();
		byte[] encrypted = cipher.doFinal(input);
		String encoded = Base64.getEncoder().encodeToString(encrypted);
		
		System.out.println(encoded);
		
		byte[] decoded = Base64.getDecoder().decode(encoded); //=encrypted
		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec); //대칭키이므로 같은 키 사용
		byte[] decrypted = cipher.doFinal(decoded); //=input
		System.out.println(new String(decrypted));
	}
	
	private static String encrptSha512(String plain) {
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] input = plain.getBytes();
			//암호화의 기본데이터는 바이트
			byte[] encrypted = md.digest(input);
			//encrypted.length = 64
			//Base64 쓰면 데이터 1.3배 되므로 DB저장 크기는 64*1.3
			//db에 넣을 수 있도록 인코딩, Base64 : 이진데이터 문자화할 때 제일 많이 쓰는방식
			String encoded = Base64.getEncoder().encodeToString(encrypted);
			//SHA-512 : 단방향이므로 평문 찾을 수 없다.
			return encoded;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public static void encodeTest() throws UnsupportedEncodingException {
		String plain = "원문데이터";
		
		String base64Encoded = Base64.getEncoder().encodeToString(plain.getBytes());
		System.out.println(base64Encoded);
		System.out.println(new String(Base64.getDecoder().decode(base64Encoded))); //바이트배열 다시 모을 수 있다.
		
		String urlEncoded = URLEncoder.encode(plain,"UTF-8");
		System.out.println(urlEncoded);
		System.out.println(URLDecoder.decode(urlEncoded,"UTF-8"));
	}

}
