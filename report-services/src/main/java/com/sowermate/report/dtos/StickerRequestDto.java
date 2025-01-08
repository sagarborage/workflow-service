package com.sowermate.report.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StickerRequestDto {
    String tenantUuid;
    String companyUuid;
    String batchItemUuid;
    String batchUuid;
}
