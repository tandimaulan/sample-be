package com.yakuen.ceisa.mapper.auth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.yakuen.ceisa.model.auth.PasswordResetToken;

@Mapper
public interface PasswordResetTokenMapper {

    @Insert("""
            INSERT INTO TB_SYSPASSWORD_RESET_TOKEN (ID, USER_ID, TOKEN, EXPIRES_AT, USED, CREATED_AT)
            VALUES (#{id}, #{userId}, #{token}, #{expiresAt}, #{used}, #{createdAt})
            """)
    @SelectKey(statement = "SELECT PASSWORD_RESET_TOKEN_SEQ.NEXTVAL FROM DUAL", keyProperty = "id", before = true, resultType = Long.class)
    @Options(useGeneratedKeys = false)
    int insert(PasswordResetToken token);

    @Select("""
            SELECT ID, USER_ID, TOKEN, EXPIRES_AT, USED, CREATED_AT
            FROM TB_SYSPASSWORD_RESET_TOKEN
            WHERE TOKEN = #{token}
            """)
    PasswordResetToken findByToken(@Param("token") String token);

    @Update("""
            UPDATE TB_SYSPASSWORD_RESET_TOKEN
            SET USED = 1
            WHERE ID = #{id}
            """)
    int markAsUsed(@Param("id") Long id);
}