package com.hutech.furniturestore.dtos.role;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Request to delete multiple roles")
public class DeleteRoleDto {
    @Schema(description = "List of role IDs to be deleted", example = "[1, 2, 3]")
    private List<Long> roleIds;

    // Getters and Setters
    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
