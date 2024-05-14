package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.tenantService.entities.minimal.ViewToughenBatchProcessDetailsProjection;
import com.sowermate.tenantService.entities.value.*;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.ToughenBatchProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ToughenBatchProcessServiceImpl implements ToughenBatchProcessService {

    @Autowired
    private ToughenBatchProcessRepository toughenBatchProcessRepository;

    @Autowired
    private JbCreationRepository jbCreationRepository;

    @Autowired
    private ToughenBatchProcessDetailsRepository toughenBatchProcessDetailsRepository;

    @Autowired
    private GlassThicknessRepository thicknessRepository;

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

    @Autowired
    private GlassBreakageDetailsServiceImpl glassBreakageDetailsService;

    @Override
    @Transactional
    public void toughenBatchProcessItemAdd(GeneralParamValue generalParamValue) {
        Optional<List<ToughenBatchProcessEntity>> batchListInProgress = toughenBatchProcessRepository.findByStatusOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum.IN_PROGRESS);
        ToughenBatchProcessDetailsEntity toughenBatchProcessDetailsEntity = createToughenBatchProcessDetailsEntity(generalParamValue);
        //deduct item from toughen item
        deductItemFromToughenItem(generalParamValue);

        if (!ObjectUtils.isEmpty(batchListInProgress.get())) {
            ToughenBatchProcessEntity toughenBatchProcessEntity = batchListInProgress.get().get(0);
            toughenBatchProcessDetailsEntity = toughenBatchProcessDetailsEntity.toBuilder().toughenBatchProcessEntity(toughenBatchProcessEntity).build();
            toughenBatchProcessEntity.getToughenBatchProcessDetailsEntities().add(toughenBatchProcessDetailsEntity);
            toughenBatchProcessRepository.saveAndFlush(toughenBatchProcessEntity).toDTO();
        } else {
            //
            Optional<ToughenBatchProcessEntity> batchListRecentRecord = toughenBatchProcessRepository.findFirstByCompanyEntityUuidOrderByCreatedDateTimeDesc(generalParamValue.getCompanyUuid());

            ToughenBatchProcessEntity toughenBatchProcessEntity;
            if (batchListRecentRecord.isPresent() && !ObjectUtils.isEmpty(batchListRecentRecord.get())) {
                ToughenBatchProcessEntity dbEntity = batchListRecentRecord.get();
                LocalDateTime createdDateTime = dbEntity.getCreatedDateTime();
                if (createdDateTime.getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
                    toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, dbEntity.getBatchNo() + 1);
                } else {
                    toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, 1);
                }
            } else {
                //empty records
                toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, 1);
            }

            toughenBatchProcessEntity = toughenBatchProcessEntity.toBuilder().toughenBatchProcessDetailsEntities(List.of(toughenBatchProcessDetailsEntity)).build();
            toughenBatchProcessRepository.saveAndFlush(toughenBatchProcessEntity);
        }
    }

    @Override
    public void toughenBatchProcessJBAddItems(List<JbCreationValue> jbCreationValues) {
        JbCreationValue jbCreationValue = jbCreationValues.get(0);
        Optional<List<ToughenBatchProcessEntity>> batchListInProgress = toughenBatchProcessRepository.findByStatusOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum.IN_PROGRESS);

        if (!ObjectUtils.isEmpty(batchListInProgress.get())) {
            GlassThicknessEntity thicknessEntity = thicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(jbCreationValue.getTenantUuid(), jbCreationValue.getGlassThicknessUuid());
            ToughenBatchProcessEntity toughenBatchProcessEntity = batchListInProgress.get().get(0);

            List<JbCreationEntity> jbCreationEntities = getJbCreationEntities(jbCreationValues, thicknessEntity, toughenBatchProcessEntity);

            toughenBatchProcessEntity.getJbCreationEntities().addAll(jbCreationEntities);
            toughenBatchProcessRepository.saveAndFlush(toughenBatchProcessEntity).toDTO();
        } else {
            // setting this values to be passed to create toughenBatchProcess
            GeneralParamValue generalParamValue = GeneralParamValue.newBuilder().tenantUuid(jbCreationValue.getTenantUuid()).companyUuid(jbCreationValue.getFirmUuid()).build();
            //
            Optional<ToughenBatchProcessEntity> batchListRecentRecord = toughenBatchProcessRepository.findFirstByCompanyEntityUuidOrderByCreatedDateTimeDesc(jbCreationValue.getFirmUuid());

            ToughenBatchProcessEntity toughenBatchProcessEntity;
            if (batchListRecentRecord.isPresent() && !ObjectUtils.isEmpty(batchListRecentRecord.get())) {
                ToughenBatchProcessEntity dbEntity = batchListRecentRecord.get();
                LocalDateTime createdDateTime = dbEntity.getCreatedDateTime();
                if (createdDateTime.getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
                    toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, dbEntity.getBatchNo() + 1);
                } else {
                    toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, 1);
                }
            } else {
                //empty records
                toughenBatchProcessEntity = createToughenBatchProcessEntity(generalParamValue, 1);
            }
            GlassThicknessEntity thicknessEntity = thicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(jbCreationValue.getTenantUuid(), jbCreationValue.getGlassThicknessUuid());

            List<JbCreationEntity> jbCreationEntities = getJbCreationEntities(jbCreationValues, thicknessEntity, toughenBatchProcessEntity);
            toughenBatchProcessEntity.setJbCreationEntities(jbCreationEntities);
            toughenBatchProcessRepository.saveAndFlush(toughenBatchProcessEntity);
        }
    }

    private static List<JbCreationEntity> getJbCreationEntities(List<JbCreationValue> jbCreationValues, GlassThicknessEntity thicknessEntity, ToughenBatchProcessEntity toughenBatchProcessEntity) {
        return jbCreationValues.stream()
                .map(jb-> jb.toEntity().toBuilder().glassThicknessEntity(thicknessEntity)
                        .toughenBatchProcessEntity(toughenBatchProcessEntity).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ToughenBatchProcessDetailsValue toughenBatchProcessItemCancel(String uuid, String companyUuid) {
        ToughenBatchProcessDetailsEntity toughenBatchProcessDetailsEntity = toughenBatchProcessRepository.findToughenBatchProcessDetailsEntityByUuidAndCompanyUuid(uuid,companyUuid);
        if (toughenBatchProcessDetailsEntity != null) {
            //TODO: changed this logic to completely remove entry from batch process
            //toughenBatchProcessDetailsEntity.setStatus(ToughenBatchProcessStatusEnum.CANCEL);
            //toughenBatchProcessDetailsEntity = toughenBatchProcessDetailsRepository.save(toughenBatchProcessDetailsEntity);
            ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = toughenBatchProcessRepository.getPIItemToBeCancelled(uuid);
            proFormaInvoiceItemEntity.setToughenBucket(proFormaInvoiceItemEntity.getToughenBucket() + 1);
            proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity);
            toughenBatchProcessDetailsRepository.delete(toughenBatchProcessDetailsEntity);
            return toughenBatchProcessDetailsEntity.toDTO();
        } else {
            //Check and remove if the item type is JB
            JbCreationEntity jbCreationEntity = jbCreationRepository.getJbCreationEntityByUuid(uuid);
            if(jbCreationEntity != null) {
                jbCreationRepository.delete(jbCreationEntity);
            }
        }
        return null;
    }

    @Override
    public ToughenBatchProcessDetailsValue toughenBatchProcessItemBroke(GeneralParamValue generalParamValue) {
        //TODO: rewrite this logic later on, specially param GeneralParamValue
        ToughenBatchProcessDetailsEntity toughenBatchProcessDetailsEntity = toughenBatchProcessRepository.findToughenBatchProcessDetailsEntityByUuidAndCompanyUuid(generalParamValue.getBatchItemUuid(), generalParamValue.getCompanyUuid());
        if (toughenBatchProcessDetailsEntity != null) {
            toughenBatchProcessDetailsEntity = toughenBatchProcessDetailsEntity.toBuilder().status(ToughenBatchProcessStatusEnum.BROKEN).build();
            toughenBatchProcessDetailsRepository.save(toughenBatchProcessDetailsEntity);

            ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = toughenBatchProcessRepository.getPIItemToBeCancelled(toughenBatchProcessDetailsEntity.getUuid());
            proFormaInvoiceItemEntity.setCuttingCompleted(proFormaInvoiceItemEntity.getCuttingCompleted() - 1);
            proFormaInvoiceItemEntity.setCuttingBucket(proFormaInvoiceItemEntity.getCuttingBucket() + 1);
            //Entry into break table
            GlassBreakageDetailsValue breakageDetails = getBreakageDetails(generalParamValue);
            glassBreakageDetailsService.createGlassBreakageDetails(breakageDetails);
            proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity);
        }
        assert toughenBatchProcessDetailsEntity != null;
        return toughenBatchProcessDetailsEntity.toDTO();
    }

    @Override
    @Transactional
    public List<ToughenBatchProcessValue> markToughenBatchProcessComplete(GeneralParamValue generalParamValue) {
        Optional<List<ToughenBatchProcessEntity>> batchListInProgress = toughenBatchProcessRepository.findByBatchNoAndCompanyUuidAndStatus(generalParamValue.getBatchNo(), generalParamValue.getCompanyUuid(), ToughenBatchProcessStatusEnum.IN_PROGRESS);
        List<ToughenBatchProcessEntity> toBeUpdated = batchListInProgress.get().stream().map(e -> {
            e.setStatus(ToughenBatchProcessStatusEnum.COMPLETED);
            Optional<List<ProFormaInvoiceItemEntity>> proFormaInvoiceItemEntityList = toughenBatchProcessRepository.findByBatchNo(e.getId());
            List<ProFormaInvoiceItemEntity> toBeUpdatedPiItem = proFormaInvoiceItemEntityList.get().stream().map(pi -> {
                pi.setToughenCompleted(pi.getToughenCompleted() + 1);
                pi.setDispatchBucket(pi.getDispatchBucket() + 1);
                return pi;
            }).toList();
            return e;
        }).collect(Collectors.toList());
        toughenBatchProcessRepository.saveAllAndFlush(toBeUpdated);

        //TODO optimize this logic
        Optional<List<ToughenBatchProcessEntity>> latestBatchListInProgress = toughenBatchProcessRepository.findByStatusOrderByCreatedDateTimeDesc(ToughenBatchProcessStatusEnum.IN_PROGRESS);
        return latestBatchListInProgress.get().stream().map(e -> e.toDTO()).collect(Collectors.toList());
    }

    @Override
    public List<ToughenBatchProcessProjection> getToughenBatchProcessByStatus(String companyUuid, ToughenBatchProcessStatusEnum toughenBatchProcessStatusEnum) {
        List<ToughenBatchProcessProjection> list = toughenBatchProcessRepository.findByCompanyUuidAndStatus(companyUuid, toughenBatchProcessStatusEnum);
        if (!list.isEmpty()) {
            return list;
        }
        return new ArrayList<>();
    }

    @Override
    public List<ViewToughenBatchProcessDetailsProjection> getViewToughBatchProcess(String tenantUuid, String companyUuid, LocalDate batchProcessingDate) {
        List<ViewToughenBatchProcessDetailsProjection> list = toughenBatchProcessRepository.findByViewToughBatchProcess(tenantUuid, companyUuid, batchProcessingDate);
        if(!list.isEmpty()){
            return list;
        }
        return new ArrayList<>();
    }

    private void deductItemFromToughenItem(GeneralParamValue generalParamValue) {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(generalParamValue.getTenantUuid(), generalParamValue.getPiItemUuid());
        Integer toughenBucketQty = proFormaInvoiceItemEntity.getToughenBucket();
        ProFormaInvoiceItemEntity updatedProFormaInvoiceItemEntity = proFormaInvoiceItemEntity.toBuilder().toughenBucket(toughenBucketQty - 1).build();
        proFormaInvoiceItemRepository.saveAndFlush(updatedProFormaInvoiceItemEntity);
    }

    public ToughenBatchProcessDetailsEntity createToughenBatchProcessDetailsEntity(GeneralParamValue
                                                                                           generalParamValue) {
        return ToughenBatchProcessDetailsEntity.newBuilder()
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByUuid(generalParamValue.getPiUuid()))
                .workOrderEntity(workOrderRepository.findByUuid(generalParamValue.getWorkOrderUuid()))
                .proFormaInvoiceItemEntity(proFormaInvoiceItemRepository.findByUuid(generalParamValue.getPiItemUuid()))
                .status(ToughenBatchProcessStatusEnum.IN_PROGRESS)
                .isActive(true)
                .build();
    }

    public ToughenBatchProcessEntity createToughenBatchProcessEntity(GeneralParamValue generalParamValue, Integer
            batchNO) {
        return ToughenBatchProcessEntity.newBuilder()
                .tenantEntity(tenantRepository.findByUuid(generalParamValue.getTenantUuid()))
                .companyEntity(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(generalParamValue.getTenantUuid(), generalParamValue.getCompanyUuid()))
                .batchNo(batchNO)
                .status(ToughenBatchProcessStatusEnum.IN_PROGRESS)
                .isActive(true)
                .build();
    }

    private static GlassBreakageDetailsValue getBreakageDetails(GeneralParamValue generalParamValue) {
        return  GlassBreakageDetailsValue
                .newBuilder()
                .tenantUuid(generalParamValue.getTenantUuid())
                .proFormaInvoiceUuid(generalParamValue.getPiUuid())
                .proFormaInvoiceItemUuid(generalParamValue.getPiItemUuid())
                .deptName(DeptTypeEnum.TOUGHEN)
                .details(generalParamValue.getDetails())
                .isActive(true)
                .build();
    }
}
