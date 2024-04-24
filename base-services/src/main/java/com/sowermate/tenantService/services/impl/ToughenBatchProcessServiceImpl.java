package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.GeneralParamValue;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.ToughenBatchProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ToughenBatchProcessServiceImpl implements ToughenBatchProcessService {

    @Autowired
    private ToughenBatchProcessRepository toughenBatchProcessRepository;

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;
    @Autowired
    private WorkOrderRepository workOrderRepository;
    @Autowired
    private ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;

    @Autowired
    private ProFormaInvoiceItemServiceImpl proFormaInvoiceItemService;

    @Override
    @Transactional
    public void toughenBatchProcessItemAdd(GeneralParamValue generalParamValue) {
        Optional<List<ToughenBatchProcessEntity>>  batchListInProgress = toughenBatchProcessRepository.findByStatusOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum.IN_PROGRESS);
        ToughenBatchProcessDetailsEntity toughenBatchProcessDetailsEntity = createToughenBatchProcessDetailsEntity(generalParamValue);
        //deduct item from toughen item
        deductItemFromToughenItem(generalParamValue);

        if(!ObjectUtils.isEmpty(batchListInProgress.get())) {
           ToughenBatchProcessEntity toughenBatchProcessEntity = batchListInProgress.get().get(0);
           toughenBatchProcessDetailsEntity = toughenBatchProcessDetailsEntity.toBuilder().toughenBatchProcessEntity(toughenBatchProcessEntity).build();
           toughenBatchProcessEntity.getToughenBatchProcessDetailsEntities().add(toughenBatchProcessDetailsEntity);
           toughenBatchProcessRepository.saveAndFlush(toughenBatchProcessEntity).toDTO();
       } else {
           //
           Optional<ToughenBatchProcessEntity> batchListRecentRecord = toughenBatchProcessRepository.findFirstByCompanyEntityUuidOrderByCreatedDateTimeDesc(generalParamValue.getCompanyUuid());

           ToughenBatchProcessEntity toughenBatchProcessEntity;
           if(batchListRecentRecord.isPresent() && !ObjectUtils.isEmpty(batchListRecentRecord.get())) {
               ToughenBatchProcessEntity dbEntity = batchListRecentRecord.get();
               LocalDateTime createdDateTime = dbEntity.getCreatedDateTime();
               if(createdDateTime.getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
                   toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, dbEntity.getBatchNo() + 1);
               } else {
                   toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, 1);
               }
           }  else {
               //empty records
               toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, 1);
           }

           toughenBatchProcessEntity = toughenBatchProcessEntity.toBuilder().toughenBatchProcessDetailsEntities(List.of(toughenBatchProcessDetailsEntity)).build();
           toughenBatchProcessRepository.saveAndFlush(toughenBatchProcessEntity);
       }
    }

    @Override
    public List<ToughenBatchProcessValue> toughenBatchProcessItemCancel(String tenantUuid, String uuid) {
        proFormaInvoiceItemService.toughenBatchProcess(tenantUuid, uuid, true);
        Optional<List<ToughenBatchProcessEntity>>  batchListInProgress =  toughenBatchProcessRepository.findByStatusOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum.IN_PROGRESS);
        return batchListInProgress.get().stream().map(e->e.toDTO()).collect(Collectors.toList());
    }

    @Override
    public List<ToughenBatchProcessValue> markToughenBatchProcessComplete(GeneralParamValue generalParamValue) {
        Optional<List<ToughenBatchProcessEntity>>  batchListInProgress = toughenBatchProcessRepository.findByStatusOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum.IN_PROGRESS);
        List<ToughenBatchProcessEntity> toBeUpdated = batchListInProgress.get().stream().map(e->{
             e.setStatus(ToughenBatchProcessStatusEnum.COMPLETED);
             return e;
         }).collect(Collectors.toList());
        toughenBatchProcessRepository.saveAllAndFlush(toBeUpdated);
        //TODO optimize this logic
        Optional<List<ToughenBatchProcessEntity>>  latestBatchListInProgress =  toughenBatchProcessRepository.findByStatusOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum.IN_PROGRESS);
        return latestBatchListInProgress.get().stream().map(e->e.toDTO()).collect(Collectors.toList());
    }

    @Override
    public List<ToughenBatchProcessValue> getToughenBatchProcessByStatus(ToughenBatchProcessStatusEnum toughenBatchProcessStatusEnum) {
        Optional<List<ToughenBatchProcessEntity>>  list = toughenBatchProcessRepository.findByStatusOrderByCreatedDateTimeDesc(toughenBatchProcessStatusEnum);
        if(list.isPresent()) {
            return list.get().stream().map(ToughenBatchProcessEntity::toDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private void deductItemFromToughenItem(GeneralParamValue generalParamValue) {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(generalParamValue.getTenantUuid(), generalParamValue.getPiItemUuid());
        Integer toughenBucketQty = proFormaInvoiceItemEntity.getToughenBucket();
        ProFormaInvoiceItemEntity updatedProFormaInvoiceItemEntity = proFormaInvoiceItemEntity.toBuilder().toughenBucket(toughenBucketQty - 1).build();
        proFormaInvoiceItemRepository.saveAndFlush(updatedProFormaInvoiceItemEntity);
    }

    public ToughenBatchProcessDetailsEntity createToughenBatchProcessDetailsEntity(GeneralParamValue generalParamValue) {
        return ToughenBatchProcessDetailsEntity.newBuilder()
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByUuid(generalParamValue.getPiUuid()))
                .workOrderEntity(workOrderRepository.findByUuid(generalParamValue.getWorkOrderUuid()))
                .proFormaInvoiceItemEntity(proFormaInvoiceItemRepository.findByUuid(generalParamValue.getPiItemUuid()))
                .status(ToughenBatchProcessStatusEnum.IN_PROGRESS)
                .isActive(true)
                .build();
    }

    public ToughenBatchProcessEntity createToughenBatchProcessEntity(GeneralParamValue generalParamValue, Integer batchNO) {
        return ToughenBatchProcessEntity.newBuilder()
                .tenantEntity(tenantRepository.findByUuid(generalParamValue.getTenantUuid()))
                .companyEntity(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(generalParamValue.getTenantUuid(), generalParamValue.getCompanyUuid()))
                .batchNo(batchNO)
                .status(ToughenBatchProcessStatusEnum.IN_PROGRESS)
                .isActive(true)
                .build();
    }
}
