package com.yakuen.ceisa.service.basicdata.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yakuen.ceisa.mapper.basicdata.HsCodeMapper;
import com.yakuen.ceisa.model.basicdata.HsCode;
import com.yakuen.ceisa.service.basicdata.HsCodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HsCodeServiceImpl implements HsCodeService {
    private final HsCodeMapper mapper;
    @Override
    public List<HsCode> getHsCodes() {
        return mapper.findAll();
    }
    
}
