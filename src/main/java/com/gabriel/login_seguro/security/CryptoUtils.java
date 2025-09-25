package com.gabriel.login_seguro.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class CryptoUtils {

    private static final String ALGORITHM = "AES";

    @Value("${app.encryption.key}")
    private String encryptionKey;

    public String encrypt(String data) {
        if (data == null) return null;
        try {
            Key key = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(c.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar", e);
        }
    }

    public String decrypt(String encryptedData) {
        if (encryptedData == null) return null;
        try {
            Key key = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.getDecoder().decode(encryptedData)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar", e);
        }
    }
}