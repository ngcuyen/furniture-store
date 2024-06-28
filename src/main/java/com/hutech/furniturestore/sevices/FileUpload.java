package com.hutech.furniturestore.sevices;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {
    String uploadFile(MultipartFile multipartFile) throws Exception;
}
