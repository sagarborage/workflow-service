package com.sowermate.workflow.domain.entities.value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketManipulationValue {
    private String tenantUuid;

    private String proFormaInvoiceUUid;

    private String proFormaInvoiceItemUUid;

    private String currentBucket;

    private Integer quantity;

    private Integer workOrderNo;

    private String details;
}
