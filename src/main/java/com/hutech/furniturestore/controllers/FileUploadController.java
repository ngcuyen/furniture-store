package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.sevices.FileUploadService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/uploads")
@Tag(name="uploads",description = "Upload management APIs")
@RequiredArgsConstructor
public class FileUploadController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");
    private final FileUploadService fileUploadService;


    @PostMapping()
    public ResponseEntity<ApiResponse<String>> uploadFile(
            @Parameter(description="File image to upload", required=true, schema = @Schema(type = "string", format = "binary"))
            @RequestParam("image") MultipartFile file) {
        try {
            if(file.getSize() == 0){
                return new ResponseEntity<>(
                        new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "File is required", "", LocalDateTime.now().format(formatter), null),
                        HttpStatus.BAD_REQUEST);
            }
            if(file.getSize() > 5 * 1024 * 1024){
                return new ResponseEntity<>(
                        new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY.value(), "File too large", "", LocalDateTime.now().format(formatter), null),
                        HttpStatus.PAYLOAD_TOO_LARGE);
            }
            String imageURL = fileUploadService.uploadFile(file);
            ApiResponse<String> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Image uploaded successfully",
                    imageURL,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred",
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
