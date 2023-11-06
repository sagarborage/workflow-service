package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.DeptTypeEnum;
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.value.BucketManipulationValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.ProFormaInvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
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

    @Autowired
    private TenantRepository tenantRepository;

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
    public ProFormaInvoiceItemValue editProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue) {

        String tenantUuid = proFormaInvoiceItemValue.getTenantUuid();
        ProFormaInvoiceItemEntity tempProFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(proFormaInvoiceItemValue.getUuid(), proFormaInvoiceItemValue.getTenantUuid());
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
                .createdDateTime(tempProFormaInvoiceItemEntity.getCreatedDateTime())
                .createdBy(tempProFormaInvoiceItemEntity.getCreatedBy())
                .build();
        return proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity).toDTO();
    }

    @Override
    public ProFormaInvoiceItemValue getProFormaInvoiceItem(String tenantUuid, String proFormaInvoiceItemUuid) {
        return proFormaInvoiceItemRepository.findByTenantEntity_UuidAndProFormaInvoiceItemUuid(tenantUuid,proFormaInvoiceItemUuid).toDTO();
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
        } else {
            throw new ResourceNotFoundException();
        }

        proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity);
        List<ProFormaInvoiceIndividualsOrdersProjection> proFormaInvoiceIndividualsOrdersProjections = null;


        switch (DeptTypeEnum.valueOf(deptType.toUpperCase())) {
            case OPTIMIZE:
                proFormaInvoiceIndividualsOrdersProjections =  proFormaInvoiceRepository.findAllPiOrdersDetailsOfOptimizeIndividual(bucketManipulationValue.getTenantUuid(),bucketManipulationValue.getProFormaInvoiceUUid());
                break;
            case CUTTING:
                proFormaInvoiceIndividualsOrdersProjections =  proFormaInvoiceRepository.findAllPiOrdersDetailsOfCuttingIndividual(bucketManipulationValue.getTenantUuid(),bucketManipulationValue.getProFormaInvoiceUUid());
                break;

            case TOUGHEN:
                proFormaInvoiceIndividualsOrdersProjections =  proFormaInvoiceRepository.findAllPiOrdersDetailsOfToughenIndividual(bucketManipulationValue.getTenantUuid(),bucketManipulationValue.getProFormaInvoiceUUid());
                break;
            case DISPATCH:
                proFormaInvoiceIndividualsOrdersProjections =  proFormaInvoiceRepository.findAllPiOrdersDetailsOfDispatchIndividual(bucketManipulationValue.getTenantUuid(),bucketManipulationValue.getProFormaInvoiceUUid());
                break;

            default:
                throw new ResourceNotFoundException();
        }

        return proFormaInvoiceIndividualsOrdersProjections;
    }
}

