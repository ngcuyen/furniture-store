package com.hutech.furniturestore.sevices;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FileUploadService implements FileUpload{
    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException{
    return cloudinary.uploader()
            .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
            .get("url")
            .toString();
    }
}
