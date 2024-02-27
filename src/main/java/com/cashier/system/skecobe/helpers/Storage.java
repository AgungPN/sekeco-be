package com.cashier.system.skecobe.helpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
@NoArgsConstructor
public class Storage {
    private static final String baseDirectory = "src/main/resources/static/storages/";

    public static String saveFileToStorage(MultipartFile file, String uploadedDirectory) throws IOException {
        // https://medium.com/@kkarththi15/saving-images-locally-in-a-spring-boot-web-application-01405a988bc7
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String directory = baseDirectory + uploadedDirectory;

        Path folder = Path.of(directory);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        Path filePath = Path.of(directory + "/" + uniqueFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public static String getFileUrl(String fileName, String directory) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return baseUrl + "/static/storages/" + directory + "/" + fileName;
    }

    public byte[] getFile(String fileName, String directory) throws IOException {
        Path filePath = Path.of(baseDirectory + directory, fileName);

        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            return null; // Handle missing images
        }
    }

    public boolean deleteFile(String fileName, String directory) throws IOException {
        Path imagePath = Path.of(baseDirectory + directory, fileName);

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            return true;
        } else {
            return false;
        }
    }
}
