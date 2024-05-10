package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.JbCreationValue;

import java.util.List;

public interface JbCreationService {

    public JbCreationValue createJbCreation(JbCreationValue jbCreationValue);

    public JbCreationValue updateJbCreation(JbCreationValue jbCreationValue);

    public List<JbCreationValue> getAllJbCreation(String toughenBatchProcessUuid);

    public JbCreationValue getJbCreation(String toughenBatchProcessUuid, String jbCreationUuid);

    public JbCreationValue deleteJbCreation(String toughenBatchProcessUuid, String jbCreationUuid);
}
