package com.yakuen.ceisa.model.basicdata;

import lombok.Data;

@Data
public class Guest {
    private String custNo;//Customer Number
    private String customNo;//Custom Number
    private String localMk;//Local Mark
    private String useMk;//Use Mark
    private String currNo;//Currency Number
    private String typeCd;//Type Code
    private String custNm;//Customer Name
    private String custNms;//Customer Name (Short)
    private String ctryNm;//Country Name
    private String areaId;//Area ID
}