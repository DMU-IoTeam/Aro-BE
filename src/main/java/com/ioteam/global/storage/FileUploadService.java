package com.ioteam.global.storage;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileUploadService {
    String upload(MultipartFile file, String dirName) throws IOException;
}