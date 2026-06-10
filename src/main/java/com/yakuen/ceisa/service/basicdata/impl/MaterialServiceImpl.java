package com.yakuen.ceisa.service.basicdata.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yakuen.ceisa.mapper.basicdata.MaterialMapper;
import com.yakuen.ceisa.model.basicdata.Material;
import com.yakuen.ceisa.service.basicdata.MaterialService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    private final MaterialMapper mapper;

    @Override
    public List<Material> getMaterials() {
        return mapper.findAll();
    }
    
}
