package com.example.servicesprovider.utility;

import com.example.servicesprovider.exception.ImageSizeNotValidException;
import com.example.servicesprovider.exception.InvalidImageTypeException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageConverter {

    public static byte[] readFileToBytes(String filePath) {
        try {
            Path path = Paths.get(filePath);
            long fileSize = Files.size(path);

            if (fileSize > 300 * 1024) {
                throw new ImageSizeNotValidException("File size exceeds 300 KB");
            }

            String mimeType = Files.probeContentType(path);
            if (mimeType == null || !mimeType.equals("image/jpeg")) {
                throw new InvalidImageTypeException("File is not a valid JPG image");
            }

            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static byte[] readFileToBytes(File file) {
        try {
            Path path = file.toPath();
            long fileSize = Files.size(path);

            if (fileSize > 300 * 1024) {
                throw new ImageSizeNotValidException("File size exceeds 300 KB");
            }

            String mimeType = Files.probeContentType(path);
            if (mimeType == null || !mimeType.equals("image/jpeg")) {
                throw new InvalidImageTypeException("File is not a valid JPG image");
            }

            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static void saveBytesToFile(byte[] bytes, String folderPath, String fileName) {
        try {
            FileOutputStream outputStream = new FileOutputStream(folderPath + File.separator + fileName);

            outputStream.write(bytes);

            outputStream.close();

            System.out.println("File saved to: " + folderPath + File.separator + fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}