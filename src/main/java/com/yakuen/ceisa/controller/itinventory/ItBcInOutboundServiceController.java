package com.yakuen.ceisa.controller.itinventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yakuen.ceisa.dto.pabean.PabeanFilter;
import com.yakuen.ceisa.model.itinventory.ItBcInbound;
import com.yakuen.ceisa.model.itinventory.ItBcOutbound;
import com.yakuen.ceisa.service.itinventory.ItBcInOutboundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/itinventory/bc-inoutbound")
@RequiredArgsConstructor
public class ItBcInOutboundServiceController {
    private final ItBcInOutboundService itBcInOutboundService;
    
    @PostMapping("/itbcinbound")
    public ResponseEntity<?> getInboundData(@RequestBody PabeanFilter filter) {
        try {
            List<ItBcInbound> data = itBcInOutboundService.getInboundData(filter);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/itbcoutbound")
    public ResponseEntity<?> getOutboundData(@RequestBody PabeanFilter filter) {
        try {
            List<ItBcOutbound> data = itBcInOutboundService.getOutboundData(filter);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
   @PostMapping("/itbcinbound/export")
    public ResponseEntity<?> exportInboundDataToExcel(@RequestBody PabeanFilter filter) {
        try {
            String base64Data = itBcInOutboundService.exportInboundDataToExcel(filter);
            
            if (base64Data == null || base64Data.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Tidak ada data untuk diexport");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            // Return sebagai Map dengan key "data"
            Map<String, String> response = new HashMap<>();
            response.put("data", base64Data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Export gagal: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @PostMapping("/itbcoutbound/export")
    public ResponseEntity<?> exportOutboundDataToExcel(@RequestBody PabeanFilter filter) {
        try {
            String base64Data = itBcInOutboundService.exportOutboundDataToExcel(filter);
            
            if (base64Data == null || base64Data.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Tidak ada data untuk diexport");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("data", base64Data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Export gagal: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}