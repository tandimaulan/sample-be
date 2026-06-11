package com.yakuen.ceisa.model.itinventory;

import lombok.Data;

@Data
public class ItBcOutbound {

  private String jenisDok; // TYPE_CD
  private String cdNo; // CD_NO (No Dok Pabean)
  private String dtCd; // DT_CD (Tgl Dok Pabean)
  private String billIo; // BILL_IO (No Bukti Keluar)
  private String dtIo; // DT_IO (Tgl Bukti Keluar)
  private String custNm; // CUST_NM (nama penerima)
  private String matNo; // MAT_NO (dari TB_GWMAT)
  private String matNm; // MAT_NM (nama barang)
  private String unitMat; // UNIT_MAT (satuan)
  private Double qtyIo; // QTY_IO (jumlah)
  private Double wtIo; // WT_IO (sementara jadi nilai barang, tapi hati-hati)
}
