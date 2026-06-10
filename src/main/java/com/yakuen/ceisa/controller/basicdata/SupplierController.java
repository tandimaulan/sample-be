package com.yakuen.ceisa.controller.basicdata;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yakuen.ceisa.model.basicdata.Supplier;
import com.yakuen.ceisa.service.basicdata.SupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping("/all")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getSuppliers();
    }
}
