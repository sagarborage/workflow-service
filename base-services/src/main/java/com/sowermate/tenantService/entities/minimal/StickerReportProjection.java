package com.sowermate.tenantService.entities.minimal;

import java.time.LocalDate;
import java.util.List;

public interface StickerReportProjection {
    String getPiNo();
    String getThickness();
    String getPartyName();
    String getSize();
    LocalDate getDate();
    String getStickerNumber();
}
