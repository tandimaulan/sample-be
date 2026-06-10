package com.yakuen.ceisa.model.basicdata;

import lombok.Data;

@Data
public class Supplier {
    private String suppNo;//Supplier Number
    private String venderNo;//Vendor Number
    private String localMk;//Local Mark
    private String useMk;//Use Mark
    private String currNo;//Currency Number
    private String typeCd;//Type Code
    private String suppNm;//Supplier Name
    private String suppNms;//Supplier Name (Short)
    private String ctryNm;//Country Name
    private String areaId;//Area ID
}
