package com.yakuen.ceisa.service.pabean;

import java.util.List;

import com.yakuen.ceisa.dto.pabean.PabeanFilter;
import com.yakuen.ceisa.model.pabean.Inbound;
import com.yakuen.ceisa.model.pabean.Outbound;

public interface PabeanService {

    public List<Inbound> getInboundData(PabeanFilter filter);
    public List<Outbound> getOutboundData(PabeanFilter filter);
    public String exportInboundDataToExcel(PabeanFilter filter);
    public String exportOutboundDataToExcel(PabeanFilter filter);
    
}
