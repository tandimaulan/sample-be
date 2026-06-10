package com.yakuen.ceisa.model.itinventory;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WipInventory {
    private String matNo;
    private String matNm;
    private String unitMat;
    private BigDecimal qty;
    private String desc;
}