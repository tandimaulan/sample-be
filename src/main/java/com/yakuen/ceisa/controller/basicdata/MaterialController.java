package com.yakuen.ceisa.controller.basicdata;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yakuen.ceisa.model.basicdata.Material;
import com.yakuen.ceisa.service.basicdata.MaterialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/material")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;

    @PostMapping("/all")
    public List<Material> getAllMaterials() {
        return materialService.getMaterials();
    }
}
