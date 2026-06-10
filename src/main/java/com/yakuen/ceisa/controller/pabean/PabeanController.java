package com.yakuen.ceisa.controller.pabean;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yakuen.ceisa.dto.pabean.PabeanFilter;
import com.yakuen.ceisa.model.pabean.Inbound;
import com.yakuen.ceisa.service.pabean.PabeanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pabean")
@RequiredArgsConstructor
public class PabeanController {

    private final PabeanService pabeanService;

    // Get inbound data with filter (POST)
    @PostMapping("/inbound")
    public List<Inbound> getInbound(@RequestBody(required = false) PabeanFilter filter) {
        return pabeanService.getInboundData(filter);
    }
    
    // Export inbound to Excel with filter (POST)
    @PostMapping("/inbound/export")
    public Map<String, String> exportInboundToExcel(@RequestBody(required = false) PabeanFilter filter) {
        String base64 = pabeanService.exportInboundDataToExcel(filter);
        
        String fileName = "Laporan_Inbound_" + LocalDate.now() + ".xlsx";
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("fileName", fileName);
        response.put("base64", base64);
        
        return response;
    }
}