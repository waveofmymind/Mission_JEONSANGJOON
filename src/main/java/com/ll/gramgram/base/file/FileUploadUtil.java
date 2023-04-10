package com.ll.gramgram.base.file;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

public class FileUploadUtil {
    final static String uploadDir = "src/main/resources/static/profileImage/";


    public static void saveFile(MultipartFile image) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);

        // 폴더가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("파일 저장에 실패했습니다: " + fileName, ioe);
        }
    }
}