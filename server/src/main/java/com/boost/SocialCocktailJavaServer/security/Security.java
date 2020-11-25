package com.boost.SocialCocktailJavaServer.security;

import org.apache.commons.codec.digest.DigestUtils;

public final class Security {
    private static final int HashedPasswordLength = 32;

    public static String hash(String password) {
        StringBuilder builder = new StringBuilder();
        byte[] byteDigest = DigestUtils.getSha3_512Digest().digest(password.getBytes());

        for (byte b : byteDigest) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }
}
