package com.duoc.ms_rabbitmq_cons2.dto;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class BlobFileDTO {
    private String name;
    private String url;
    private String contentType;
    private Long size;
    private OffsetDateTime lastModified;
}