package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ServiceRateValue;
import java.util.List;

public interface ServiceRateService {

    public ServiceRateValue createServiceRate(ServiceRateValue serviceRateValue) throws Exception;

    public ServiceRateValue editServiceRate(ServiceRateValue serviceRateValue) throws Exception;

    public List<ServiceRateValue> getAllServiceRate() throws Exception;


    public ServiceRateValue getServiceRate(String serviceRateUuid) throws Exception;

    public ServiceRateValue deleteServiceRate(String serviceRateUuid)throws Exception;

}
