package com.omkar.jobaiassistant.service.impl;
import com.omkar.jobaiassistant.service.AiResumeService;

import com.omkar.jobaiassistant.config.FileStorageConfig;
import com.omkar.jobaiassistant.dto.ResumeResponseDto;
import com.omkar.jobaiassistant.entity.Resume;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.ResumeRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.service.ResumeService;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ResumeServiceImpl
        implements ResumeService {

    private final ResumeRepository resumeRepository;

    private final UserRepository userRepository;
    private final AiResumeService aiResumeService;

    public ResumeServiceImpl(
            ResumeRepository resumeRepository,
            UserRepository userRepository,
            AiResumeService aiResumeService
    ) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.aiResumeService = aiResumeService;
    }

    @Override
    public ResumeResponseDto uploadResume(
            MultipartFile file
    ) {

        try {

            Authentication auth =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            String email =
                    auth.getName();

            User user =
                    userRepository
                            .findByEmail(email)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "User not found"
                                    )
                            );

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            File uploadDir =
                    new File(
                            FileStorageConfig.RESUME_UPLOAD_DIR
                    );

            if (!uploadDir.exists()) {

                uploadDir.mkdirs();
            }

            String filePath =
                    uploadDir.getAbsolutePath()
                            + File.separator
                            + fileName;

            File destination =
                    new File(filePath);

            System.out.println(
                    "UPLOAD DIRECTORY = "
                            + uploadDir.getAbsolutePath()
            );

            System.out.println(
                    "FILE PATH = "
                            + filePath
            );

            file.transferTo(destination);

            String extractedText =
                    extractPdfText(destination);

            if (extractedText.length() > 15000) {

                extractedText =
                        extractedText.substring(
                                0,
                                15000
                        );
            }

            Resume existingResume =
                    resumeRepository
                            .findByUserId(user.getId())
                            .orElse(null);

            Resume resume =
                    existingResume != null
                            ? existingResume
                            : new Resume();

            String aiSummary =
                    aiResumeService
                            .generateResumeSummary(
                                    extractedText
                            );

            resume.setAiSummary(
                    aiSummary
            );

            resume.setFileName(
                    file.getOriginalFilename()
            );

            resume.setFilePath(
                    filePath
            );

            resume.setFileType(
                    file.getContentType()
            );

            resume.setExtractedText(
                    extractedText
            );

            resume.setUser(user);

            Resume saved =
                    resumeRepository.save(resume);

            ResumeResponseDto response =
                    new ResumeResponseDto();

            response.setId(
                    saved.getId()
            );

            response.setFileName(
                    saved.getFileName()
            );

            response.setFileType(
                    saved.getFileType()
            );

            response.setUploadedAt(
                    saved.getUploadedAt()
            );

            response.setExtractedText(
                    saved.getExtractedText()
            );

            response.setAiSummary(
                    saved.getAiSummary()
            );

            response.setMessage(
                    existingResume != null
                            ? "Resume updated successfully"
                            : "Resume uploaded successfully"
            );

            return response;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Resume upload failed: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public ResumeResponseDto getResume(
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Resume resume =
                resumeRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resume not found"
                                )
                        );

        ResumeResponseDto response =
                new ResumeResponseDto();

        response.setId(
                resume.getId()
        );

        response.setFileName(
                resume.getFileName()
        );

        response.setFileType(
                resume.getFileType()
        );

        response.setUploadedAt(
                resume.getUploadedAt()
        );

        response.setExtractedText(
                resume.getExtractedText()
        );

        response.setAiSummary(
                resume.getAiSummary()
        );

        response.setMessage(
                "Resume fetched successfully"
        );

        return response;
    }

    private String extractPdfText(
            File pdfFile
    ) throws IOException {

        try (
                PDDocument document =
                        Loader.loadPDF(pdfFile)
        ) {

            PDFTextStripper stripper =
                    new PDFTextStripper();

            return stripper.getText(document);
        }
    }
    @Override
    public void deleteResume(
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Resume resume =
                resumeRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resume not found"
                                )
                        );

        try {

            File resumeFile =
                    new File(
                            resume.getFilePath()
                    );

            if (
                    resumeFile.exists()
            ) {

                boolean deleted =
                        resumeFile.delete();

                System.out.println(
                        "FILE DELETED = "
                                + deleted
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        resumeRepository.delete(resume);
    }
}