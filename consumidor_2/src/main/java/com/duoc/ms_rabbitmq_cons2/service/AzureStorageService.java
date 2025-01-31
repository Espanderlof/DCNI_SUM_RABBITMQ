package com.duoc.ms_rabbitmq_cons2.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AzureStorageService {
    private static final Logger logger = LoggerFactory.getLogger(AzureStorageService.class);

    @Autowired
    private BlobServiceClient blobServiceClient;

    @Value("${azure.storage.container-name}")
    private String containerName;

    public void saveJsonToStorage(String jsonContent) {
        try {
            // Obtener o crear el contenedor
            BlobContainerClient containerClient = blobServiceClient.createBlobContainerIfNotExists(containerName);
            
            // Generar nombre del archivo con timestamp
            String fileName = generateFileName();
            
            // Obtener referencia al blob
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            
            // Subir el contenido
            blobClient.upload(new ByteArrayInputStream(jsonContent.getBytes()), jsonContent.length());
            
            logger.info("Archivo {} guardado exitosamente en Azure Blob Storage", fileName);
        } catch (Exception e) {
            logger.error("Error al guardar archivo en Azure Blob Storage: {}", e.getMessage(), e);
            throw new RuntimeException("Error al guardar en Azure Storage", e);
        }
    }

    private String generateFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "resumen_signos_vitales_" + now.format(formatter) + ".json";
    }
}