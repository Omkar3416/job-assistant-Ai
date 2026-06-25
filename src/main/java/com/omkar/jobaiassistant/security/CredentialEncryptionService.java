package com.omkar.jobaiassistant.security;

public interface CredentialEncryptionService {

    String encrypt(
            String plainText
    );

    String decrypt(
            String cipherText
    );
}