package com.yakuen.ceisa.controller.itinventory;

import com.yakuen.ceisa.model.itinventory.WipInventory;
import com.yakuen.ceisa.service.itinventory.WipInventoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wip")
@RequiredArgsConstructor
public class WipController {

  private final WipInventoryService service;

  @PostMapping("/all")
  public ResponseEntity<?> getWips() {
    try {
      List<WipInventory> data = service.getWips();
      return ResponseEntity.ok(data);
    } catch (Exception e) {
      Map<String, String> error = new HashMap<>();
      error.put("message", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        error
      );
    }
  }
}
