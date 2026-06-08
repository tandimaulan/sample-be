package com.yakuen.ceisa.service.impl;

import com.yakuen.ceisa.dto.CreateRoleRequest;
import com.yakuen.ceisa.exception.BusinessException;
import com.yakuen.ceisa.mapper.RoleMapper;
import com.yakuen.ceisa.mapper.UserMapper;
import com.yakuen.ceisa.model.Role;
import com.yakuen.ceisa.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final String DEFAULT_ROLE_CODE = "USER";

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;

    @Override
    public Role createRole(CreateRoleRequest request) {
        if (roleMapper.findByCode(request.getRoleCode()) != null) {
            throw new BusinessException(HttpStatus.CONFLICT, "Role sudah ada");
        }

        Role role = new Role();
        role.setRoleCode(request.getRoleCode().trim().toUpperCase());
        role.setRoleName(request.getRoleName().trim());
        roleMapper.insert(role);
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.findAll();
    }

    @Override
    @Transactional
    public void assignRolesToUser(Long userId, List<String> roleCodes) {
        if (userMapper.findById(userId) == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "User tidak ditemukan");
        }

        roleMapper.deleteUserRoles(userId);
        for (String roleCode : roleCodes) {
            Role role = roleMapper.findByCode(roleCode.trim().toUpperCase());
            if (role == null) {
                throw new BusinessException(HttpStatus.NOT_FOUND, "Role " + roleCode + " tidak ditemukan");
            }
            roleMapper.assignRoleToUser(userId, role.getId());
        }
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        return roleMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public void assignDefaultRole(Long userId) {
        Role defaultRole = roleMapper.findByCode(DEFAULT_ROLE_CODE);
        if (defaultRole == null) {
            Role role = new Role();
            role.setRoleCode(DEFAULT_ROLE_CODE);
            role.setRoleName("Default User");
            roleMapper.insert(role);
            defaultRole = role;
        }
        roleMapper.assignRoleToUser(userId, defaultRole.getId());
    }
}
