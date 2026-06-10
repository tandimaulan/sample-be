package com.yakuen.ceisa.mapper.pabean;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yakuen.ceisa.dto.pabean.PabeanFilter;
import com.yakuen.ceisa.model.pabean.Inbound;

@Mapper
public interface PabeanMapper {

    @Select("<script>" +
            "SELECT " +
            "    i.TYPE_CD as typeDoc, " +
            "    i.CD_NO as cdNo, " +
            "    i.DT_CD as dtCd, " +
            "    i.BILL_IO as billIo, " +
            "    i.DT_IO as dtIo, " +
            "    i.QTY_IO as qtyIo, " +
            "    m.MAT_NO as matNo, " +
            "    m.MAT_NM as matNm, " +
            "    m.UNIT_MAT as sat, " +
            "    s.SUPP_NM as suppNm " +
            "FROM TB_GWITI i " +
            "LEFT JOIN TB_GWMAT m ON i.MAT_ID = m.MAT_ID " +
            "LEFT JOIN TB_GWSUPP s ON i.SUPP_NO = s.SUPP_NO " +
            "WHERE 1=1 " +
            "<if test='filter.typeDoc != null and filter.typeDoc != \"\"'>" +
            "    AND i.TYPE_CD = #{filter.typeDoc} " +
            "</if>" +
            "<if test='filter.startDate != null'>" +
            "    AND TO_DATE(i.DT_CD, 'YYYYMMDD') &gt;= #{filter.startDate} " +
            "</if>" +
            "<if test='filter.endDate != null'>" +
            "    AND TO_DATE(i.DT_CD, 'YYYYMMDD') &lt;= #{filter.endDate} " +
            "</if>" +
            "ORDER BY i.DT_CD DESC" +
            "</script>")
    List<Inbound> getInboundData(@Param("filter") PabeanFilter filter);
}
