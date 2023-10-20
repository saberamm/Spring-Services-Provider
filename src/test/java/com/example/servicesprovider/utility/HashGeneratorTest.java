package com.example.servicesprovider.utility;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashGeneratorTest {

    @Test
    public void testGenerateSHA512Hash() {
        HashGenerator hashGenerator = new HashGenerator();
        String input = "TestString";

        String hash = hashGenerator.generateSHA512Hash(input);

        assertNotNull(hash);
        assertEquals(128, hash.length());
    }

    @Test
    public void testAreHashesEqual() {
        HashGenerator hashGenerator = new HashGenerator();
        String input = "TestString";

        String hash1 = hashGenerator.generateSHA512Hash(input);
        String hash2 = hashGenerator.generateSHA512Hash(input);

        assertTrue(hashGenerator.areHashesEqual(hash1, hash2));
    }

    @Test
    public void testAreHashesNotEqual() {
        HashGenerator hashGenerator = new HashGenerator();
        String input1 = "TestString1";
        String input2 = "TestString2";

        String hash1 = hashGenerator.generateSHA512Hash(input1);
        String hash2 = hashGenerator.generateSHA512Hash(input2);

        assertFalse(hashGenerator.areHashesEqual(hash1, hash2));
    }
}
