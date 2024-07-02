package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.models.Role;
import com.hutech.furniturestore.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");
    private final RoleRepository roleRepository;

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Helper method to convert String to LocalDateTime
    public LocalDateTime convertStringToLocalDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSS");
        return LocalDateTime.parse(dateTime, formatter);
    }

//    public RoleResponse createRole(RoleDto roleRequest) {
//        Role role = new Role();
//        role.setName(roleRequest.getName());
//        role.setDescription(roleRequest.getDescription());
//        Role savedRole = roleRepository.save(role);
//        RoleResponse roleResponse = new RoleResponse();
//        roleResponse.setId(savedRole.getId());
//        roleResponse.setName(savedRole.getName());
//        roleResponse.setDescription(savedRole.getDescription());
//        return roleResponse;
//    }
//
//    public RoleResponse getRoleById(Long id) {
//        Optional<Role> roleOpt = roleRepository.findById(id);
//        if (roleOpt.isPresent() && !roleOpt.get().getIsRemoved()) {
//            Role role = roleOpt.get();
//            RoleResponse roleResponse = new RoleResponse();
//            roleResponse.setId(role.getId());
//            roleResponse.setName(role.getName());
//            roleResponse.setDescription(role.getDescription());
//            return roleResponse;
//        } else {
//            throw new NoSuchElementFoundException("Role not found or has been removed with ID");
//        }
//    }
//
//    public List<Role> getAllRoles() {
//        return roleRepository.findAll();
//    }
//
//    private RoleResponse convertToRoleResponse(Role role) {
//        RoleResponse roleResponse = new RoleResponse();
//        roleResponse.setId(role.getId());
//        roleResponse.setName(role.getName());
//        roleResponse.setDescription(role.getDescription());
//        return roleResponse;
//    }
//
//    public PaginationResponse<RoleResponse> getAllRolesPagination(int page, int size, String sortBy, String sortOrder) {
//        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
//        Page<Role> rolePage = roleRepository.findAll(pageable);
//
//        List<RoleResponse> roleResponses = rolePage.getContent().stream()
//                .map(this::convertToRoleResponse)
//                .collect(Collectors.toList());
//
//        PaginationResponse<RoleResponse> paginatedResponse = new PaginationResponse<>();
//        paginatedResponse.setItems(roleResponses);
//        paginatedResponse.setPage(rolePage.getNumber() + 1); // Page index starts from 0, so add 1
//        paginatedResponse.setPerPage(rolePage.getSize());
//        paginatedResponse.setTotalPages(rolePage.getTotalPages());
//        paginatedResponse.setTotalItems(rolePage.getTotalElements());
//
//        return paginatedResponse;
//    }
//
//    public void deleteRoleById(Long id) {
//        Optional<Role> roleOpt = roleRepository.findById(id);
//        if (roleRepository.existsById(id) && !roleOpt.get().getIsRemoved()) {
//            Role role = roleOpt.get();
//            role.setIsRemoved(true);
//            role.setUpdatedAt(LocalDateTime.now());
//            roleRepository.save(role);
//        } else {
//            throw new NoSuchElementFoundException("Role not found or has been removed with ID");
//        }
//    }
//
//    public RoleResponse updateRole(Long id, RoleDto roleUpdateRequest) {
//        Optional<Role> roleOpt = roleRepository.findById(id);
//        if (roleOpt.isPresent() && !roleOpt.get().getIsRemoved()) {
//            Role role = roleOpt.get();
//            role.setName(roleUpdateRequest.getName());
//            role.setDescription(roleUpdateRequest.getDescription());
//            role.setUpdatedAt(LocalDateTime.now());
//            Role updatedRole = roleRepository.save(role);
//            return convertToRoleResponse(updatedRole);
//        } else {
//            throw new NoSuchElementFoundException("Role not found or has been removed with ID");
//        }
//    }
//
//    public void deleteRoles(DeleteRoleDto deleteRolesRequest) {
//        List<Long> roleIds = deleteRolesRequest.getRoleIds();
//        for (Long id : roleIds) {
//            Optional<Role> roleOpt = roleRepository.findById(id);
//            if (roleOpt.isPresent() && !roleOpt.get().getIsRemoved()) {
//                Role role = roleOpt.get();
//                role.setIsRemoved(true);
//                role.setUpdatedAt(LocalDateTime.now());
//                roleRepository.save(role);
//            } else {
//                throw new NoSuchElementFoundException("Role not found or has been removed with ID: " + id);
//            }
//        }
//    }
}
