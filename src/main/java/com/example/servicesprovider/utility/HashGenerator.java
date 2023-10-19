package com.example.servicesprovider.utility;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashGenerator {

    public String generateSHA512Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

            byte[] hashBytes = md.digest(inputBytes);

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xFF & hashByte);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean areHashesEqual(String hash1, String hash2) {
        byte[] hash1Bytes = hexStringToByteArray(hash1);
        byte[] hash2Bytes = hexStringToByteArray(hash2);

        return MessageDigest.isEqual(hash1Bytes, hash2Bytes);
    }

    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] byteArray = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }
}