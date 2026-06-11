package com.yakuen.ceisa.service.basicdata.impl;

import com.yakuen.ceisa.mapper.basicdata.SupplierMapper;
import com.yakuen.ceisa.model.basicdata.Supplier;
import com.yakuen.ceisa.service.basicdata.SupplierService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

  private final SupplierMapper mapper;

  @Override
  public List<Supplier> getSuppliers() {
    return mapper.findAll();
  }

  @Override
  public Supplier getSupplierById(String id) {
    // return mapper.findById(id);
    return null;
  }

  @Override
  public Supplier getSupplierByName(String name) {
    // return mapper.findByName(name);
    return null;
  }
}
