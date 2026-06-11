package com.yakuen.ceisa.service.basicdata.impl;

import com.yakuen.ceisa.mapper.basicdata.GuestMapper;
import com.yakuen.ceisa.model.basicdata.Guest;
import com.yakuen.ceisa.service.basicdata.GuestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

  private final GuestMapper mapper;

  @Override
  public List<Guest> getGuests() {
    return mapper.findAll();
  }
}
