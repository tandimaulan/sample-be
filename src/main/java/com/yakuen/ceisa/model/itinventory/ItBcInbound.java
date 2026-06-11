package com.yakuen.ceisa.model.itinventory;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ItBcInbound {

  private String typeDoc; // TYPE_CD
  private String cdNo; // CD_NO
  private String dtCd; // DT_CD
  private String billIo; // BILL_IO (No Bukti)
  private String dtIo; // DT_IO
  private String suppNm; // SUPP_NM (Nama Supplier) - dari TB_GWSUPP
  private String matNo; // MAT_NO (dari TB_GWMAT)
  private String matNm; // MAT_NM (Nama Barang) - dari TB_GWMAT
  private String sat; // UNIT_MAT (Satuan) - dari TB_GWMAT atau TB_GWITI
  private BigDecimal qtyIo;
}
