package com.boost.SocialCocktailJavaServer.security;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

/**
 * Class to have a baseline level of security
 */
public final class Security {
    // The length of the salts generated
    private static final int SaltLength = 16;

    // Limit the length of the password hashes so that they don't get too angry
    private static final int HashedPasswordLength = 32;

    /**
     * Hashes the given password with the salt
     * @return Hexadecimal string of 32 characters
     */
    public static String hash(String password, String salt) {
        byte[] byteDigest = DigestUtils.getSha3_512Digest().digest((salt + password).getBytes());

        return toHex(byteDigest, HashedPasswordLength);
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
