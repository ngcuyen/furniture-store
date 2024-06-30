package com.hutech.furniturestore.dtos.request;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Pattern(regexp = "^[A-Za-zÀ-Ạà-ạÁ-Ắá-ắÂ-Ậâ-ậÄ-äÈ-Ẹè-ẹÉ-Ếé-ếÊ-Ệê-ệÌ-Ịì-ịÍ-íÒ-Ọò-ọÓ-Ốó-ốÔ-Ộô-ộÖ-öÙ-Ụù-ụÚ-Ứú-ứÛ-ûÜ-üĐđ\\s]+$", message = "FIRSTNAME_INVALID")
    String firstName;

    @Pattern(regexp = "^[A-Za-zÀ-Ạà-ạÁ-Ắá-ắÂ-Ậâ-ậÄ-äÈ-Ẹè-ẹÉ-Ếé-ếÊ-Ệê-ệÌ-Ịì-ịÍ-íÒ-Ọò-ọÓ-Ốó-ốÔ-Ộô-ộÖ-öÙ-Ụù-ụÚ-Ứú-ứÛ-ûÜ-üĐđ\\s]+$", message = "LASTNAME_INVALID")
    String lastName;

    @Pattern(regexp = "^https?:\\/\\/[^\\s\\/$.?#].[^\\s]*\\/[^\\s]*\\.(jpeg|jpg|png)$", message = "VALID_THUMBNAIL_URL_IMAGE")
    String thumbnail;

    @Pattern(regexp = "^https?:\\/\\/[^\\s\\/$.?#].[^\\s]*\\/[^\\s]*\\.(jpeg|jpg|png)$", message = "VALID_AVATAR_URL_IMAGE")
    String avatar;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "PHONE_INVALID")
    String phone;

    String address;
}
