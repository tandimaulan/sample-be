//BC Masuk
package com.yakuen.ceisa.service.itinventory;

import com.yakuen.ceisa.dto.pabean.PabeanFilter;
import com.yakuen.ceisa.model.itinventory.ItBcInbound;
import com.yakuen.ceisa.model.itinventory.ItBcOutbound;
import java.util.List;

public interface ItBcInOutboundService {
  public List<ItBcInbound> getInboundData(PabeanFilter filter);
  public String exportInboundDataToExcel(PabeanFilter filter);
  public List<ItBcOutbound> getOutboundData(PabeanFilter filter);
  public String exportOutboundDataToExcel(PabeanFilter filter);
}
