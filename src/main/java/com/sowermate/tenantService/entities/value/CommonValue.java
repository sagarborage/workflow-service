package com.sowermate.tenantService.entities.value;

import lombok.Data;

import java.util.Date;
@Data
public class CommonValue  {
    protected  String uuid;
    protected Date createdDttm;
    protected Date updatedDttm;

}
