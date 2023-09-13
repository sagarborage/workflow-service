package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ServiceRateValue;
import java.util.List;

public interface ServiceRateService {

    public ServiceRateValue createServiceRate(ServiceRateValue serviceRateValue);

    public ServiceRateValue editServiceRate(ServiceRateValue serviceRateValue);

    public List<ServiceRateValue> getAllServiceRate(String tenantUuid);


    public ServiceRateValue getServiceRate(String tenantUuid,String serviceRateUuid);

    public ServiceRateValue deleteServiceRate(String tenantUuid,String serviceRateUuid);

}
