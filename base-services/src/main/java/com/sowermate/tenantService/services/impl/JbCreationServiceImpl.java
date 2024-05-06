package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.JbCreationValue;
import com.sowermate.tenantService.repositories.JbCreationRepository;
import com.sowermate.tenantService.repositories.ToughenBatchProcessDetailsRepository;
import com.sowermate.tenantService.repositories.ToughenBatchProcessRepository;
import com.sowermate.tenantService.services.JbCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JbCreationServiceImpl implements JbCreationService {

    @Autowired
    private JbCreationRepository jbCreationRepository;

    @Autowired
    private ToughenBatchProcessRepository toughenBatchProcessRepository;

    @Override
    public JbCreationValue createJbCreation(JbCreationValue jbCreationValue) {
        return prepareAndSaveEntities(jbCreationValue);
    }


    @Override
    public JbCreationValue updateJbCreation(JbCreationValue jbCreationValue) {
        return prepareAndSaveEntities(jbCreationValue);
    }


    @Override
    public List<JbCreationValue> getAllJbCreation(String toughenBatchProcessUuid) {
        List<JbCreationEntity> toughenBatchProcessDetails = jbCreationRepository.findAllByToughenBatchProcessEntityUuid(toughenBatchProcessUuid);
        return toughenBatchProcessDetails.stream().map(e -> e.toDTO()).collect(Collectors.toList());
    }

    @Override
    public JbCreationValue getJbCreation(String toughenBatchProcessUuid, String jbCreationUuid) {
        JbCreationEntity jbCreationEntity = jbCreationRepository.findByToughenBatchProcessEntity_UuidAndJbCreationEntityUuid(toughenBatchProcessUuid, jbCreationUuid);
        return jbCreationEntity.toDTO().toBuilder().build();
    }

    @Override
    public JbCreationValue deleteJbCreation(String toughenBatchProcessUuid, String jbCreationUuid) {
        jbCreationRepository.softDelete(toughenBatchProcessUuid, jbCreationUuid);
        return getJbCreation(toughenBatchProcessUuid, jbCreationUuid);
    }


    private JbCreationValue prepareAndSaveEntities(JbCreationValue jbCreationValue) {

        ToughenBatchProcessEntity toughenBatchProcessEntity = toughenBatchProcessRepository.findByUuid(jbCreationValue.getToughenBatchProcessUuid());

        JbCreationEntity jbCreationEntity = prepareAndSaveJbCreationEntity(jbCreationValue, toughenBatchProcessEntity);

        return jbCreationEntity.toDTO().toBuilder().build();
    }

    private JbCreationEntity prepareAndSaveJbCreationEntity(JbCreationValue jbCreationValue, ToughenBatchProcessEntity toughenBatchProcessEntity) {
        if (null == jbCreationValue.getJbCreationUuid()) {
            return jbCreationRepository.save(jbCreationValue.toEntity().toBuilder().toughenBatchProcessEntity(toughenBatchProcessEntity).build());
        } else {
            JbCreationEntity jbCreationEntityTemp = jbCreationRepository.findByToughenBatchProcessEntity_UuidAndJbCreationEntityUuid(jbCreationValue.getToughenBatchProcessUuid(), jbCreationValue.getJbCreationUuid());
            return jbCreationRepository.save(jbCreationValue.toEntity().toBuilder()
                    .id(jbCreationEntityTemp.getId())
                    .toughenBatchProcessEntity(toughenBatchProcessEntity)
                    .createdDateTime(jbCreationEntityTemp.getCreatedDateTime())
                    .createdBy(jbCreationEntityTemp.getCreatedBy())
                    .build());
        }
    }

}
