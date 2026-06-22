package com.omkar.jobaiassistant.dto;

import java.time.LocalDateTime;

public class ResumeResponseDto {

    private Long id;

    private String fileName;

    private String fileType;

    private LocalDateTime uploadedAt;

    private String message;

    private String extractedText;

    private String aiSummary;

    private String storageMode;

    public ResumeResponseDto() {
    }

    public String getExtractedText() {
        return extractedText;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public String getStorageMode() {
        return storageMode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public void setStorageMode(String storageMode) {
        this.storageMode = storageMode;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

}