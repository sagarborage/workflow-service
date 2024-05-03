package com.sowermate.tenantService.services.impl;

import com.sowermate.image.config.ImageStorageConfig;
import com.sowermate.image.services.PdfService;
import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.value.BucketManipulationValue;
import com.sowermate.tenantService.entities.value.GlassBreakageDetailsValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.GlassBreakageDetailsService;
import com.sowermate.tenantService.services.ProFormaInvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional(rollbackForClassName = {"Exception"})
public class ProFormaInvoiceItemServiceImpl implements ProFormaInvoiceItemService {

    @Autowired
    private ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private GlassTypeRepository glassTypeRepository;

    @Autowired
    private GlassSpecificationRepository glassSpecificationRepository;

    @Autowired
    private GlassThicknessRepository glassThicknessRepository;

    @Autowired(required = true)
    private PdfService pdfService;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private GlassBreakageDetailsService glassBreakageDetailsService;

    @Autowired
    private ImageStorageConfig imageStorageConfig;

    private static GlassBreakageDetailsValue getGlassBreakageDetailsValue(BucketManipulationValue bucketManipulationValue, String deptType) {
        GlassBreakageDetailsValue glassBreakageDetailsValue = new GlassBreakageDetailsValue();
        glassBreakageDetailsValue.setProFormaInvoiceItemUuid(bucketManipulationValue.getProFormaInvoiceItemUUid());
        glassBreakageDetailsValue.setProFormaInvoiceUuid(bucketManipulationValue.getProFormaInvoiceUUid());
        glassBreakageDetailsValue.setTenantUuid(bucketManipulationValue.getTenantUuid());
        glassBreakageDetailsValue.setDeptName(deptType.toUpperCase());
        glassBreakageDetailsValue.setDetails(bucketManipulationValue.getDetails());
        return glassBreakageDetailsValue;
    }

