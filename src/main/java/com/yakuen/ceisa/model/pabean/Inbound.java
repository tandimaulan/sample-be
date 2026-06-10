package com.yakuen.ceisa.model.pabean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Inbound {
    // Jenis Dokumen
    private String typeDoc;          // TYPE_CD
    
    // Dokumen Pabean
    private String cdNo;             // CD_NO
    private String dtCd;             // DT_CD
    
    // Bukti Penerimaan Barang
    private String billIo;           // BILL_IO (No Bukti)
    private String dtIo;             // DT_IO
    //Supplier
    private String suppNm;          // SUPP_NM (Nama Supplier) - dari TB_GWSUPP
    // Barang
    private String matNo;            // MAT_NO (dari TB_GWMAT)
    private String matNm;            // MAT_NM (Nama Barang) - dari TB_GWMAT
    private String sat;              // UNIT_MAT (Satuan) - dari TB_GWMAT atau TB_GWITI
    
    // Jumlah
    private BigDecimal qtyIo;        // QTY_IO
}
