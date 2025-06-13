package com.sowermate.workflow.service.services.impl;

import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.core.tenant.services.TenantService;
import com.sowermate.workflow.domain.entities.DeptTypeEnum;
import com.sowermate.workflow.domain.entities.GlassThicknessEntity;
import com.sowermate.workflow.domain.entities.JbCreationEntity;
import com.sowermate.workflow.domain.entities.ProFormaInvoiceItemEntity;
import com.sowermate.workflow.domain.entities.ToughenBatchProcessDetailsEntity;
import com.sowermate.workflow.domain.entities.ToughenBatchProcessEntity;
import com.sowermate.workflow.domain.entities.minimal.CompletedGlassesProjection;
import com.sowermate.workflow.domain.entities.minimal.StickerReportProjection;
import com.sowermate.workflow.domain.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.workflow.domain.entities.minimal.ToughenReportProjection;
import com.sowermate.workflow.domain.entities.minimal.ViewToughenBatchProcessDetailsProjection;
import com.sowermate.workflow.domain.entities.value.GeneralParamValue;
import com.sowermate.workflow.domain.entities.value.GlassBreakageDetailsValue;
import com.sowermate.workflow.domain.entities.value.JbCreationValue;
import com.sowermate.workflow.domain.entities.value.ToughReportDto;
import com.sowermate.workflow.domain.entities.value.ToughenBatchProcessDetailsValue;
import com.sowermate.workflow.domain.entities.value.ToughenBatchProcessValue;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;
import com.sowermate.workflow.persistence.repositories.GlassThicknessRepository;
import com.sowermate.workflow.persistence.repositories.JbCreationRepository;
import com.sowermate.workflow.persistence.repositories.ProFormaInvoiceItemRepository;
import com.sowermate.workflow.persistence.repositories.ProFormaInvoiceRepository;
import com.sowermate.workflow.persistence.repositories.ToughenBatchProcessDetailsRepository;
import com.sowermate.workflow.persistence.repositories.ToughenBatchProcessRepository;
import com.sowermate.workflow.persistence.repositories.WorkOrderRepository;
import com.sowermate.workflow.service.services.ToughenBatchProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;

    @Autowired
    private ProFormaInvoiceItemServiceImpl proFormaInvoiceItemService;

    @Autowired
    private GlassBreakageDetailsServiceImpl glassBreakageDetailsService;

    @Autowired
    private TenantService tenantService;

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
                .map(jb -> jb.toEntity().toBuilder().glassThicknessEntity(thicknessEntity)
                        .toughenBatchProcessEntity(toughenBatchProcessEntity).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ToughenBatchProcessDetailsValue toughenBatchProcessItemCancel(String uuid, String companyUuid) {
        ToughenBatchProcessDetailsEntity toughenBatchProcessDetailsEntity = toughenBatchProcessRepository.findToughenBatchProcessDetailsEntityByUuidAndCompanyUuid(uuid, companyUuid);
        if (toughenBatchProcessDetailsEntity != null) {
            //TODO: changed this logic to completely remove entry from batch process
            //toughenBatchProcessDetailsEntity.setStatus(ToughenBatchProcessStatusEnum.CANCEL);
            //toughenBatchProcessDetailsEntity = toughenBatchProcessDetailsRepository.save(toughenBatchProcessDetailsEntity);
            ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = toughenBatchProcessRepository.getPIItemToBeCancelled(uuid);
            proFormaInvoiceItemEntity.setToughenBucket(proFormaInvoiceItemEntity.getToughenBucket() + 1);
            proFormaInvoiceItemEntity.setToughenCompleted(proFormaInvoiceItemEntity.getToughenCompleted() - 1);
            proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity);
            toughenBatchProcessDetailsRepository.delete(toughenBatchProcessDetailsEntity);
            return toughenBatchProcessDetailsEntity.toDTO();
        } else {
            //Check and remove if the item type is JB
            JbCreationEntity jbCreationEntity = jbCreationRepository.getJbCreationEntityByUuid(uuid);
            if (jbCreationEntity != null) {
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
            proFormaInvoiceItemEntity.setToughenCompleted(proFormaInvoiceItemEntity.getToughenCompleted() - 1);
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
            //batchListInProgress.get().get(0).getToughenBatchProcessDetailsEntities().get(3).getProFormaInvoiceItemEntity().getUuid()
            //TODO optimize this logic
            for (ToughenBatchProcessDetailsEntity tbpd : e.getToughenBatchProcessDetailsEntities()) {
                if (!tbpd.getStatus().equals(ToughenBatchProcessStatusEnum.BROKEN)) {
                    Optional<List<ProFormaInvoiceItemEntity>> proFormaInvoiceItemEntityList = toughenBatchProcessRepository.findByBatchNo(e.getId());
                    proFormaInvoiceItemEntityList.get().stream().map(pi -> {
                        if (tbpd.getProFormaInvoiceItemEntity().getUuid().equals(pi.getUuid())) {
                            //pi.setToughenCompleted(pi.getToughenCompleted() + 1);
                            pi.setDispatchBucket(pi.getDispatchBucket() + 1);
                        }
                        return pi;
                    }).toList();
                }
            }
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

    public static double scaleValue(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public ToughReportDto getToughenReportForReport(LocalDate date) {
        List<String> toughenThickness = toughenBatchProcessRepository.findToughenThickness();
        List<ToughenReportProjection> workOrders = toughenBatchProcessRepository.findToughenReportByDate(date);
        List<ToughenReportProjection> jbs = toughenBatchProcessRepository.findToughenReportJbCreationByDate(date);
        Map<String, Double> jbMap = new HashMap<>();
        Map<String, Double> workOrderMap = new HashMap<>();
        Map<String, Double> rejectedMap = new HashMap<>();
        Map<String, Double> totalMap = new HashMap<>();
        Double totalJbSqft = 0.0;
        Double totalWoSqft = 0.0;
        Double totalRejectSqft = 0.0;
        for (ToughenReportProjection jbData : jbs) {
            String thickness = jbData.getThickness();
            Double width = jbData.getWidthMm();
            Double height = jbData.getHeightMm();
            Integer quantity = jbData.getQuantity();

            if (thickness != null && width != null && height != null && quantity != null) {
                double sqft = ((width * height * quantity) / 1000000) * 10.764;
                jbMap.put(thickness, scaleValue(jbMap.getOrDefault(thickness, 0.0) + sqft));
                totalMap.put(thickness, scaleValue(totalMap.getOrDefault(thickness, 0.0) + sqft));
                totalJbSqft += sqft;
            }

        }

        for (ToughenReportProjection woData : workOrders) {
            String thickness = woData.getThickness();
            Double width = woData.getWidthMm();
            Double height = woData.getHeightMm();
            Integer quantity = 1;

            if (thickness != null && width != null && height != null) {
                double sqft = ((width * height * quantity) / 1000000) * 10.764;
                workOrderMap.put(thickness, scaleValue(workOrderMap.getOrDefault(thickness, 0.0) + sqft));
                totalMap.put(thickness, scaleValue(totalMap.getOrDefault(thickness, 0.0) + sqft));
                totalWoSqft += sqft;
            }
        }
        for (String thick : toughenThickness) {
            workOrderMap.putIfAbsent(thick, 0.0);
            jbMap.putIfAbsent(thick, 0.0);
            rejectedMap.putIfAbsent(thick, 0.0);
            totalMap.putIfAbsent(thick, 0.0);
        }
        ToughReportDto toughReportDto = new ToughReportDto();
        toughReportDto.setDate(date);
        toughReportDto.setWorkOrder(scaleValue(totalWoSqft));
        toughReportDto.setJb(scaleValue(totalJbSqft));
        toughReportDto.setReject(0.0);
        toughReportDto.setTotalCompleted(scaleValue(totalJbSqft + totalWoSqft + totalRejectSqft));
        toughReportDto.setThickness(toughenThickness);
        toughReportDto.setWorkOrderSqft(workOrderMap);
        toughReportDto.setJbSqft(jbMap);
        toughReportDto.setRejectSqft(rejectedMap);
        toughReportDto.setTotalSqft(totalMap);
        return toughReportDto;
    }

    @Override
    public List<ViewToughenBatchProcessDetailsProjection> viewToughenBatchProcessDetails(String tenantUuid, String companyUuid, LocalDate batchProcessingDate) {
        List<ViewToughenBatchProcessDetailsProjection> list = toughenBatchProcessRepository.findByViewToughBatchProcess(tenantUuid, companyUuid, batchProcessingDate);
        if (!list.isEmpty()) {
            return list;
        }
        return new ArrayList<>();
    }

    @Override
    public StickerReportProjection getStickerOfBatchItem(String tenantUuid, String companyUuid, String batchItemUuid) {
        return toughenBatchProcessDetailsRepository.findStickerDataOfToughenBatchItem(tenantUuid, companyUuid, batchItemUuid);
    }

    @Override
    public List<StickerReportProjection> getStickersOfBatch(String tenantUuid, String companyUuid, String batchUuid) {
        return toughenBatchProcessDetailsRepository.findAllStickerDataOfBatch(tenantUuid, companyUuid, batchUuid);
    }

    @Override
    public List<CompletedGlassesProjection> getCompletedGlassesForReport(String tenantUuid, String companyUuid, LocalDate batchItemDate) {
        return toughenBatchProcessRepository.findByThickness(tenantUuid, companyUuid, batchItemDate);
    }


    private void deductItemFromToughenItem(GeneralParamValue generalParamValue) {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(generalParamValue.getTenantUuid(), generalParamValue.getPiItemUuid());
        Integer toughenBucketQty = proFormaInvoiceItemEntity.getToughenBucket();
        Integer toughenCompleteQty = proFormaInvoiceItemEntity.getToughenCompleted();
        ProFormaInvoiceItemEntity updatedProFormaInvoiceItemEntity = proFormaInvoiceItemEntity.toBuilder().toughenBucket(toughenBucketQty - 1).toughenCompleted(toughenCompleteQty + 1).build();
        proFormaInvoiceItemRepository.saveAndFlush(updatedProFormaInvoiceItemEntity);
    }

    public ToughenBatchProcessDetailsEntity createToughenBatchProcessDetailsEntity(GeneralParamValue
                                                                                           generalParamValue) {
        return ToughenBatchProcessDetailsEntity.newBuilder()
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByUuid(generalParamValue.getPiUuid()))
                .workOrderEntity(workOrderRepository.findByUuid(generalParamValue.getWorkOrderUuid()))
                .proFormaInvoiceItemEntity(proFormaInvoiceItemRepository.findByUuid(generalParamValue.getPiItemUuid()))
                .stickerNumber(generalParamValue.getStickerNumber())
                .status(ToughenBatchProcessStatusEnum.IN_PROGRESS)
                .isActive(true)
                .build();
    }

    public ToughenBatchProcessEntity createToughenBatchProcessEntity(GeneralParamValue generalParamValue, Integer
            batchNO) {
        return ToughenBatchProcessEntity.newBuilder()
                .tenantEntity(tenantRepository.findByUuid(generalParamValue.getTenantUuid()))
                //.companyEntity(companyService.getCompanyEntity(generalParamValue.getCompanyUuid(), generalParamValue.getTenantUuid()))
                .companyEntity(tenantService.getTenantEntity(generalParamValue.getCompanyUuid()))
                .batchNo(batchNO)
                .status(ToughenBatchProcessStatusEnum.IN_PROGRESS)
                .isActive(true)
                .build();
    }

    private static GlassBreakageDetailsValue getBreakageDetails(GeneralParamValue generalParamValue) {
        return GlassBreakageDetailsValue
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
