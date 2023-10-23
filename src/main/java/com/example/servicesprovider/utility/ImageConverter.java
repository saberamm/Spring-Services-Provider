package com.example.servicesprovider.utility;

import com.example.servicesprovider.exception.ImageSizeNotValidException;
import com.example.servicesprovider.exception.InvalidImageTypeException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageConverter {

    public byte[] readFileToBytes(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return getBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file: " + e.getMessage());
        }
    }

    public byte[] readFileToBytes(File file) {
        try {
            Path path = file.toPath();
            return getBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file: " + e.getMessage());
        }
    }

    public void saveBytesToFile(byte[] bytes, String folderPath, String fileName) {
        try {
            FileOutputStream outputStream = new FileOutputStream(folderPath + File.separator + fileName);

            outputStream.write(bytes);

            outputStream.close();

            System.out.println("File saved to: " + folderPath + File.separator + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image file: " + e.getMessage());
        }
    }

    private byte[] getBytes(Path path) throws IOException {
        long fileSize = Files.size(path);

        if (fileSize > 300 * 1024) {
            throw new ImageSizeNotValidException("File size exceeds 300 KB");
        }

        String mimeType = Files.probeContentType(path);
        if (mimeType == null || !mimeType.equals("image/jpeg")) {
            throw new InvalidImageTypeException("File is not a valid JPG image");
        }

        return Files.readAllBytes(path);
    }
}