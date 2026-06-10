//BC Masuk
package com.yakuen.ceisa.service.itinventory;

import java.util.List;

import com.yakuen.ceisa.dto.pabean.PabeanFilter;
import com.yakuen.ceisa.model.itinventory.ItBcInbound;
import com.yakuen.ceisa.model.itinventory.ItBcOutbound;

public interface ItBcInOutboundService {
    public List<ItBcInbound> getInboundData(PabeanFilter filter);
    public String exportInboundDataToExcel(PabeanFilter filter);
    public List<ItBcOutbound> getOutboundData(PabeanFilter filter);
    public String exportOutboundDataToExcel(PabeanFilter filter);
}
