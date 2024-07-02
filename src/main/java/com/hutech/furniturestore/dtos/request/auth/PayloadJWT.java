package com.hutech.furniturestore.dtos.request.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayloadJWT {
    String id;
    String username;
    String email;
}
