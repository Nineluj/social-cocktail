package com.boost.SocialCocktailJavaServer.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class to have a baseline level of security
 */
public final class Security {
    private static final String algorithm = "SHA-256";

    // Limit the length of the password hashes so that they don't get too angry
    private static final int HashedPasswordLength = 32;

    // The length of the salts generated
    private static final int SaltLength = HashedPasswordLength;

    /**
     * Hashes the given password with the salt
     * @return Hexadecimal string of 32 characters
     */
    public static String hash(String password, String salt) {
        MessageDigest md = Security.getDigest();
        byte[] byteDigest = md.digest((salt + password).getBytes());

        return toHex(byteDigest, HashedPasswordLength);
    }

    private static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException algorithmException) {
            throw new IllegalArgumentException(String.format("Can't use %s", Security.algorithm));
        }
    }

    /**
     * Returns a hexadecimal string representing the byte array of size up to limit
     */
    private static String toHex(byte[] byteArr, int limit) {
        StringBuilder builder = new StringBuilder();

        for (byte b : byteArr) {
            builder.append(String.format("%02x", b));

            if (builder.length() >= limit) {
                return builder.toString();
            }
        }

        return builder.toString();
    }

    /**
     * Convenience overload
     */
    private static String toHex(byte[] byteArr) {
        return toHex(byteArr, byteArr.length);
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[SaltLength];
        random.nextBytes(saltBytes);
        return toHex(saltBytes);
    }
}
