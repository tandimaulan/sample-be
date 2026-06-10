package com.yakuen.ceisa.service.basicdata;

import java.util.List;

import com.yakuen.ceisa.model.basicdata.Supplier;


public interface SupplierService {
    public List<Supplier> getSuppliers();
    public Supplier getSupplierById(String id);
    public Supplier getSupplierByName(String name);
}
