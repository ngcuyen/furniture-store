package com.hutech.furniturestore.mapper;

import com.hutech.furniturestore.dtos.request.auth.UserSignInRequest;
import com.hutech.furniturestore.dtos.request.auth.UserUpdateRequest;
import com.hutech.furniturestore.dtos.response.user.UserResponse;
import com.hutech.furniturestore.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserSignInRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
