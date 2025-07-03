package com.sowermate.workflow.report.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class StickerReportDto {
    private String piNo;
    private String thickness;
    private String partyName;
    private String size;
    private LocalDate date;
    private String stickerNumber;
    private List<StickerReportDto> abc;
}
