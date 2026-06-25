package com.omkar.jobaiassistant.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class CredentialEncryptionServiceImpl
        implements CredentialEncryptionService {

    @Value("${automation.encryption-key}")
    private String encryptionKey;

    @Override
    public String encrypt(
            String plainText
    ) {

        try {

            System.out.println(
                    "ENCRYPTION KEY = " +
                            encryptionKey
            );

            byte[] keyBytes =
                    Base64.getDecoder()
                            .decode(encryptionKey);

            SecretKeySpec secretKey =
                    new SecretKeySpec(
                            keyBytes,
                            "AES"
                    );

            Cipher cipher =
                    Cipher.getInstance(
                            "AES"
                    );

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey
            );

            byte[] encrypted =
                    cipher.doFinal(
                            plainText.getBytes()
                    );

            return Base64.getEncoder()
                    .encodeToString(
                            encrypted
                    );

        } catch (Exception ex) {

            ex.printStackTrace();

            throw new RuntimeException(
                    "Encryption failed",
                    ex
            );
        }
    }

    @Override
    public String decrypt(
            String cipherText
    ) {

        try {

            byte[] keyBytes =
                    Base64.getDecoder()
                            .decode(encryptionKey);

            SecretKeySpec secretKey =
                    new SecretKeySpec(
                            keyBytes,
                            "AES"
                    );

            Cipher cipher =
                    Cipher.getInstance(
                            "AES"
                    );

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey
            );

            byte[] decoded =
                    Base64.getDecoder()
                            .decode(
                                    cipherText
                            );

            return new String(
                    cipher.doFinal(
                            decoded
                    )
            );

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Decryption failed",
                    ex
            );
        }
    }
}