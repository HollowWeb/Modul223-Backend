package org.example.modul223backend.Role;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Role entities.
 * Provides endpoints for creating, retrieving, updating, and deleting roles.
 * Access to some methods is restricted to users with the "ADMIN" role.
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    /**
     * Constructor for injecting the RoleService.
     * @param roleService the service handling role operations.
     */
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Creates a new role.
     * Only accessible to users with the "ADMIN" role.
     * @param roleDTO the data of the role to be created.
     * @return the created role as a DTO.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

    /**
     * Retrieves all roles.
     * Only accessible to users with the "ADMIN" role.
     * @return a list of all roles as DTOs.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * Retrieves a specific role by its ID.
     * @param id the ID of the role to retrieve.
     * @return the role as a DTO.
     */
    @GetMapping("/{id}")
    public RoleDTO getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    /**
     * Updates an existing role by its ID.
     * @param id the ID of the role to update.
     * @param roleDTO the updated data for the role.
     * @return the updated role as a DTO.
     */
    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(id, roleDTO);
    }

    /**
     * Deletes a role by its ID.
     * @param id the ID of the role to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}

