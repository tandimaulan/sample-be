package com.yakuen.ceisa.controller.basicdata;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yakuen.ceisa.model.basicdata.HsCode;
import com.yakuen.ceisa.service.basicdata.HsCodeService;

import lombok.RequiredArgsConstructor;

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
