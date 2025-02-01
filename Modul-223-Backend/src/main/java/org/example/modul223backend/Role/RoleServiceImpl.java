package org.example.modul223backend.Role;


import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Service implementation for managing Role entities.
 * Handles the business logic for creating, retrieving, updating, and deleting roles.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Creates a new role.
     * @param roleDTO the data of the role to be created.
     * @return the created role as a DTO.
     * @throws RuntimeException if a role with the given name already exists.
     */
    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        if (roleRepository.existsByRoleName(roleDTO.getRoleName())) {
            throw new RuntimeException("Role already exists");
        }

        Role role = Mapper.mapToRoleEntity(roleDTO);
        role = roleRepository.save(role);
        return Mapper.mapToRoleDTO(role);
    }

    /**
     * Retrieves all roles.
     * @return a list of all roles as DTOs.
     */
    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(Mapper::mapToRoleDTO).toList();
    }

    /**
     * Retrieves a specific role by its ID.
     * @param id the ID of the role to retrieve.
     * @return the role as a DTO.
     * @throws RuntimeException if the role with the given ID is not found.
     */
    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return Mapper.mapToRoleDTO(role);
    }

    /**
     * Updates an existing role by its ID.
     * @param id the ID of the role to update.
     * @param roleDTO the updated data for the role.
     * @return the updated role as a DTO.
     * @throws RuntimeException if the role with the given ID is not found.
     */
    @Override
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setRoleName(roleDTO.getRoleName());
        role = roleRepository.save(role);
        return Mapper.mapToRoleDTO(role);
    }

    /**
     * Deletes a role by its ID.
     * @param id the ID of the role to delete.
     * @throws RuntimeException if the role with the given ID is not found.
     */
    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }
}

