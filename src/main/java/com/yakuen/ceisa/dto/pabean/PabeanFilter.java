package com.yakuen.ceisa.dto.pabean;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PabeanFilter {

  private LocalDateTime startDate; // Tanggal awal (format: YYYYMMDD)
  private LocalDateTime endDate; // Tanggal akhir (format: YYYYMMDD)
  private String typeDoc;
}
