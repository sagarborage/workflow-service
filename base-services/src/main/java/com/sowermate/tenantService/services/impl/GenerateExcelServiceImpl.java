package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.common.GatePassExcelConstant;
import com.sowermate.tenantService.entities.common.PiExcelConstant;
import com.sowermate.tenantService.entities.common.WorkOrderExcelConstant;
import com.sowermate.tenantService.entities.value.GatePassExcelReport;
import com.sowermate.tenantService.entities.value.GatePassExcelReportData;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceExcelReport;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceExcelReportData;
import com.sowermate.tenantService.entities.value.WorkOrderExcelReport;
import com.sowermate.tenantService.entities.value.WorkOrderExcelReportData;
import com.sowermate.tenantService.services.GenerateExcelService;
import com.sowermate.tenantService.services.TenantService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Service
public class GenerateExcelServiceImpl implements GenerateExcelService {
    Integer totalPresentDays = 0;
    Float totalOverTime = 0.0f;
    @Autowired
    private TenantService tenantService;


    private String encodeWorkbookToBase64(Workbook workbook) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        }
    }

    private Row createRow(Sheet sheet, int rowNum, int height) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        row.setHeightInPoints(height);
        return row;
    }

    private void createCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void autoSizeAllColumns(int columnLength, Sheet sheet) {
        for (int i = 0; i < columnLength; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private CellStyle createCellStyle(Workbook workbook, boolean isBold, int fontSize, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(isBold);
        font.setFontHeightInPoints((short) fontSize);
        style.setFont(font);
        style.setAlignment(hAlign);
        style.setVerticalAlignment(vAlign);
        return style;
    }

    private CellStyle createBorderedCellStyle(Workbook workbook, boolean isBold, int fontSize, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
        CellStyle style = createCellStyle(workbook, isBold, fontSize, hAlign, vAlign);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createTenantStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        return style;
    }

    private CellStyle createDatesBetweenStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 8);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        return style;
    }

    private CellStyle createGeneratedOnStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 8);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        return style;
    }

    private CellStyle createReportsValueStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 8);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createTableHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    @Override
    public String generateProformaInvoiceReportsExcelSheet(List<ProFormaInvoiceExcelReportData> proFormaInvoiceExcelReportDataList, ProFormaInvoiceExcelReport proFormaInvoiceExcelReport) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(PiExcelConstant.DATA);

        CellStyle tenantStyle = createTenantStyle(workbook);
        CellStyle datesBetweenStyle = createDatesBetweenStyle(workbook);
        CellStyle generatedOnStyle = createGeneratedOnStyle(workbook);
        CellStyle reportsValueStyle = createReportsValueStyle(workbook);
        CellStyle tableHeaderStyle = createTableHeaderStyle(workbook);

        int rowNum = addTitleRows(sheet, proFormaInvoiceExcelReport, tenantStyle, datesBetweenStyle, generatedOnStyle);
        addColumnHeaders(sheet, rowNum++, tableHeaderStyle);
        if (!proFormaInvoiceExcelReportDataList.isEmpty()) {
            populatePiData(sheet, rowNum, proFormaInvoiceExcelReportDataList, reportsValueStyle);
        }
        autoSizeAllColumns(10, sheet);
        return encodeWorkbookToBase64(workbook);
    }

    private void addColumnHeaders(Sheet sheet, int rowNum, CellStyle tableHeaderStyle) {
        Row headerRow = sheet.createRow(rowNum);
        String[] headers = {PiExcelConstant.SR_NO, PiExcelConstant.PI_NUMBER, PiExcelConstant.PARTY_NAME, PiExcelConstant.INVOICE_DATE, PiExcelConstant.AMOUNT, PiExcelConstant.USER, PiExcelConstant.STATUS};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(tableHeaderStyle);
            cell.setCellValue(headers[i]);
            sheet.autoSizeColumn(i);
        }
    }

    private int addTitleRows(Sheet sheet, ProFormaInvoiceExcelReport proFormaInvoiceExcelReport, CellStyle tenantStyle, CellStyle datesBetweenStyle, CellStyle generatedOnStyle) {
        int rowNum = 0;
        int columnLength = 6;

        String partyName = tenantService.getTenantName(proFormaInvoiceExcelReport.getTenantUuid());

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow1 = createRow(sheet, 0, 25);
        createCell(titleRow1, 0, partyName.toUpperCase(), tenantStyle);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow2 = createRow(sheet, rowNum - 1, 22);
        createCell(titleRow2, 0, PiExcelConstant.PRO_FORMA_INVOICE_REPORT, tenantStyle);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        createCell(createRow(sheet, rowNum, 15), 0,
                LocalDate.parse(proFormaInvoiceExcelReport.getFromDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + PiExcelConstant.TO + LocalDate.parse(proFormaInvoiceExcelReport.getToDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), datesBetweenStyle);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow4 = createRow(sheet, rowNum - 1, 15);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, h:mm:ss a");
        createCell(titleRow4, 0, PiExcelConstant.GENERATED_ON + LocalDateTime.now().format(dateTimeFormatter), generatedOnStyle);

        return rowNum;
    }

    private void populatePiData(Sheet sheet, int rowNum, List<ProFormaInvoiceExcelReportData> proFormaInvoiceExcelReportDataList, CellStyle reportsValueStyle) {
        int srNo = 1;

        for (ProFormaInvoiceExcelReportData proformaInvoice : proFormaInvoiceExcelReportDataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(srNo++);
            row.getCell(0).setCellStyle(reportsValueStyle);

            row.createCell(1).setCellValue(proformaInvoice.getPiNumber());
            row.getCell(1).setCellStyle(reportsValueStyle);

            row.createCell(2).setCellValue(proformaInvoice.getPartyName());
            row.getCell(2).setCellStyle(reportsValueStyle);

            row.createCell(3).setCellValue(proformaInvoice.getInvoiceDateTime());
            row.getCell(3).setCellStyle(reportsValueStyle);

            row.createCell(4).setCellValue(proformaInvoice.getAmount());
            row.getCell(4).setCellStyle(reportsValueStyle);

            row.createCell(5).setCellValue(proformaInvoice.getUser());
            row.getCell(5).setCellStyle(reportsValueStyle);

            row.createCell(6).setCellValue(proformaInvoice.getStatus());
            row.getCell(6).setCellStyle(reportsValueStyle);
        }
    }

    // Work order details excel report

    @Override
    public String generateWorkOrderDetailsExcelSheet(List<WorkOrderExcelReportData> workOrderExcelReportDataList, WorkOrderExcelReport workOrderExcelReport) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(WorkOrderExcelConstant.DATA);

        CellStyle tenantStyle = createTenantStyle(workbook);
        CellStyle datesBetweenStyle = createDatesBetweenStyle(workbook);
        CellStyle generatedOnStyle = createGeneratedOnStyle(workbook);
        CellStyle reportsValueStyle = createReportsValueStyle(workbook);
        CellStyle tableHeaderStyle = createTableHeaderStyle(workbook);

        int rowNum = addWorkOrderTitleRows(sheet, workOrderExcelReport, tenantStyle, datesBetweenStyle, generatedOnStyle);
        addWorkOrderColumnHeaders(sheet, rowNum++, tableHeaderStyle);
        if (!workOrderExcelReportDataList.isEmpty()) {
            populateWorkOrderData(sheet, rowNum, workOrderExcelReportDataList, reportsValueStyle);
        }
        autoSizeAllColumns(10, sheet);
        return encodeWorkbookToBase64(workbook);
    }

    private void addWorkOrderColumnHeaders(Sheet sheet, int rowNum, CellStyle tableHeaderStyle) {
        Row headerRow = sheet.createRow(rowNum);
        String[] headers = {WorkOrderExcelConstant.SR_NO, WorkOrderExcelConstant.FIRM_NAME, WorkOrderExcelConstant.PI_NUMBER, WorkOrderExcelConstant.PI_TYPE, WorkOrderExcelConstant.PARTY_NAME, WorkOrderExcelConstant.PI_DATE, WorkOrderExcelConstant.WORK_ORDER_DATE, WorkOrderExcelConstant.AMOUNT, WorkOrderExcelConstant.USER, WorkOrderExcelConstant.SQFT, WorkOrderExcelConstant.SQMTR};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(tableHeaderStyle);
            cell.setCellValue(headers[i]);
            sheet.autoSizeColumn(i);
        }
    }

    private int addWorkOrderTitleRows(Sheet sheet, WorkOrderExcelReport workOrderExcelReport, CellStyle tenantStyle, CellStyle datesBetweenStyle, CellStyle generatedOnStyle) {
        int rowNum = 0;
        int columnLength = 10;

        String partyName = tenantService.getTenantName(workOrderExcelReport.getTenantUuid());

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow1 = createRow(sheet, 0, 25);
        createCell(titleRow1, 0, partyName.toUpperCase(), tenantStyle);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow2 = createRow(sheet, rowNum - 1, 22);
        createCell(titleRow2, 0, WorkOrderExcelConstant.WORK_ORDER_REPORT, tenantStyle);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        createCell(createRow(sheet, rowNum, 15), 0,
                LocalDate.parse(workOrderExcelReport.getFromDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + WorkOrderExcelConstant.TO + LocalDate.parse(workOrderExcelReport.getToDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), datesBetweenStyle);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow4 = createRow(sheet, rowNum - 1, 15);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, h:mm:ss a");
        createCell(titleRow4, 0, WorkOrderExcelConstant.GENERATED_ON + LocalDateTime.now().format(dateTimeFormatter), generatedOnStyle);

        return rowNum;
    }

    private void populateWorkOrderData(Sheet sheet, int rowNum, List<WorkOrderExcelReportData> workOrderExcelReportDataList, CellStyle reportsValueStyle) {
        int srNo = 1;

        for (WorkOrderExcelReportData workOrderExcelReport : workOrderExcelReportDataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(srNo++);
            row.getCell(0).setCellStyle(reportsValueStyle);

            row.createCell(1).setCellValue(workOrderExcelReport.getFirmName());
            row.getCell(1).setCellStyle(reportsValueStyle);

            row.createCell(2).setCellValue(workOrderExcelReport.getPiNumber());
            row.getCell(2).setCellStyle(reportsValueStyle);

            row.createCell(3).setCellValue(workOrderExcelReport.getPiType());
            row.getCell(3).setCellStyle(reportsValueStyle);

            row.createCell(4).setCellValue(workOrderExcelReport.getPartyName());
            row.getCell(4).setCellStyle(reportsValueStyle);

            row.createCell(5).setCellValue(workOrderExcelReport.getPiDate());
            row.getCell(5).setCellStyle(reportsValueStyle);

            row.createCell(6).setCellValue(workOrderExcelReport.getWorkOrderDate());
            row.getCell(6).setCellStyle(reportsValueStyle);

            row.createCell(7).setCellValue(workOrderExcelReport.getAmount());
            row.getCell(7).setCellStyle(reportsValueStyle);

            row.createCell(8).setCellValue(workOrderExcelReport.getUser());
            row.getCell(8).setCellStyle(reportsValueStyle);

            if (workOrderExcelReport.getSQFT() != null) {
                row.createCell(9).setCellValue(workOrderExcelReport.getSQFT());
            } else {
                row.createCell(9).setCellValue("-");
            }
            row.getCell(9).setCellStyle(reportsValueStyle);

            if (workOrderExcelReport.getSQMTR() != null) {
                row.createCell(10).setCellValue(workOrderExcelReport.getSQMTR());
            } else {
                row.createCell(10).setCellValue("-");
            }
            row.getCell(10).setCellStyle(reportsValueStyle);

        }
    }

    // Work order details excel report

    @Override
    public String generateGatePassExcelSheet(List<GatePassExcelReportData> gatePassExcelReportDataList, GatePassExcelReport gatePassExcelReport) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(GatePassExcelConstant.DATA);

        CellStyle tenantStyle = createTenantStyle(workbook);
        CellStyle datesBetweenStyle = createDatesBetweenStyle(workbook);
        CellStyle generatedOnStyle = createGeneratedOnStyle(workbook);
        CellStyle reportsValueStyle = createReportsValueStyle(workbook);
        CellStyle tableHeaderStyle = createTableHeaderStyle(workbook);

        int rowNum = addGatePassTitleRows(sheet, gatePassExcelReport, tenantStyle, datesBetweenStyle, generatedOnStyle);
        addGatePassColumnHeaders(sheet, rowNum++, tableHeaderStyle);
        if (!gatePassExcelReportDataList.isEmpty()) {
            populateGatePassData(sheet, rowNum, gatePassExcelReportDataList, reportsValueStyle);
        }
        autoSizeAllColumns(8, sheet);
        return encodeWorkbookToBase64(workbook);
    }

    private void addGatePassColumnHeaders(Sheet sheet, int rowNum, CellStyle tableHeaderStyle) {
        Row headerRow = sheet.createRow(rowNum);
        String[] headers = {GatePassExcelConstant.SR_NO, GatePassExcelConstant.GATE_PASS_NO, GatePassExcelConstant.CREATED_BY, GatePassExcelConstant.PI_NUMBER, GatePassExcelConstant.PARTY_NAME, GatePassExcelConstant.FIRM_NAME, GatePassExcelConstant.VEHICLE_DETAILS, GatePassExcelConstant.QUANTITY, GatePassExcelConstant.DATE_TIME};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(tableHeaderStyle);
            cell.setCellValue(headers[i]);
            sheet.autoSizeColumn(i);
        }
    }

    private int addGatePassTitleRows(Sheet sheet, GatePassExcelReport gatePassExcelReport, CellStyle tenantStyle, CellStyle datesBetweenStyle, CellStyle generatedOnStyle) {
        int rowNum = 0;
        int columnLength = 8;

        String partyName = tenantService.getTenantName(gatePassExcelReport.getTenantUuid());

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow1 = createRow(sheet, 0, 25);
        createCell(titleRow1, 0, partyName.toUpperCase(), tenantStyle);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow2 = createRow(sheet, rowNum - 1, 22);
        createCell(titleRow2, 0, GatePassExcelConstant.GATE_PASS_REPORT, tenantStyle);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        createCell(createRow(sheet, rowNum, 15), 0,
                LocalDate.parse(gatePassExcelReport.getFromDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + GatePassExcelConstant.TO + LocalDate.parse(gatePassExcelReport.getToDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), datesBetweenStyle);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum++, 0, columnLength));
        Row titleRow4 = createRow(sheet, rowNum - 1, 15);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, h:mm:ss a");
        createCell(titleRow4, 0, GatePassExcelConstant.GENERATED_ON + LocalDateTime.now().format(dateTimeFormatter), generatedOnStyle);

        return rowNum;
    }

    private void populateGatePassData(Sheet sheet, int rowNum, List<GatePassExcelReportData> gatePassExcelReportDataList, CellStyle reportsValueStyle) {
        int srNo = 1;

        for (GatePassExcelReportData excelReportData : gatePassExcelReportDataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(srNo++);
            row.getCell(0).setCellStyle(reportsValueStyle);

            row.createCell(1).setCellValue(excelReportData.getGatePassNo());
            row.getCell(1).setCellStyle(reportsValueStyle);

            row.createCell(2).setCellValue(excelReportData.getCreatedBy());
            row.getCell(2).setCellStyle(reportsValueStyle);

            row.createCell(3).setCellValue(excelReportData.getPiNumber());
            row.getCell(3).setCellStyle(reportsValueStyle);

            row.createCell(4).setCellValue(excelReportData.getPartyName());
            row.getCell(4).setCellStyle(reportsValueStyle);

            row.createCell(5).setCellValue(excelReportData.getFirmName());
            row.getCell(5).setCellStyle(reportsValueStyle);

            row.createCell(6).setCellValue(excelReportData.getVehicleDetails());
            row.getCell(6).setCellStyle(reportsValueStyle);

            row.createCell(7).setCellValue(excelReportData.getQuantity());
            row.getCell(7).setCellStyle(reportsValueStyle);

            row.createCell(8).setCellValue(excelReportData.getDateTime());
            row.getCell(8).setCellStyle(reportsValueStyle);
        }
    }
}