package com.yakuen.ceisa.model.basicdata;

import lombok.Data;

@Data
public class HsCode {

  private String hsId; //HS Code ID
  private String dtAdd; //Date Added
  private String hsNo; //HS Code Number
  private String typeHs; //Type of HS Code
  private String unitHs; //Unit of HS Code
  private String ofenMk; //Often Mark
  private String useMk; //Use Mark
  private String dtStp; //Date Stopped
  private String tmOpr; //Time of Operation
  private String hsNob; //HS Code Number (Short)
  private String hsNmb; //HS Code Name (Short)
  private String hsNm; //HS Code Name
}
