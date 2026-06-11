package com.yakuen.ceisa.service.itinventory.impl;

import com.yakuen.ceisa.mapper.itinventory.WipMapper;
import com.yakuen.ceisa.model.itinventory.WipInventory;
import com.yakuen.ceisa.service.itinventory.WipInventoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WipInventoryServiceImpl implements WipInventoryService {

  private final WipMapper mapper;

  @Override
  public List<WipInventory> getWips() {
    return mapper.findAll();
  }
}
