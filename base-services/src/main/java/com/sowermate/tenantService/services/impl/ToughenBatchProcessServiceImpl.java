package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.ToughenBatchProcessEntity;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import com.sowermate.tenantService.repositories.ToughenBatchProcessRepository;
import com.sowermate.tenantService.services.ToughenBatchProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ToughenBatchProcessServiceImpl implements ToughenBatchProcessService {

    @Autowired
    private ToughenBatchProcessRepository toughenBatchProcessRepository;

    @Override
    public ToughenBatchProcessValue saveOrUpdate(ToughenBatchProcessValue toughenBatchProcessValue) {
        Optional<ToughenBatchProcessEntity> result = toughenBatchProcessRepository.findFirstByCompanyEntityUuidOrderByCreatedDateTimeDesc(toughenBatchProcessValue.getCompanyUuid());
        List<ToughenBatchProcessEntity> toughenBatchProcessEntities = toughenBatchProcessRepository.findAllByCompanyEntityUuidAndStatus(toughenBatchProcessValue.getCompanyUuid(), ToughenBatchProcessStatusEnum.INPROGRESS);
        //toughenBatchProcessEntities.get
        return toughenBatchProcessRepository.saveAndFlush(toughenBatchProcessValue.toEntity()).toDTO();
    }
}
