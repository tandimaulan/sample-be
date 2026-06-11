package com.yakuen.ceisa.service.basicdata;

import com.yakuen.ceisa.model.basicdata.Supplier;
import java.util.List;

public interface SupplierService {
  public List<Supplier> getSuppliers();
  public Supplier getSupplierById(String id);
  public Supplier getSupplierByName(String name);
}
