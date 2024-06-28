package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.sevices.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("api/v1/uploads")
@Tag(name="uploads",description = "Upload management APIs")
@RequiredArgsConstructor
public class FileUploadController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");
    private final FileUploadService fileUploadService;

//    @PostMapping
//    @Operation(
//            summary = "Upload image",
//            responses = {
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
//                            responseCode = "200",
//                            description = "Image uploaded successfully",
//                            content = @Content(
//                                    mediaType = "multipart/form-data",
//                                    schema = @Schema(implementation = ApiResponse.class),
//                                    examples = @ExampleObject(
//                                            value = "{ \"statusCode\": 200, \"message\": \"Successfully retrieved all products in database.\", " +
//                                                    "\"data\":  \"https://res.cloudinary.com/dfnmmhhf2/image/upload/v1717078523/solutions/wkttjm65i6xkkjvpxkl1.png\" , " +
//                                                    "\"dateTime\": \"18-06-2024T01:36:12.822\", " +
//                                                    "\"messageConstants\": null}"
//                                    )
//                            )
//                    ),
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
//                            responseCode = "401",
//                            description = "Unauthorized",
//                            content = @Content(
//                                    mediaType = "multipart/form-data",
//                                    schema = @Schema(implementation = ApiResponse.class),
//                                    examples = @ExampleObject(
//                                            value = "{ \"statusCode\": 401, \"message\": \"Unauthorized access\", " +
//                                                    "\"data\": null, " +
//                                                    "\"dateTime\": \"01-01-2023T12:00:00.000\", " +
//                                                    "\"messageConstants\": null}"
//                                    )
//                            )
//                    ),
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
//                            responseCode = "403",
//                            description = "Forbidden",
//                            content = @Content(
//                                    mediaType = "multipart/form-data",
//                                    schema = @Schema(implementation = ApiResponse.class),
//                                    examples = @ExampleObject(
//                                            value = "{ \"statusCode\": 403, \"message\": \"Access forbidden\"," +
//                                                    " \"data\": null, " +
//                                                    "\"dateTime\": \"01-01-2023T12:00:00.000\", " +
//                                                    "\"messageConstants\": null }"
//                                    )
//                            )
//                    ),
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
//                            responseCode = "500",
//                            description = "Internal Server Error",
//                            content = @Content(
//                                    mediaType = "multipart/form-data",
//                                    schema = @Schema(implementation = ApiResponse.class),
//                                    examples = @ExampleObject(
//                                            value = "{ \"statusCode\": 500, \"message\": \"An unexpected error occurred\", " +
//                                                    "\"data\": null, " +
//                                                    "\"dateTime\": \"01-01-2023T12:00:00.000\", " +
//                                                    "\"messageConstants\": null }"
//                                    )
//                            )
//                    )
//            }
//    )
//    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestPart("image") MultipartFile file)  {
//       try {
//           // Implement file upload logic here
//           String imageURL = fileUploadService.uploadFile(file);
//           ApiResponse<String> response = new ApiResponse<>(
//                   HttpStatus.OK.value(),
//                   "Image uploaded successfully",
//                   imageURL,
//                   LocalDateTime.now().format(formatter),
//                   null
//           );
//           return new ResponseEntity<>(response, HttpStatus.OK);
//       }catch (Exception e){
//           ApiResponse<String> response = new ApiResponse<>(
//                   HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                   "An unexpected error occurred",
//                   null,
//                   LocalDateTime.now().format(formatter),
//                   null
//           );
//           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//       }
//    }

    @PostMapping()
    @Operation(
            summary = "Upload image",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Content",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(
                                    type = "object",
                                    requiredProperties = {"image"}
//                                    properties = {
//                                            @Schema(name = "image", description = "The image file to upload", type = "string", format = "binary")
//                                    }
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Image uploaded successfully",
                            content = @Content(
                                    mediaType = "multipart/form-data",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "multipart/form-data",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
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
        } catch (Exception e) {
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
