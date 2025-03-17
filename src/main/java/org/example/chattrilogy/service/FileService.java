package org.example.chattrilogy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    public void createUploadFolder(String folder) throws URISyntaxException {
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File tmpDir = path.toFile();

        if (!tmpDir.isDirectory()) {
            try {
                Files.createDirectory(tmpDir.toPath());
                System.out.println(">>> CREATE NEW DIRECTORY SUCCESSFUL, PATH = " + folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
        }
    }

    @Value("${hoidanit.upload-file.base-uri}")
    private String baseURI;

    public String store(MultipartFile file, String folder) throws URISyntaxException, IOException {
        String originalFileName = file.getOriginalFilename();
        String safeFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8)
                .replace("+", "%20");

        String finalName = System.currentTimeMillis() + "-" + safeFileName;
        URI uri = new URI(baseURI + folder + "/" + finalName);
        Path path = Paths.get(uri);
        System.out.println(">>> STORE FILE SUCCESSFUL, PATH = " + path);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        return finalName;
    }



}
