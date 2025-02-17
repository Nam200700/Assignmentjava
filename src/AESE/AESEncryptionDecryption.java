package AESE;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ACER
 */
public class AESEncryptionDecryption {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public void prepareSecretKey(String myKey) {
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error initializing secret key: " + e.getMessage());
        }
    }

    public String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecretKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.err.println("Error while encrypting: " + e.getMessage());
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecretKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Error while decrypting: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        final String secretKey = "mySecretKey123"; // Khóa bí mật của bạn

        AESEncryptionDecryption aes = new AESEncryptionDecryption();

        // Thông tin kết nối gốc
        String jdbcUrl = "jdbc:mysql://localhost:3306/qlsv";
        String user = "root";
        String password = "tranhainam123";

        // Mã hóa thông tin
        String encryptedJdbcUrl = aes.encrypt(jdbcUrl, secretKey);
        String encryptedUser = aes.encrypt(user, secretKey);
        String encryptedPassword = aes.encrypt(password, secretKey);

        System.out.println("Encrypted JDBC URL: " + encryptedJdbcUrl);
        System.out.println("Encrypted User: " + encryptedUser);
        System.out.println("Encrypted Password: " + encryptedPassword);

        // Giải mã để kiểm tra
        String decryptedJdbcUrl = aes.decrypt(encryptedJdbcUrl, secretKey);
        String decryptedUser = aes.decrypt(encryptedUser, secretKey);
        String decryptedPassword = aes.decrypt(encryptedPassword, secretKey);

        System.out.println("Decrypted JDBC URL: " + decryptedJdbcUrl);
        System.out.println("Decrypted User: " + decryptedUser);
        System.out.println("Decrypted Password: " + decryptedPassword);
    }

}
