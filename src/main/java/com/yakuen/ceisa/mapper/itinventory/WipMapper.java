package com.yakuen.ceisa.mapper.itinventory;

import com.yakuen.ceisa.model.itinventory.WipInventory;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WipMapper {
  @Select(
    "SELECT " +
      "   m.MAT_NO, " +
      "   m.MAT_NM, " +
      "   m.UNIT_MAT, " +
      "   w.QTY_N, " +
      "   w.DESC_MK " +
      "FROM TB_GWITWIP w " +
      "INNER JOIN TB_GWMAT m ON w.MAT_ID = m.MAT_ID"
  )
  @Results(
    id = "wipResultMap",
    value = {
      @Result(property = "matNo", column = "MAT_NO"),
      @Result(property = "matNm", column = "MAT_NM"),
      @Result(property = "unitMat", column = "UNIT_MAT"),
      @Result(property = "qty", column = "QTY_N"),
      @Result(property = "desc", column = "DESC_MK"),
    }
  )
  public List<WipInventory> findAll();
}
