package org.example.modul223backend.Role;

import org.example.modul223backend.Role.RoleDTO;
import org.example.modul223backend.Role.Role;
import org.example.modul223backend.Role.RoleRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        // Check if role already exists
        if (roleRepository.existsByRoleName(roleDTO.getRoleName())) {
            throw new RuntimeException("Role already exists");
        }

        Role role = Mapper.mapToRoleEntity(roleDTO);
        role = roleRepository.save(role);
        return Mapper.mapToRoleDTO(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(Mapper::mapToRoleDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return Mapper.mapToRoleDTO(role);
    }

    @Override
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setRoleName(roleDTO.getRoleName());
        role = roleRepository.save(role);
        return Mapper.mapToRoleDTO(role);
    }

    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }
        roleRepository.deleteById(id);
    }
}

