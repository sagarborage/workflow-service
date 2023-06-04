package com.sowermate.tenantService.services;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import java.util.List;

public interface AdditionalChargesService {
    public AdditionalChargesValue SaveAdditionalCharges(AdditionalChargesValue additionalChargesValue) throws Exception;

    public List<AdditionalChargesValue> getAllAdditionalCharges() throws Exception;
    public AdditionalChargesValue editAdditionalCharges(AdditionalChargesValue  additionalChargesValue) throws Exception;

    public AdditionalChargesValue getAdditionalCharges(String uuid) throws Exception;

    public AdditionalChargesValue deleteAdditionalCharges(String uuid)throws Exception;

}
