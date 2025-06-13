package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.value.ServiceRateValue;

import java.util.List;

public interface ServiceRateService {
    public ServiceRateValue createServiceRate(ServiceRateValue serviceRateValue);

    public ServiceRateValue editServiceRate(ServiceRateValue serviceRateValue);

    public List<ServiceRateValue> getAllServiceRate(String tenantUuid);

    public ServiceRateValue getServiceRate(String tenantUuid, String serviceRateUuid);

    public ServiceRateValue deleteServiceRate(String tenantUuid, String serviceRateUuid);
}
