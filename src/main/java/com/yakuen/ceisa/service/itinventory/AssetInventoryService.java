//Inventory Asset
package com.yakuen.ceisa.service.itinventory;

import java.util.List;

public interface AssetInventoryService {
  public List<String> getAssetInventoryData();
  public String exportAssetInventoryToExcel();
}
