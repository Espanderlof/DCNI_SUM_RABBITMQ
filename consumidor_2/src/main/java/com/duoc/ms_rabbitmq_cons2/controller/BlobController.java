package com.duoc.ms_rabbitmq_cons2.controller;

import com.duoc.ms_rabbitmq_cons2.dto.BlobFileDTO;
import com.duoc.ms_rabbitmq_cons2.service.AzureStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blobs")
@CrossOrigin(origins = "*")
public class BlobController {

    @Autowired
    private AzureStorageService azureStorageService;

    @GetMapping("/list")
    public ResponseEntity<List<BlobFileDTO>> listFiles() {
        return ResponseEntity.ok(azureStorageService.listFiles());
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        byte[] content = azureStorageService.downloadFile(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileName) {
        azureStorageService.deleteFile(fileName);
        return ResponseEntity.ok().build();
    }
}