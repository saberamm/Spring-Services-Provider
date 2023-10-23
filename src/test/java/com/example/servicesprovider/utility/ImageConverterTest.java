package com.example.servicesprovider.utility;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class ImageConverterTest {

    @Test
    public void testReadFileToBytes() {
        ImageConverter imageConverter = new ImageConverter();

        String filePath = "C:\\Users\\Administrator\\Desktop\\Temp\\sss.jpg";
        byte[] bytes = imageConverter.readFileToBytes(filePath);

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    @Test
    public void testReadFileToBytesWithFile() throws FileNotFoundException {
        ImageConverter imageConverter = new ImageConverter();

        File testImageFile = ResourceUtils.getFile("C:\\Users\\Administrator\\Desktop\\Temp\\sss.jpg");
        byte[] bytes = imageConverter.readFileToBytes(testImageFile);

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    @Test
    public void testSaveBytesToFile() {
        ImageConverter imageConverter = new ImageConverter();

        byte[] testData = new byte[]{1, 2, 3, 4, 5};
        String folderPath = "path_to_test_folder";
        String fileName = "test_output.jpg";

        try {
            imageConverter.saveBytesToFile(testData, folderPath, fileName);
        } catch (Exception e) {
            fail("An exception occurred during the test: " + e.getMessage());
        }
    }
}

