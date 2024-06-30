package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.constants.PredefinedRole;
import com.hutech.furniturestore.dtos.request.DeleteUsersDto;
import com.hutech.furniturestore.dtos.request.UserSignInRequest;
import com.hutech.furniturestore.dtos.request.UserUpdateRequest;
import com.hutech.furniturestore.dtos.response.user.UserResponse;
import com.hutech.furniturestore.exceptions.ErrorCode;
import com.hutech.furniturestore.exceptions.ErrorException;
import com.hutech.furniturestore.mapper.UserMapper;
import com.hutech.furniturestore.models.Role;
import com.hutech.furniturestore.models.User;
import com.hutech.furniturestore.repositories.RoleRepository;
import com.hutech.furniturestore.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    // create new user
    public User createUser(UserSignInRequest request){
        if(userRepository.existsByUsername(request.getUsername()) && userRepository.existsByEmail(request.getEmail())){
            throw new ErrorException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        // encoder password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set role user when user created account in system
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception){
            throw new ErrorException(ErrorCode.USER_EXISTED);
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findByActiveFalse();
    }

    private UserResponse convertToRoleResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setAddress(user.getAddress());
        userResponse.setPhone(user.getPhone());
        userResponse.setAddress(user.getAddress());
        userResponse.setActive(user.isActive());
        userResponse.setBlocked(user.isBlocked());
        userResponse.setThumbnail(user.getThumbnail());
        userResponse.setAvatar(user.getAvatar());
        return userResponse;
    }

    public PaginationResponse<UserResponse> getAllUserPagination(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserResponse> userResponses = userPage.getContent().stream()
                .map(this::convertToRoleResponse)
                .collect(Collectors.toList());
        PaginationResponse<UserResponse> paginatedResponse = new PaginationResponse<>();
        paginatedResponse.setItems(userResponses);
        paginatedResponse.setPage(userPage.getNumber() + 1);
        paginatedResponse.setPerPage(userPage.getSize());
        paginatedResponse.setTotalPages(userPage.getTotalPages());
        paginatedResponse.setTotalItems(userPage.getTotalElements());
        return paginatedResponse;
    }

    public Optional<User> getUserById(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent() && !userOpt.get().isActive()){
            return userOpt;
        }else{
            throw new ErrorException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public void deleteUser(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userRepository.existsById(id) && !userOpt.get().isActive()) {
            User user = userOpt.get();
            user.setActive(true);
            userRepository.save(user);
        } else {
            throw new ErrorException(ErrorCode.USER_NOT_FOUND);
        }
    }

    // Remove list of users
    public void deleteUsers(DeleteUsersDto deleteUsersRequest) {
        List<String> userIds = deleteUsersRequest.getUserIds();
        for (String id : userIds) {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isPresent() && !userOpt.get().isActive()) {
                User user = userOpt.get();
                user.setActive(true);
                userRepository.save(user);
            } else {
                throw new ErrorException(ErrorCode.USER_NOT_FOUND);
            }
        }
    }

    public User updateUser(String id, UserUpdateRequest request) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent() && !userOpt.get().isActive()) {
            User user = userOpt.get();
            userMapper.updateUser(user, request);
            return userRepository.save(user);
        } else {
            throw new ErrorException(ErrorCode.USER_NOT_FOUND);
        }
    }
}

