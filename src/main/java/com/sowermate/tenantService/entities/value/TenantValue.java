package com.sowermate.tenantService.entities.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sowermate.tenantService.entities.TenantEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Jacksonized
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantValue {

    private Integer tenantId;
    private String uuid;
    private String tenantName;
    private String address;
    private String city;
    private String state;
    private int countryId;
    private String pinCode;
    private String phoneNumber;
    private String emailId;
    private Date activationDate;
    private Date expiryDate;
    private int gracePeriod;
    private Boolean isActive = true;

    private List<CompanyValue> companyValues;
    private List<AdditionalChargesValue> additionalChargesValues;
    private List<ServiceRateValue> serviceRateValues;
    private List<GlassTypeValue> glassTypeValues;
    private List<GlassThicknessValue> glassThicknessValues;
    private List<PiTypeValue> PiTypeValues;
    private List<ConfirmThroughValue> confirmThroughValues;
    private List<CompanyTypeValue> companyTypeValues;
    private List<ProFormaInvoiceValue> proFormaInvoiceValues;
    private List<StatusValue> statusValues;
    private List<AddressTypeValue> addressTypeValues;

    public TenantEntity toEntity() {
        return TenantEntity.newBuilder()
                .tenantId(getTenantId())
                .uuid(getUuid())
                .tenantName(getTenantName())
                .address(getAddress())
                .city(getCity())
                .state(getState())
                .countryId(getCountryId())
                .pinCode(getPinCode())
                .phoneNumber(getPhoneNumber())
                .emailId(getEmailId())
                .activationDate(getActivationDate())
                .expiryDate(getExpiryDate())
                .gracePeriod(getGracePeriod())
                .isActive(getIsActive())
/*                .companyEntities(Optional.ofNullable(getCompanyValues()).map(values -> values.stream()
                        .map(CompanyValue::toEntity)
                        .collect(Collectors.toList())).orElse(Collections.emptyList()))*/
                .additionalChargesEntities(Optional.ofNullable(getAdditionalChargesValues()).map(values -> values.stream()
                        .map(AdditionalChargesValue::toEntity)
                        .collect(Collectors.toList())).orElse(Collections.emptyList()))
                // ... (other @OneToMany fields)
                .build();
    }
}
