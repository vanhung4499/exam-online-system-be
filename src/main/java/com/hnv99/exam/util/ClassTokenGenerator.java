package com.hnv99.exam.util;

import java.security.SecureRandom;

import java.security.SecureRandom;

public class ClassTokenGenerator {

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Generates a class token with the specified length.
     *
     * @param length the length of the token to be generated
     * @return a randomly generated class token as a String
     */
    public static String generateClassToken(int length) {
        // Create a secure random number generator
        SecureRandom random = new SecureRandom();

        // StringBuilder to store the token
        StringBuilder tokenBuilder = new StringBuilder(length);

        // Generate the token
        for (int i = 0; i < length; i++) {
            // Select a random character from CHAR_POOL and append it to the token
            tokenBuilder.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }

        // Return the generated token
        return tokenBuilder.toString();
    }
}
