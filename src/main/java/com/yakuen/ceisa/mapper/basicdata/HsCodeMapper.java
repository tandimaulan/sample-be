package com.yakuen.ceisa.mapper.basicdata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.yakuen.ceisa.model.basicdata.HsCode;

@Mapper
public interface HsCodeMapper {

    @Select("SELECT HS_ID as hsId, " +
            "DT_ADD as dtAdd, " +
            "HS_NO as hsNo, " +
            "TYPE_HS as typeHs, " +
            "UNIT_HS as unitHs, " +
            "OFEN_MK as ofenMk, " +
            "USE_MK as useMk, " +
            "DT_STP as dtStp, " +
            "TM_OPR as tmOpr, " +
            "HS_NOB as hsNob, " +
            "HS_NMB as hsNmb, " +
            "HS_NM as hsNm " +
            "FROM TB_GWHS")
    List<HsCode> findAll();

}
