package com.yakuen.ceisa.service.basicdata.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yakuen.ceisa.mapper.basicdata.GuestMapper;
import com.yakuen.ceisa.model.basicdata.Guest;
import com.yakuen.ceisa.service.basicdata.GuestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final GuestMapper mapper;

    @Override
    public List<Guest> getGuests() {
        return mapper.findAll();
    }
}
    
