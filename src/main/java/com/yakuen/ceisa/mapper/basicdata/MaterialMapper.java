package com.yakuen.ceisa.mapper.basicdata;

import com.yakuen.ceisa.model.basicdata.Material;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MaterialMapper {
  @Select(
    "SELECT MAT_ID as matId, " +
      "TYPE_MATG as typeMatg, " +
      "MAT_NO as matNo, " +
      "UNIT_MAT as unitMat, " +
      "WT_PER as wtPer, " +
      "FROM_MK as fromMk, " +
      "USE_MK as useMk, " +
      "RT_WST as rtWst, " +
      "MAT_NM as matNm, " +
      "MAT_NM2 as matNm2, " +
      "DT_E as dtE, " +
      "TM_OPR as tmOpr " +
      "FROM TB_GWMAT"
  )
  List<Material> findAll();
}
