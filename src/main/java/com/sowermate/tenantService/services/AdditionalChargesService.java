package com.sowermate.tenantService.services;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import java.util.List;

public interface AdditionalChargesService {
    public AdditionalChargesValue saveAdditionalCharges(AdditionalChargesValue additionalChargesValue) throws Exception;

    public List<AdditionalChargesValue> getAllAdditionalCharges(String tenantUuid) throws Exception;
    public AdditionalChargesValue editAdditionalCharges(AdditionalChargesValue  additionalChargesValue) throws Exception;

    public AdditionalChargesValue getAdditionalCharges(String tenantUuid,String additionalChargesUuid) throws Exception;

   int deleteAdditionalCharges(String tenantUuid,String additionalChargesUuid)throws Exception;

}
