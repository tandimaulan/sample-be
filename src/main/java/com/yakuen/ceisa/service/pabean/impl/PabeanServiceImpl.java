package com.yakuen.ceisa.service.pabean.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.yakuen.ceisa.dto.pabean.PabeanFilter;
import com.yakuen.ceisa.mapper.pabean.PabeanMapper;
import com.yakuen.ceisa.model.pabean.Inbound;
import com.yakuen.ceisa.model.pabean.Outbound;
import com.yakuen.ceisa.service.pabean.PabeanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PabeanServiceImpl implements PabeanService {

    private final PabeanMapper pabeanMapper;

    @Override
    public List<Inbound> getInboundData(PabeanFilter filter) {
        return pabeanMapper.getInboundData(filter);
    }

    @Override
    public List<Outbound> getOutboundData(PabeanFilter filter) {
        // TODO: Implement outbound
        return null;
    }

    @Override
    public String exportInboundDataToExcel(PabeanFilter filter) {
        try {
            List<Inbound> inboundList = getInboundData(filter);
            
            if (filter != null) {
                log.info("Export inbound dengan filter: startDate={}, endDate={}, typeDoc={}", 
                    filter.getStartDate(), filter.getEndDate(), filter.getTypeDoc());
            } else {
                log.info("Export semua data inbound (tanpa filter)");
            }
            
            if (inboundList == null || inboundList.isEmpty()) {
                log.warn("Tidak ada data inbound untuk diexport");
                return null;
            }
            
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Laporan Inbound");
            
            // Style untuk header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setWrapText(true);
            
            // ==================== BARIS 1 (Header Utama) ====================
            Row row1 = sheet.createRow(0);
            
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 7, 7));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 8, 8));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 10, 10));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 11, 11));
            
            String[] mainHeaders = {
                "No", "Jenis Dokumen", "Dokumen Pabean", "", 
                "Bukti Penerimaan Barang", "", "Pemasok/Pengirim", 
                "Kode Barang", "Nama Barang", "Sat", "Jumlah", "Nilai Barang"
            };
            
            for (int i = 0; i < mainHeaders.length; i++) {
                Cell cell = row1.createCell(i);
                cell.setCellValue(mainHeaders[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // ==================== BARIS 2 (Sub Header) ====================
            Row row2 = sheet.createRow(1);
            
            String[] subHeaders = {
                "", "", "Nomor", "Tanggal", "Nomor", "Tanggal", 
                "", "", "", "", "", ""
            };
            
            for (int i = 0; i < subHeaders.length; i++) {
                Cell cell = row2.createCell(i);
                cell.setCellValue(subHeaders[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // ==================== DATA ROWS ====================
            int rowNum = 2;
            int no = 1;
            
            for (Inbound inbound : inboundList) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(no++);
                row.createCell(1).setCellValue(inbound.getTypeDoc() != null ? inbound.getTypeDoc() : "");
                row.createCell(2).setCellValue(inbound.getCdNo() != null ? inbound.getCdNo() : "");
                row.createCell(3).setCellValue(formatDate(inbound.getDtCd()));
                row.createCell(4).setCellValue(inbound.getBillIo() != null ? inbound.getBillIo() : "");
                row.createCell(5).setCellValue(formatDate(inbound.getDtIo()));
                row.createCell(6).setCellValue(inbound.getSuppNm() != null ? inbound.getSuppNm() : "");
                row.createCell(7).setCellValue(inbound.getMatNo() != null ? inbound.getMatNo() : "");
                row.createCell(8).setCellValue(inbound.getMatNm() != null ? inbound.getMatNm() : "");
                row.createCell(9).setCellValue(inbound.getSat() != null ? inbound.getSat() : "");
                row.createCell(10).setCellValue(inbound.getQtyIo() != null ? inbound.getQtyIo().doubleValue() : 0);
                row.createCell(11).setCellValue("");
            }
            
            // ==================== SET LEBAR KOLOM ====================
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 5000);
            sheet.setColumnWidth(8, 6000);
            sheet.setColumnWidth(9, 3000);
            sheet.setColumnWidth(10, 4000);
            sheet.setColumnWidth(11, 5000);
            
            // ==================== KONVERSI KE BASE64 ====================
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();
            
            byte[] excelBytes = baos.toByteArray();
            String base64Excel = Base64.getEncoder().encodeToString(excelBytes);
            
            log.info("Export inbound ke Excel sukses, size: {} bytes, total data: {}", excelBytes.length, inboundList.size());
            
            return base64Excel;
            
        } catch (IOException e) {
            log.error("Gagal export data inbound ke Excel", e);
            throw new RuntimeException("Gagal export data inbound ke Excel: " + e.getMessage());
        }
    }
    
    private String formatDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }
        try {
            String year = dateStr.substring(0, 4);
            String month = dateStr.substring(4, 6);
            String day = dateStr.substring(6, 8);
            return day + "/" + month + "/" + year;
        } catch (Exception e) {
            return dateStr;
        }
    }

    @Override
    public String exportOutboundDataToExcel(PabeanFilter filter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
