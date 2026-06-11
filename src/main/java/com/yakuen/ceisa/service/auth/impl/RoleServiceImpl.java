package com.yakuen.ceisa.service.auth.impl;

import com.yakuen.ceisa.dto.auth.CreateRoleRequest;
import com.yakuen.ceisa.exception.BusinessException;
import com.yakuen.ceisa.mapper.auth.RoleMapper;
import com.yakuen.ceisa.mapper.auth.UserMapper;
import com.yakuen.ceisa.model.auth.Role;
import com.yakuen.ceisa.service.auth.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private static final String DEFAULT_ROLE_CODE = "STAFF";

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
    roleMapper.deleteUserRoles(userId);
    for (String roleCode : roleCodes) {
      Role role = roleMapper.findByCode(roleCode);
      if (role == null) {
        throw new BusinessException(
          HttpStatus.BAD_REQUEST,
          "Role not found: " + roleCode
        );
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
      throw new BusinessException(
        HttpStatus.BAD_REQUEST,
        "Default role tidak ditemukan: " + DEFAULT_ROLE_CODE
      );
    }
    roleMapper.deleteUserRoles(userId);
    roleMapper.assignRoleToUser(userId, defaultRole.getId());
  }
}
