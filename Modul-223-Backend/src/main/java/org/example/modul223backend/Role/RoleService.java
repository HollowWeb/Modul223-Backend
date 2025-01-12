package org.example.modul223backend.Role;


import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(Long id);
    RoleDTO updateRole(Long id, RoleDTO roleDTO);
    void deleteRole(Long id);
}
