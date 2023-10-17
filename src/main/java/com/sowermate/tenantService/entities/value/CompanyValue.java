package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.CompanyEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyValue extends BaseValue {

    private String tenantUuid;
    private String companyTypeUuid;
    private String companyName;
    private String cin;
    private String gstin;
    private String tan;
    private String pan;
    private List<AddressValue> addresses;

    public CompanyEntity toEntity() {
        return CompanyEntity.newBuilder()
                .uuid(getUuid())
                .companyName(getCompanyName())
                .cin(getCin())
                .gstin(getGstin())
                .tan(getTan())
                .pan(getPan())
                .isActive(getIsActive())
                .build();
    }
}
