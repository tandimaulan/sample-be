package com.yakuen.ceisa.mapper.basicdata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yakuen.ceisa.model.basicdata.Guest;

@Mapper
public interface GuestMapper {
    // Find all
    @Select("SELECT CUST_NO as custNo, " +
            "CUSTOM_NO as customNo, " +
            "LOCAL_MK as localMk, " +
            "USE_MK as useMk, " +
            "CURR_NO as currNo, " +
            "TYPE_CD as typeCd, " +
            "CUST_NM as custNm, " +
            "CUST_NMS as custNms, " +
            "CTRY_NM as ctryNm, " +
            "AREA_ID as areaId " +
            "FROM TB_GWCUST")
    List<Guest> findAll();
    
    // Find by ID (CUST_NO)
    @Select("SELECT CUST_NO as custNo, " +
            "CUSTOM_NO as customNo, " +
            "LOCAL_MK as localMk, " +
            "USE_MK as useMk, " +
            "CURR_NO as currNo, " +
            "TYPE_CD as typeCd, " +
            "CUST_NM as custNm, " +
            "CUST_NMS as custNms, " +
            "CTRY_NM as ctryNm, " +
            "AREA_ID as areaId " +
            "FROM TB_GWCUST WHERE CUST_NO = #{custNo}")
    Guest findByCustNo(@Param("custNo") String custNo);
}
