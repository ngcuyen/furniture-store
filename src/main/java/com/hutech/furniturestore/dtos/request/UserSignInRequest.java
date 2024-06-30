package com.hutech.furniturestore.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignInRequest {
    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotBlank(message = "USER_REQUIRED")
    @Size(min = 3, max = 50, message = "USERNAME_INVALID")
    String username;

    @NotBlank(message = "FIRSTNAME_REQUIRED")
    @Pattern(regexp = "^[A-Za-zÀ-Ạà-ạÁ-Ắá-ắÂ-Ậâ-ậÄ-äÈ-Ẹè-ẹÉ-Ếé-ếÊ-Ệê-ệÌ-Ịì-ịÍ-íÒ-Ọò-ọÓ-Ốó-ốÔ-Ộô-ộÖ-öÙ-Ụù-ụÚ-Ứú-ứÛ-ûÜ-üĐđ\\s]+$", message = "FIRSTNAME_INVALID")
    String firstName;

    @NotBlank(message = "LASTNAME_REQUIRED")
    @Pattern(regexp = "^[A-Za-zÀ-Ạà-ạÁ-Ắá-ắÂ-Ậâ-ậÄ-äÈ-Ẹè-ẹÉ-Ếé-ếÊ-Ệê-ệÌ-Ịì-ịÍ-íÒ-Ọò-ọÓ-Ốó-ốÔ-Ộô-ộÖ-öÙ-Ụù-ụÚ-Ứú-ứÛ-ûÜ-üĐđ\\s]+$", message = "LASTNAME_INVALID")
    String lastName;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 8, max = 16, message = "PASSWORD_LENGTH_MUST_BE_FROM_8_TO_16")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,16}$", message = "INCORRECT_FORMAT_PASSWORD")
    String password;

    @NotBlank(message = "PHONE_REQUIRED")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "PHONE_INVALID")
    String phone;
}
