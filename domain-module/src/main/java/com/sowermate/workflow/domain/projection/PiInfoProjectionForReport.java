package com.sowermate.workflow.domain.projection;

import java.time.LocalDateTime;

public interface PiInfoProjectionForReport {
    String getPiNo();

    String getPartyName();

    String getPartyBillToName();

    String getPartyBillToUuid();

    LocalDateTime getPiDate();
}
