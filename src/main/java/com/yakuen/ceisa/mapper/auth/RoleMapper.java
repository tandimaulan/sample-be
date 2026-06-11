package com.yakuen.ceisa.mapper.auth;

import com.yakuen.ceisa.model.auth.Role;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface RoleMapper {
  @Insert(
    """
    INSERT INTO TB_SYSROLE (ID, ROLE_CODE, ROLE_NAME)
    VALUES (#{id}, #{roleCode}, #{roleName})
    """
  )
  @SelectKey(
    statement = "SELECT TB_SYS_ROLE_SEQ.NEXTVAL FROM DUAL",
    keyProperty = "id",
    before = true,
    resultType = Long.class
  )
  @Options(useGeneratedKeys = false)
  int insert(Role role);

  @Select(
    """
    SELECT ID, ROLE_CODE, ROLE_NAME
    FROM TB_SYSROLE
    WHERE ROLE_CODE = #{roleCode}
    """
  )
  Role findByCode(@Param("roleCode") String roleCode);

  @Select(
    """
    SELECT ID, ROLE_CODE, ROLE_NAME
    FROM TB_SYSROLE
    ORDER BY ROLE_CODE
    """
  )
  List<Role> findAll();

  @Select(
    """
    SELECT r.ID, r.ROLE_CODE, r.ROLE_NAME
    FROM TB_SYSROLE r
    JOIN TB_SYSUSER_ROLE ur ON ur.ROLE_ID = r.ID
    WHERE ur.USER_ID = #{userId}
    ORDER BY r.ROLE_CODE
    """
  )
  List<Role> findByUserId(@Param("userId") Long userId);

  @Insert(
    """
    INSERT INTO TB_SYSUSER_ROLE (USER_ID, ROLE_ID)
    VALUES (#{userId}, #{roleId})
    """
  )
  int assignRoleToUser(
    @Param("userId") Long userId,
    @Param("roleId") Long roleId
  );

  @Delete(
    """
    DELETE FROM TB_SYSUSER_ROLE
    WHERE USER_ID = #{userId}
    """
  )
  int deleteUserRoles(@Param("userId") Long userId);
}
