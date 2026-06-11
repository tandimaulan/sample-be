package com.yakuen.ceisa.controller.basicdata;

import com.yakuen.ceisa.model.basicdata.HsCode;
import com.yakuen.ceisa.service.basicdata.HsCodeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hsCode")
@RequiredArgsConstructor
public class HsCodeController {

  private final HsCodeService hsCodeService;

  @PostMapping("/all")
  public List<HsCode> getAllHsCodes() {
    return hsCodeService.getHsCodes();
  }
}
