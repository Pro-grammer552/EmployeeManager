package com.task.service;

import com.task.entity.Role;
import com.task.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Integer roleId) {
        return roleRepository.findById(roleId);
    }

    public Role createRole(Role role) {
        // Add any additional validation or business logic before saving
        return roleRepository.save(role);
    }

    public Role updateRole(Integer roleId, Role updatedRole) {
        updatedRole.setRoleId(roleId);
        return roleRepository.save(updatedRole);
    }


    public void deleteRole(Integer roleId) {
        roleRepository.deleteById(roleId);
    }
}