    @Override
    public ProFormaInvoiceItemValue createProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) {

        String tenantUuid = proFormaInvoiceItemValue.getTenantUuid();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemValue.toEntity().toBuilder()
                .tenantEntity(tenantRepository.findByUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(
                        tenantUuid, proFormaInvoiceItemValue.getProFormaInvoiceUuid()))
                .glassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassThicknessUuid()))
                .glassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassTypeUuid()))
                .glassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassSpecificationUuid()))
                .build();
        return proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity).toDTO();
    }

    @Override
    public List<ProFormaInvoiceItemValue> saveAllProFormaInvoiceItem(String tenantUuid, List<ProFormaInvoiceItemValue> proFormaInvoiceItems) {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue = proFormaInvoiceItems.get(0);
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(
                tenantUuid, proFormaInvoiceItemValue.getProFormaInvoiceUuid());
        GlassThicknessEntity glassThicknessEntity = glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,
                proFormaInvoiceItemValue.getGlassThicknessUuid());
        GlassTypeEntity glassTypeEntity = glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,
                proFormaInvoiceItemValue.getGlassTypeUuid());
        GlassSpecificationEntity glassSpecificationEntity = glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                proFormaInvoiceItemValue.getGlassSpecificationUuid());

        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = proFormaInvoiceItems.stream().map(e -> {
            return e.toEntity().toBuilder()
                    .tenantEntity(tenantEntity)
                    .proFormaInvoiceEntity(proFormaInvoiceEntity)
                    .glassThicknessEntity(glassThicknessEntity)
                    .glassTypeEntity(glassTypeEntity)
                    .glassSpecificationEntity(glassSpecificationEntity)
                    .version(1)
                    .build();
        }).collect(Collectors.toList());

        return proFormaInvoiceItemRepository.saveAll(proFormaInvoiceItemEntities).stream().map(e -> e.toDTO()).collect(Collectors.toList());
    }

    @Override
    public String deleteProformaInvoiceItemFile(String tenantUuid,String proformaInvoiceItemUuid, String fileName) {
        Boolean isDeleted = pdfService.deleteFile(imageStorageConfig.getProFormInvoicePdfDirectory(),proformaInvoiceItemUuid,fileName);
        if (isDeleted) {
            ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(tenantUuid,proformaInvoiceItemUuid);
            proFormaInvoiceItemEntity.setFileUrl(null);
            proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity);
            return "File Deleted Successfully";
        }
        else
            return "Error when Deleted file";

    }

    @Override
    public ProFormaInvoiceItemValue editProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) {

        String tenantUuid = proFormaInvoiceItemValue.getTenantUuid();
        String fileUrl = null;
        if (proFormaInvoiceItemValue.getBase64File() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(proFormaInvoiceItemValue.getBase64File());
            fileUrl = pdfService.handlePdf(imageBytes, proFormaInvoiceItemValue.getUuid(), "", imageStorageConfig.getProFormInvoicePdfDirectory());
        }
        ProFormaInvoiceItemEntity tempProFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(proFormaInvoiceItemValue.getTenantUuid(), proFormaInvoiceItemValue.getUuid());
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemValue.toEntity().toBuilder()
                .id(tempProFormaInvoiceItemEntity.getId())
                .tenantEntity(tenantRepository.findByUuid(tenantUuid))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(
                        tenantUuid, proFormaInvoiceItemValue.getProFormaInvoiceUuid()))
                .glassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassThicknessUuid()))
                .glassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassTypeUuid()))
                .glassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassSpecificationUuid()))
                .widthInch(proFormaInvoiceItemValue.getWidthInch() == null ? tempProFormaInvoiceItemEntity.getWidthInch() : proFormaInvoiceItemValue.getWidthInch())
                .widthMeasurement(proFormaInvoiceItemValue.getWidthMeasurement() == null ? tempProFormaInvoiceItemEntity.getWidthMeasurement() : proFormaInvoiceItemValue.getWidthMeasurement())
                .actualWidth(proFormaInvoiceItemValue.getActualWidth() == null ? tempProFormaInvoiceItemEntity.getActualWidth() : proFormaInvoiceItemValue.getActualWidth())
                .chargeableWidth(proFormaInvoiceItemValue.getChargeableWidth() == null ? tempProFormaInvoiceItemEntity.getChargeableWidth() : proFormaInvoiceItemValue.getChargeableWidth())
                .heightInch(proFormaInvoiceItemValue.getHeightInch() == null ? tempProFormaInvoiceItemEntity.getHeightInch() : proFormaInvoiceItemValue.getHeightInch())
                .heightMeasurement(proFormaInvoiceItemValue.getHeightMeasurement() == null ? tempProFormaInvoiceItemEntity.getHeightMeasurement() : proFormaInvoiceItemValue.getHeightMeasurement())
                .heightMeasurementLabel(proFormaInvoiceItemValue.getWidthMeasurementLabel() == null ? tempProFormaInvoiceItemEntity.getHeightMeasurementLabel() : proFormaInvoiceItemValue.getHeightMeasurementLabel())
                .actualHeight(proFormaInvoiceItemValue.getActualHeight() == null ? tempProFormaInvoiceItemEntity.getActualHeight() : proFormaInvoiceItemValue.getActualHeight())
                .chargeableHeight(proFormaInvoiceItemValue.getChargeableHeight() == null ? tempProFormaInvoiceItemEntity.getChargeableHeight() : proFormaInvoiceItemValue.getChargeableHeight())
                .extraMm(proFormaInvoiceItemValue.getExtraMm() == null ? tempProFormaInvoiceItemEntity.getExtraMm() : proFormaInvoiceItemValue.getExtraMm())
                .quantity(proFormaInvoiceItemValue.getQuantity() == null ? tempProFormaInvoiceItemEntity.getQuantity() : proFormaInvoiceItemValue.getQuantity())
                .unitValue(proFormaInvoiceItemValue.getUnitValue() == null ? tempProFormaInvoiceItemEntity.getUnitValue() : proFormaInvoiceItemValue.getUnitValue())
                .ratePerUnit(proFormaInvoiceItemValue.getRatePerUnit() == null ? tempProFormaInvoiceItemEntity.getRatePerUnit() : proFormaInvoiceItemValue.getRatePerUnit())
                .unitMeasurementLabel(proFormaInvoiceItemValue.getUnitMeasurementLabel() == null ? tempProFormaInvoiceItemEntity.getUnitMeasurementLabel() : proFormaInvoiceItemValue.getUnitMeasurementLabel())
                .amount(proFormaInvoiceItemValue.getAmount() == null ? tempProFormaInvoiceItemEntity.getAmount() : proFormaInvoiceItemValue.getAmount())
                .optimizeBucket(proFormaInvoiceItemValue.getOptimizeBucket() == null ? tempProFormaInvoiceItemEntity.getOptimizeBucket() : proFormaInvoiceItemValue.getOptimizeBucket())
                .cuttingBucket(proFormaInvoiceItemValue.getCuttingBucket() == null ? tempProFormaInvoiceItemEntity.getCuttingBucket() : proFormaInvoiceItemValue.getCuttingBucket())
                .toughenBucket(proFormaInvoiceItemValue.getToughenBucket() == null ? tempProFormaInvoiceItemEntity.getToughenBucket() : proFormaInvoiceItemValue.getToughenBucket())
                .dispatchBucket(proFormaInvoiceItemValue.getDispatchBucket() == null ? tempProFormaInvoiceItemEntity.getDispatchBucket() : proFormaInvoiceItemValue.getDispatchBucket())
                .optimizeCompleted(proFormaInvoiceItemValue.getOptimizeCompleted() == null ? tempProFormaInvoiceItemEntity.getOptimizeCompleted() : proFormaInvoiceItemValue.getOptimizeCompleted())
                .cuttingCompleted(proFormaInvoiceItemValue.getCuttingCompleted() == null ? tempProFormaInvoiceItemEntity.getCuttingCompleted() : proFormaInvoiceItemValue.getCuttingCompleted())
                .toughenCompleted(proFormaInvoiceItemValue.getToughenCompleted() == null ? tempProFormaInvoiceItemEntity.getToughenCompleted() : proFormaInvoiceItemValue.getToughenCompleted())
                .dispatchCompleted(proFormaInvoiceItemValue.getDispatchCompleted() == null ? tempProFormaInvoiceItemEntity.getDispatchCompleted() : proFormaInvoiceItemValue.getDispatchCompleted())
                .fileUrl(proFormaInvoiceItemValue.getBase64File() == null ? tempProFormaInvoiceItemEntity.getFileUrl() : fileUrl)
                .createdDateTime(tempProFormaInvoiceItemEntity.getCreatedDateTime())
                .createdBy(tempProFormaInvoiceItemEntity.getCreatedBy())
                .isActive(tempProFormaInvoiceItemEntity.getIsActive())
                .version(tempProFormaInvoiceItemEntity.getVersion())
                .build();
        return proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity).toDTO();
    }

    @Override
    public ProFormaInvoiceItemValue getProFormaInvoiceItem(String tenantUuid, String proFormaInvoiceItemUuid) {
        return proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(tenantUuid, proFormaInvoiceItemUuid).toDTO();
    }

    @Override
    public int deleteProFormaInvoiceItem(String tenantUuid, String ProFormaInvoiceItemUuid) {
        return proFormaInvoiceItemRepository.deleteByUuid(ProFormaInvoiceItemUuid);
    }

    @Override
    public List<ProFormaInvoiceItemValue> getAllProFormaInvoiceItem(String proFormaInvoiceUuid) {
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = proFormaInvoiceItemRepository.findAllByProFormaInvoiceEntity_uuid(proFormaInvoiceUuid);
        return proFormaInvoiceItemEntities.stream().map(piie -> piie.toDTO()).collect(Collectors.toList());
    }

    @Override
    public List<ProFormaInvoiceIndividualsOrdersProjection> bucketManipulation(String actionType, BucketManipulationValue bucketManipulationValue) {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(bucketManipulationValue.getTenantUuid(), bucketManipulationValue.getProFormaInvoiceItemUUid());
        String deptType = bucketManipulationValue.getCurrentBucket();
        Integer quantity = bucketManipulationValue.getQuantity();
        if (actionType.equals("add")) {
            switch (DeptTypeEnum.valueOf(deptType.toUpperCase())) {
                case OPTIMIZE:
                    proFormaInvoiceItemEntity.setOptimizeBucket(proFormaInvoiceItemEntity.getOptimizeBucket() - quantity);
                    proFormaInvoiceItemEntity.setOptimizeCompleted(proFormaInvoiceItemEntity.getOptimizeCompleted() + quantity);
                    proFormaInvoiceItemEntity.setCuttingBucket(proFormaInvoiceItemEntity.getCuttingBucket() + quantity);
                    break;
                case CUTTING:
                    proFormaInvoiceItemEntity.setCuttingBucket(proFormaInvoiceItemEntity.getCuttingBucket() - quantity);
                    proFormaInvoiceItemEntity.setCuttingCompleted(proFormaInvoiceItemEntity.getCuttingCompleted() + quantity);
                    proFormaInvoiceItemEntity.setToughenBucket(proFormaInvoiceItemEntity.getToughenBucket() + quantity);
                    break;
                case TOUGHEN:
                    proFormaInvoiceItemEntity.setToughenBucket(proFormaInvoiceItemEntity.getToughenBucket() - quantity);
                    proFormaInvoiceItemEntity.setToughenCompleted(proFormaInvoiceItemEntity.getToughenCompleted() + quantity);
                    proFormaInvoiceItemEntity.setDispatchBucket(proFormaInvoiceItemEntity.getDispatchBucket() + quantity);
                    break;
                case DISPATCH:
                    proFormaInvoiceItemEntity.setDispatchBucket(proFormaInvoiceItemEntity.getDispatchBucket() - quantity);
                    proFormaInvoiceItemEntity.setDispatchCompleted(proFormaInvoiceItemEntity.getDispatchCompleted() + quantity);
                    break;
                default:
                    throw new ResourceNotFoundException();
            }
        } else if (actionType.equals("broken")) {
            switch (DeptTypeEnum.valueOf(deptType.toUpperCase())) {
                case TOUGHEN:
                    proFormaInvoiceItemEntity.setToughenBucket(proFormaInvoiceItemEntity.getToughenBucket() - quantity);
                    proFormaInvoiceItemEntity.setCuttingCompleted(proFormaInvoiceItemEntity.getCuttingCompleted() - quantity);
                    proFormaInvoiceItemEntity.setCuttingBucket(proFormaInvoiceItemEntity.getCuttingBucket() + quantity);
                    break;
                case DISPATCH:
                    proFormaInvoiceItemEntity.setDispatchBucket(proFormaInvoiceItemEntity.getDispatchBucket() - quantity);
                    proFormaInvoiceItemEntity.setToughenCompleted(proFormaInvoiceItemEntity.getToughenCompleted() - quantity);
                    proFormaInvoiceItemEntity.setCuttingCompleted(proFormaInvoiceItemEntity.getCuttingCompleted() - quantity);
                    proFormaInvoiceItemEntity.setCuttingBucket(proFormaInvoiceItemEntity.getCuttingBucket() + quantity);
                    break;
                default:
                    throw new ResourceNotFoundException();
            }
            GlassBreakageDetailsValue glassBreakageDetailsValue = getGlassBreakageDetailsValue(bucketManipulationValue, deptType);
            glassBreakageDetailsService.createGlassBreakageDetails(glassBreakageDetailsValue);

        } else {
            throw new ResourceNotFoundException();
        }

        proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity);
        List<ProFormaInvoiceIndividualsOrdersProjection> proFormaInvoiceIndividualsOrdersProjections = null;


        switch (DeptTypeEnum.valueOf(deptType.toUpperCase())) {
            case OPTIMIZE:
                proFormaInvoiceIndividualsOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfOptimizeIndividual(bucketManipulationValue.getTenantUuid(), bucketManipulationValue.getWorkOrderNo());
                break;
            case CUTTING:
                proFormaInvoiceIndividualsOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfCuttingIndividual(bucketManipulationValue.getTenantUuid(), bucketManipulationValue.getWorkOrderNo());
                break;

            case TOUGHEN:
                proFormaInvoiceIndividualsOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfToughenIndividual(bucketManipulationValue.getTenantUuid(), bucketManipulationValue.getWorkOrderNo());
                break;
            case DISPATCH:
                proFormaInvoiceIndividualsOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfDispatchIndividual(bucketManipulationValue.getTenantUuid(), bucketManipulationValue.getWorkOrderNo());
                break;

            default:
                throw new ResourceNotFoundException();
        }

        return proFormaInvoiceIndividualsOrdersProjections;
    }

    @Override
    public List<ProFormaInvoiceIndividualsOrdersProjection> bucketManipulationCompleteAll(BucketManipulationValue bucketManipulationValue) {
        List<ProFormaInvoiceIndividualsOrdersProjection> proFormaInvoiceIndividualsOrdersProjections = null;
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceUuid(bucketManipulationValue.getTenantUuid(), bucketManipulationValue.getProFormaInvoiceItemUUid());
        for (ProFormaInvoiceItemEntity entity : proFormaInvoiceItemEntities) {
            bucketManipulationValue.setProFormaInvoiceItemUUid(entity.getUuid());
            if(bucketManipulationValue.getCurrentBucket().equalsIgnoreCase("Optimize")) {
                bucketManipulationValue.setQuantity(entity.getQuantity() - entity.getOptimizeCompleted());
            }
            if(bucketManipulationValue.getCurrentBucket().equalsIgnoreCase("Dispatch")) {
                bucketManipulationValue.setQuantity(entity.getQuantity() - entity.getDispatchCompleted());
            }
            proFormaInvoiceIndividualsOrdersProjections = bucketManipulation("add", bucketManipulationValue);
        }

        return proFormaInvoiceIndividualsOrdersProjections;
    }

    @Override
    public void toughenBatchProcess(String tenantUuid, String proFormaInvoiceItemUuid, boolean isCancel) {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(tenantUuid, proFormaInvoiceItemUuid);
        Integer toughenBucket = proFormaInvoiceItemEntity.getToughenBucket();
        ProFormaInvoiceItemEntity updatedProFormaInvoiceItemEntity = proFormaInvoiceItemEntity.toBuilder()
                .toughenBucket(isCancel ? toughenBucket + 1 : toughenBucket - 1).build();
        proFormaInvoiceItemRepository.save(updatedProFormaInvoiceItemEntity);
    }
}

