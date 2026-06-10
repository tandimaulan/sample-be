package com.yakuen.ceisa.mapper.basicdata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.yakuen.ceisa.model.basicdata.Supplier;

@Mapper
public interface SupplierMapper {
    
    @Select("SELECT SUPP_NO as suppNo, " +
            "VENDER_NO as venderNo, " +
            "LOCAL_MK as localMk, " +
            "USE_MK as useMk, " +
            "CURR_NO as currNo, " +
            "TYPE_CD as typeCd, " +
            "SUPP_NM as suppNm, " +
            "SUPP_NMS as suppNms, " +
            "CTRY_NM as ctryNm, " +
            "AREA_ID as areaId " +
            "FROM TB_GWSUPP")
    List<Supplier> findAll();

    @Select("SELECT SUPP_NO as suppNo, " +
            "VENDER_NO as venderNo, " +
            "LOCAL_MK as localMk, " +
            "USE_MK as useMk, " +
            "CURR_NO as currNo, " +
            "TYPE_CD as typeCd, " +
            "SUPP_NM as suppNm, " +
            "SUPP_NMS as suppNms, " +
            "CTRY_NM as ctryNm, " +
            "AREA_ID as areaId " +
            "FROM TB_GWSUPP WHERE SUPP_NO = #{suppNo}")
    Supplier findBySuppNo(String suppNo);
    
}