package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.models.Role;
import com.hutech.furniturestore.sevices.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "Auth management APIs")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * Description: Create a new role
     * Purpose: Adds a new role to the system.
     * Path: /api/roles
     * Method: POST
     * Body: {name}
     */
    @PostMapping("/")
    public Role createRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    /**
     * Description: Get a role by ID
     * Purpose: Retrieves a specific role by its ID.
     * Path: /api/roles/{id}
     * Method: GET
     */
    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    /**
     * Description: Get all roles
     * Purpose: Retrieves all roles in the system.
     * Path: /api/roles
     * Method: GET
     */
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * Description: Delete a role
     * Purpose: Deletes a specific role by its ID.
     * Path: /api/roles/{id}
     * Method: DELETE
     */
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    /**
     * Description: Update a role
     * Purpose: Updates a specific role by its ID.
     * Path: /api/roles/{id}
     * Method: PUT
     * Body: {name}
     */
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        Role role = roleService.getRoleById(id);

        if (role != null) {
            role.setName(roleDetails.getName());
            return roleService.saveRole(role);
        } else {
            return null; // Handle not found case
        }
    }
}
