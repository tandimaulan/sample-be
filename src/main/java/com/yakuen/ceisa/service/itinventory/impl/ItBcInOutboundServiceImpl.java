package com.yakuen.ceisa.service.itinventory.impl;

import com.yakuen.ceisa.dto.pabean.PabeanFilter;
import com.yakuen.ceisa.mapper.itinventory.InOutboundMapper;
import com.yakuen.ceisa.model.itinventory.ItBcInbound;
import com.yakuen.ceisa.model.itinventory.ItBcOutbound;
import com.yakuen.ceisa.service.itinventory.ItBcInOutboundService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItBcInOutboundServiceImpl implements ItBcInOutboundService {

  private static final int HEADER_ROW_INDEX = 5;
  private static final int SUB_HEADER_ROW_INDEX = 6;
  private static final int DATA_START_ROW_INDEX = 7;

  private final InOutboundMapper inOutboundMapper;

  @Override
  public List<ItBcInbound> getInboundData(PabeanFilter filter) {
    return inOutboundMapper.getInboundData(filter);
  }

  @Override
  public List<ItBcOutbound> getOutboundData(PabeanFilter filter) {
    return inOutboundMapper.getOutboundData(filter);
  }

  @Override
  public String exportInboundDataToExcel(PabeanFilter filter) {
    try {
      List<ItBcInbound> inboundList = getInboundData(filter);

      if (filter != null) {
        log.info(
          "Export inbound dengan filter: startDate={}, endDate={}, typeDoc={}",
          filter.getStartDate(),
          filter.getEndDate(),
          filter.getTypeDoc()
        );
      } else {
        log.info("Export semua data inbound (tanpa filter)");
      }

      if (inboundList == null || inboundList.isEmpty()) {
        log.warn("Tidak ada data inbound untuk diexport");
        return null;
      }

      ByteArrayOutputStream baos;
      try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("LAPORAN PEMASUKAN BARANG");
        createReportTemplate(
          workbook,
          sheet,
          "LAPORAN PEMASUKAN BARANG PER DOKUMEN PABEAN",
          "Bukti Penerimaan Barang",
          "Pemasok/Pengirim",
          filter
        );
        CellStyle centerStyle = createDataStyle(workbook, false);
        CellStyle leftStyle = createDataStyle(workbook, true);
        fillDataRows(sheet, inboundList, inbound -> {
          Row row = inbound.row();
          ItBcInbound data = inbound.value();
          setCellValueWithStyle(row, 1, data.getTypeDoc(), centerStyle);
          setCellValueWithStyle(row, 2, data.getCdNo(), centerStyle);
          setCellValueWithStyle(
            row,
            3,
            formatDate(data.getDtCd()),
            centerStyle
          );
          setCellValueWithStyle(row, 4, data.getBillIo(), centerStyle);
          setCellValueWithStyle(
            row,
            5,
            formatDate(data.getDtIo()),
            centerStyle
          );
          setCellValueWithStyle(row, 6, data.getSuppNm(), leftStyle);
          setCellValueWithStyle(row, 7, data.getMatNo(), centerStyle);
          setCellValueWithStyle(row, 8, data.getMatNm(), leftStyle);
          setCellValueWithStyle(row, 9, data.getSat(), centerStyle);
          setCellValueWithStyle(row, 10, data.getQtyIo(), centerStyle);
          setCellValueWithStyle(row, 11, "", centerStyle);
        });
        baos = new ByteArrayOutputStream();
        workbook.write(baos);
      }

      byte[] excelBytes = baos.toByteArray();
      String base64Excel = Base64.getEncoder().encodeToString(excelBytes);

      log.info(
        "Export inbound ke Excel sukses, size: {} bytes, total data: {}",
        excelBytes.length,
        inboundList.size()
      );

      return base64Excel;
    } catch (IOException e) {
      log.error("Gagal export data inbound ke Excel", e);
      throw new RuntimeException(
        "Gagal export data inbound ke Excel: " + e.getMessage()
      );
    }
  }

  @Override
  public String exportOutboundDataToExcel(PabeanFilter filter) {
    try {
      List<ItBcOutbound> outboundList = getOutboundData(filter);

      if (filter != null) {
        log.info(
          "Export outbound dengan filter: startDate={}, endDate={}, typeDoc={}",
          filter.getStartDate(),
          filter.getEndDate(),
          filter.getTypeDoc()
        );
      } else {
        log.info("Export semua data outbound (tanpa filter)");
      }

      if (outboundList == null || outboundList.isEmpty()) {
        log.warn("Tidak ada data outbound untuk diexport");
        return null;
      }

      ByteArrayOutputStream baos;
      try (Workbook workbook = new XSSFWorkbook()) {
        Sheet sheet = workbook.createSheet("LAPORAN PENGELUARAN BARANG");
        createReportTemplate(
          workbook,
          sheet,
          "LAPORAN PENGELUARAN BARANG PER DOKUMEN PABEAN",
          "Bukti Pengeluaran Barang",
          "Pembeli/Penerima",
          filter
        );
        CellStyle centerStyle = createDataStyle(workbook, false);
        CellStyle leftStyle = createDataStyle(workbook, true);
        fillDataRows(sheet, outboundList, outbound -> {
          Row row = outbound.row();
          ItBcOutbound data = outbound.value();
          setCellValueWithStyle(row, 1, data.getJenisDok(), centerStyle);
          setCellValueWithStyle(row, 2, data.getCdNo(), centerStyle);
          setCellValueWithStyle(
            row,
            3,
            formatDate(data.getDtCd()),
            centerStyle
          );
          setCellValueWithStyle(row, 4, data.getBillIo(), centerStyle);
          setCellValueWithStyle(
            row,
            5,
            formatDate(data.getDtIo()),
            centerStyle
          );
          setCellValueWithStyle(row, 6, data.getCustNm(), leftStyle);
          setCellValueWithStyle(row, 7, data.getMatNo(), centerStyle);
          setCellValueWithStyle(row, 8, data.getMatNm(), leftStyle);
          setCellValueWithStyle(row, 9, data.getUnitMat(), centerStyle);
          setCellValueWithStyle(row, 10, data.getQtyIo(), centerStyle);
          setCellValueWithStyle(row, 11, data.getWtIo(), centerStyle);
        });
        baos = new ByteArrayOutputStream();
        workbook.write(baos);
      }

      byte[] excelBytes = baos.toByteArray();
      String base64Excel = Base64.getEncoder().encodeToString(excelBytes);

      log.info(
        "Export outbound ke Excel sukses, size: {} bytes, total data: {}",
        excelBytes.length,
        outboundList.size()
      );

      return base64Excel;
    } catch (IOException e) {
      log.error("Gagal export data outbound ke Excel", e);
      throw new RuntimeException(
        "Gagal export data outbound ke Excel: " + e.getMessage()
      );
    }
  }

  private void createReportTemplate(
    Workbook workbook,
    Sheet sheet,
    String reportTitle,
    String proofHeader,
    String partnerHeader,
    PabeanFilter filter
  ) {
    for (int rowIndex = 0; rowIndex <= SUB_HEADER_ROW_INDEX; rowIndex++) {
      Row row = sheet.createRow(rowIndex);
      row.setHeightInPoints(15);
    }

    Row titleRow = sheet.getRow(0);
    sheet.addMergedRegion(new CellRangeAddress(0, 4, 0, 11));
    Cell titleCell = titleRow.createCell(0);
    titleCell.setCellValue(
      "KAWASAN BERIKAT................................\n" +
        reportTitle +
        "\n" +
        "PERIODE: " +
        formatPeriode(filter != null ? filter.getStartDate() : null) +
        " S.D " +
        formatPeriode(filter != null ? filter.getEndDate() : null)
    );
    titleCell.setCellStyle(createTitleStyle(workbook));

    Row headerRow = sheet.getRow(HEADER_ROW_INDEX);
    Row subHeaderRow = sheet.getRow(SUB_HEADER_ROW_INDEX);
    CellStyle headerStyle = createHeaderStyle(
      workbook,
      true,
      HorizontalAlignment.CENTER
    );

    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, SUB_HEADER_ROW_INDEX, 0, 0)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, SUB_HEADER_ROW_INDEX, 1, 1)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, HEADER_ROW_INDEX, 2, 3)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, HEADER_ROW_INDEX, 4, 5)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, SUB_HEADER_ROW_INDEX, 6, 6)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, SUB_HEADER_ROW_INDEX, 7, 7)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, SUB_HEADER_ROW_INDEX, 8, 8)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, SUB_HEADER_ROW_INDEX, 9, 9)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, SUB_HEADER_ROW_INDEX, 10, 10)
    );
    sheet.addMergedRegion(
      new CellRangeAddress(HEADER_ROW_INDEX, SUB_HEADER_ROW_INDEX, 11, 11)
    );

    String[] mainHeaders = {
      "No",
      "Jenis Dokumen",
      "Dokumen Pabean",
      "",
      proofHeader,
      "",
      partnerHeader,
      "Kode Barang",
      "Nama Barang",
      "Sat",
      "Jumlah",
      "Nilai Barang",
    };
    String[] subHeaders = {
      "",
      "",
      "Nomor",
      "Tanggal",
      "Nomor",
      "Tanggal",
      "",
      "",
      "",
      "",
      "",
      "",
    };

    for (int i = 0; i < mainHeaders.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(mainHeaders[i]);
      cell.setCellStyle(headerStyle);

      Cell subCell = subHeaderRow.createCell(i);
      subCell.setCellValue(subHeaders[i]);
      subCell.setCellStyle(headerStyle);
    }

    setColumnWidths(sheet);
  }

  private <T> void fillDataRows(
    Sheet sheet,
    List<T> dataList,
    Consumer<RowData<T>> rowFiller
  ) {
    CellStyle centerStyle = createDataStyle(sheet.getWorkbook(), false);
    int rowIndex = DATA_START_ROW_INDEX;
    int number = 1;
    for (T item : dataList) {
      Row row = sheet.createRow(rowIndex++);
      row.setHeightInPoints(15);
      setCellValueWithStyle(row, 0, number++, centerStyle);
      rowFiller.accept(new RowData<>(row, item));
    }
  }

  private CellStyle createTitleStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setWrapText(true);
    style.setBorderTop(BorderStyle.THIN);
    return style;
  }

  private CellStyle createHeaderStyle(
    Workbook workbook,
    boolean bold,
    HorizontalAlignment alignment
  ) {
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(bold);
    style.setFont(font);
    style.setAlignment(alignment);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderTop(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setWrapText(true);
    return style;
  }

  private CellStyle createHeaderStyle(Workbook workbook, boolean bold) {
    return createHeaderStyle(workbook, bold, HorizontalAlignment.CENTER);
  }

  private CellStyle createDataStyle(Workbook workbook, boolean leftAlign) {
    CellStyle style = workbook.createCellStyle();
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderTop(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setVerticalAlignment(VerticalAlignment.CENTER);

    if (leftAlign) {
      style.setAlignment(HorizontalAlignment.LEFT);
    } else {
      style.setAlignment(HorizontalAlignment.CENTER);
    }

    return style;
  }

  private void setCellValueWithStyle(
    Row row,
    int col,
    Object value,
    CellStyle style
  ) {
    Cell cell = row.createCell(col);
    if (value instanceof Number) {
      double numValue = ((Number) value).doubleValue();
      if (numValue == 0) {
        cell.setCellValue("");
      } else {
        cell.setCellValue(numValue);
      }
    } else {
      cell.setCellValue(value != null ? value.toString() : "");
    }
    cell.setCellStyle(style);
  }

  private void setColumnWidths(Sheet sheet) {
    int[] widths = {
      2816,
      4817,
      5817,
      3818,
      5817,
      3818,
      6817,
      4817,
      7817,
      2816,
      4817,
      6817,
    };
    for (int i = 0; i < widths.length; i++) {
      sheet.setColumnWidth(i, widths[i]);
    }
  }

  private static class RowData<T> {

    private final Row row;
    private final T value;

    private RowData(Row row, T value) {
      this.row = row;
      this.value = value;
    }

    private Row row() {
      return row;
    }

    private T value() {
      return value;
    }
  }

  private String formatDate(String dateStr) {
    if (dateStr == null || dateStr.isEmpty()) {
      return "";
    }
    try {
      if (dateStr.length() >= 8) {
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(4, 6);
        String day = dateStr.substring(6, 8);
        return day + "/" + month + "/" + year;
      }
      return dateStr;
    } catch (Exception e) {
      return dateStr;
    }
  }

  private String formatPeriode(LocalDateTime date) {
    if (date == null) {
      return "..../..../....";
    }
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      return date.format(formatter);
    } catch (Exception e) {
      return "..../..../....";
    }
  }
}
