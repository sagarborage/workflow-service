package com.sowermate.workflow.service.services.impl;

import com.sowermate.workflow.domain.entities.GlassThicknessEntity;
import com.sowermate.workflow.domain.entities.JbCreationEntity;
import com.sowermate.workflow.domain.entities.ToughenBatchProcessEntity;
import com.sowermate.workflow.domain.entities.minimal.StickerReportProjection;
import com.sowermate.workflow.domain.entities.value.JbCreationValue;
import com.sowermate.workflow.persistence.repositories.GlassThicknessRepository;
import com.sowermate.workflow.persistence.repositories.JbCreationRepository;
import com.sowermate.workflow.persistence.repositories.ToughenBatchProcessRepository;
import com.sowermate.workflow.service.services.JbCreationService;
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

    @Autowired
    private GlassThicknessRepository glassThicknessRepository;

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

    @Override
    public StickerReportProjection getStickerData(String jbUuid) {
        return jbCreationRepository.findByStickerData(jbUuid);
    }


    private JbCreationValue prepareAndSaveEntities(JbCreationValue jbCreationValue) {

        ToughenBatchProcessEntity toughenBatchProcessEntity = toughenBatchProcessRepository.findByUuid(jbCreationValue.getToughenBatchProcessUuid());

        JbCreationEntity jbCreationEntity = prepareAndSaveJbCreationEntity(jbCreationValue, toughenBatchProcessEntity);

        return jbCreationEntity.toDTO().toBuilder().build();
    }

    private JbCreationEntity prepareAndSaveJbCreationEntity(JbCreationValue jbCreationValue, ToughenBatchProcessEntity toughenBatchProcessEntity) {
        if (null == jbCreationValue.getUuid()) {
            GlassThicknessEntity glassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(jbCreationValue.getTenantUuid(), jbCreationValue.getGlassThicknessUuid());
            return jbCreationRepository.save(jbCreationValue.toEntity().toBuilder().toughenBatchProcessEntity(toughenBatchProcessEntity).glassThicknessEntity(glassThicknessEntity).build());
        } else {
            JbCreationEntity jbCreationEntityTemp = jbCreationRepository.findByToughenBatchProcessEntity_UuidAndJbCreationEntityUuid(jbCreationValue.getToughenBatchProcessUuid(), jbCreationValue.getUuid());
            return jbCreationRepository.save(jbCreationValue.toEntity().toBuilder()
                    .id(jbCreationEntityTemp.getId())
                    .toughenBatchProcessEntity(toughenBatchProcessEntity)
                    .createdDateTime(jbCreationEntityTemp.getCreatedDateTime())
                    .createdBy(jbCreationEntityTemp.getCreatedBy())
                    .build());
        }
    }
}
