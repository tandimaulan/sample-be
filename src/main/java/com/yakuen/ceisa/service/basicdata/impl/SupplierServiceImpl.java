package com.yakuen.ceisa.service.basicdata.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yakuen.ceisa.mapper.basicdata.SupplierMapper;
import com.yakuen.ceisa.model.basicdata.Supplier;
import com.yakuen.ceisa.service.basicdata.SupplierService;

import lombok.RequiredArgsConstructor;

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
