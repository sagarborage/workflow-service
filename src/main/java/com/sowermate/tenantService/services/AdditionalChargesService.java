package com.sowermate.tenantService.services;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import java.util.List;

public interface AdditionalChargesService {
    public AdditionalChargesValue saveAdditionalCharges(AdditionalChargesValue additionalChargesValue);

    public List<AdditionalChargesValue> getAllAdditionalCharges(String tenantUuid);
    public AdditionalChargesValue editAdditionalCharges(AdditionalChargesValue  additionalChargesValue);

    public AdditionalChargesValue getAdditionalCharges(String tenantUuid,String additionalChargesUuid);

   int deleteAdditionalCharges(String tenantUuid,String additionalChargesUuid);

}
