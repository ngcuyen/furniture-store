package com.hutech.furniturestore.dtos.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginResponse {
    String id;
    boolean authenticated;
    String username;
    String email;
    String access_token;
    String refresh_token;
}

