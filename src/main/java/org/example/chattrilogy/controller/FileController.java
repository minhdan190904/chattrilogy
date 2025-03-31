package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.dto.ResUpLoadFileDTO;
import org.example.chattrilogy.service.FileService;
import org.example.chattrilogy.util.error.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Value("${minhdan.upload-file.base-uri}")
    private String baseURI;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResUpLoadFileDTO> uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder
    ) throws URISyntaxException, IOException, StorageException {
        //validate
        if(file == null || file.isEmpty()){
            throw new StorageException("File is empty");
        }

        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "bmp", "mp4", "mkv", "mov", "avi", "wmv", "flv", "webm", "3gp");
        boolean invalid = allowedExtensions.stream().anyMatch(fileExtension -> fileName.toLowerCase().endsWith(fileExtension));
        if(!invalid){
            throw new StorageException("Invalid file format");
        }

        //create a directory if not exist
        fileService.createUploadFolder(baseURI + folder);

        //store file
        String upLoadFileName = fileService.store(file, folder);

        String filePath = folder + "/" + upLoadFileName;

        //create instance
        ResUpLoadFileDTO dto = new ResUpLoadFileDTO(filePath, Instant.now().toString());

        System.out.println("Uploaded file: " + filePath);

        return ResponseEntity.ok(dto);
    }

}
