package com.duoc.ms_rabbitmq_cons2.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import com.duoc.ms_rabbitmq_cons2.dto.BlobFileDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public List<BlobFileDTO> listFiles() {
        List<BlobFileDTO> files = new ArrayList<>();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        
        PagedIterable<BlobItem> blobs = containerClient.listBlobs();
        for (BlobItem blob : blobs) {
            BlobFileDTO dto = new BlobFileDTO();
            dto.setName(blob.getName());
            dto.setContentType(blob.getProperties().getContentType());
            dto.setSize(blob.getProperties().getContentLength());
            dto.setLastModified(blob.getProperties().getLastModified());
            dto.setUrl(containerClient.getBlobClient(blob.getName()).getBlobUrl());
            files.add(dto);
        }
        return files;
    }

    public byte[] downloadFile(String fileName) {
        try {
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            
            if (!blobClient.exists()) {
                throw new RuntimeException("El archivo no existe");
            }

            return blobClient.downloadContent().toBytes();
        } catch (Exception e) {
            logger.error("Error al descargar el archivo: {}", e.getMessage(), e);
            throw new RuntimeException("Error al descargar el archivo", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            
            if (!blobClient.exists()) {
                throw new RuntimeException("El archivo no existe");
            }

            blobClient.delete();
            logger.info("Archivo {} eliminado exitosamente", fileName);
        } catch (Exception e) {
            logger.error("Error al eliminar el archivo: {}", e.getMessage(), e);
            throw new RuntimeException("Error al eliminar el archivo", e);
        }
    }
}