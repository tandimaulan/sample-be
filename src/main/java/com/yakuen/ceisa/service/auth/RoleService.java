package com.yakuen.ceisa.service.auth;

import com.yakuen.ceisa.dto.auth.CreateRoleRequest;
import com.yakuen.ceisa.model.auth.Role;
import java.util.List;

public interface RoleService {
  Role createRole(CreateRoleRequest request);

  List<Role> getAllRoles();

  void assignRolesToUser(Long userId, List<String> roleCodes);

  List<Role> getUserRoles(Long userId);

  void assignDefaultRole(Long userId);
}
