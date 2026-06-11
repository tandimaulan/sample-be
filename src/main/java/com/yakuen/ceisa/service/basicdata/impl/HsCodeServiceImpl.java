package com.yakuen.ceisa.service.basicdata.impl;

import com.yakuen.ceisa.mapper.basicdata.HsCodeMapper;
import com.yakuen.ceisa.model.basicdata.HsCode;
import com.yakuen.ceisa.service.basicdata.HsCodeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HsCodeServiceImpl implements HsCodeService {

  private final HsCodeMapper mapper;

  @Override
  public List<HsCode> getHsCodes() {
    return mapper.findAll();
  }
}
