package org.example.modul223backend.Role;


import java.util.List;

/**
 * Service interface for managing Role entities.
 * Defines operations for creating, retrieving, updating, and deleting roles.
 */
public interface RoleService {

    /**
     * Creates a new role.
     * @param roleDTO the data of the role to be created.
     * @return the created role as a DTO.
     */
    RoleDTO createRole(RoleDTO roleDTO);

    /**
     * Retrieves all roles.
     * @return a list of all roles as DTOs.
     */
    List<RoleDTO> getAllRoles();

    /**
     * Retrieves a specific role by its ID.
     * @param id the ID of the role to retrieve.
     * @return the role as a DTO.
     */
    RoleDTO getRoleById(Long id);

    /**
     * Updates an existing role by its ID.
     * @param id the ID of the role to update.
     * @param roleDTO the updated data for the role.
     * @return the updated role as a DTO.
     */
    RoleDTO updateRole(Long id, RoleDTO roleDTO);

    /**
     * Deletes a role by its ID.
     * @param id the ID of the role to delete.
     */
    void deleteRole(Long id);
}
