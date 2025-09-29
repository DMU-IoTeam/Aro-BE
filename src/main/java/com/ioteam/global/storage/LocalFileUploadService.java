package com.ioteam.global.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class LocalFileUploadService implements FileUploadService {

    @Value("${FILE_UPLOAD_DIR}")
    private String uploadDirName;

    @Override
    public String upload(MultipartFile file, String dirName) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String basePath = System.getProperty("user.dir");
        String fullPath = basePath + File.separator + uploadDirName + dirName;

        File uploadDir = new File(fullPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String savedFilename = uuid + extension;

        String savedPath = fullPath + File.separator + savedFilename;
        File savedFile = new File(savedPath);
        file.transferTo(savedFile);

        return "/" + uploadDirName + dirName + "/" + savedFilename;
    }
}
