package com.yakuen.ceisa.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.BooleanTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.yakuen.ceisa.model.User;

@Mapper
public interface UserMapper {

    @Insert("""
            INSERT INTO TB_GWITUSER (ID, USERNAME, EMAIL, PASSWORD_HASH, FULL_NAME, ACTIVE, CREATED_AT, UPDATED_AT)
            VALUES (#{id},
                    #{username},
                    #{email},
                    #{passwordHash},
                    #{fullName},
                    #{active, jdbcType=NUMERIC, typeHandler=org.apache.ibatis.type.BooleanTypeHandler},
                    #{createdAt},
                    #{updatedAt})
            """)
    @SelectKey(statement = "SELECT TB_GWIT_USER_SEQ.NEXTVAL FROM DUAL", keyProperty = "id", before = true, resultType = Long.class)
    @Options(useGeneratedKeys = false)
    int insert(User user);

    @Select("""
            SELECT ID, USERNAME, EMAIL, PASSWORD_HASH, FULL_NAME, ACTIVE, CREATED_AT, UPDATED_AT
            FROM TB_GWITUSER
            WHERE ID = #{id}
            """)
    @Results(id = "userResultMap", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "username", column = "USERNAME"),
            @Result(property = "email", column = "EMAIL"),
            @Result(property = "passwordHash", column = "PASSWORD_HASH"),
            @Result(property = "fullName", column = "FULL_NAME"),
            @Result(property = "active", column = "ACTIVE", javaType = Boolean.class, jdbcType = JdbcType.NUMERIC, typeHandler = BooleanTypeHandler.class),
            @Result(property = "createdAt", column = "CREATED_AT"),
            @Result(property = "updatedAt", column = "UPDATED_AT")
    })
    User findById(@Param("id") Long id);

    @Select("""
            SELECT ID, USERNAME, EMAIL, PASSWORD_HASH, FULL_NAME, ACTIVE, CREATED_AT, UPDATED_AT
            FROM TB_GWITUSER
            WHERE USERNAME = #{username}
            """)
    @ResultMap("userResultMap")
    User findByUsername(@Param("username") String username);

    @Select("""
            SELECT ID, USERNAME, EMAIL, PASSWORD_HASH, FULL_NAME, ACTIVE, CREATED_AT, UPDATED_AT
            FROM TB_GWITUSER
            WHERE EMAIL = #{email}
            """)
    @ResultMap("userResultMap")
    User findByEmail(@Param("email") String email);

    @Select("""
            SELECT ID, USERNAME, EMAIL, PASSWORD_HASH, FULL_NAME, ACTIVE, CREATED_AT, UPDATED_AT
            FROM TB_GWITUSER
            WHERE USERNAME = #{identifier}
               OR EMAIL = #{identifier}
            """)
    @ResultMap("userResultMap")
    User findByUsernameOrEmail(@Param("identifier") String identifier);

    @Select("""
            SELECT ID, USERNAME, EMAIL, PASSWORD_HASH, FULL_NAME, ACTIVE, CREATED_AT, UPDATED_AT
            FROM TB_GWITUSER
            ORDER BY ID
            """)
    @ResultMap("userResultMap")
    List<User> findAll();

    @Update("""
            UPDATE TB_GWITUSER
            SET EMAIL = #{email},
                FULL_NAME = #{fullName},
                ACTIVE = #{active, jdbcType=NUMERIC, typeHandler=org.apache.ibatis.type.BooleanTypeHandler},
                UPDATED_AT = #{updatedAt}
            WHERE ID = #{id}
            """)
    int update(User user);

    @Update("""
            UPDATE TB_GWITUSER
           SET PASSWORD_HASH = #{passwordHash},
                UPDATED_AT = #{updatedAt}
            WHERE ID = #{id}
            """)
    int updatePassword(User user);

    
}
