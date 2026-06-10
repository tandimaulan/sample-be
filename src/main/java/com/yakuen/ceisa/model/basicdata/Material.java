package com.yakuen.ceisa.model.basicdata;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Material {
    private String matId;//Material ID
    private String typeMatg;//Type Material Group
    private String matNo;//Material Number
    private String unitMat;//Unit of Material
    private BigDecimal wtPer;//Weight Per Unit
    private String fromMk;//From Mark
    private String useMk;//Use Mark
    private BigDecimal rtWst;//Rate of Waste
    private String matNm;//Material Name
    private String matNm2;//Material Name 2
    private String dtE;//Date Effective
    private String tmOpr;//Time of Operation
}