package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceMinimal;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceOrdersProjection;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import com.sowermate.tenantService.entities.DeptTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ProFormaInvoiceServiceImpl implements ProFormaInvoiceService {

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    private ConfirmThroughRepository confirmThroughRepository;

    @Autowired
    private PiTypeRepository piTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    WorkOrderRepository workOrderRepository;

    @Autowired
    ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;

    @Override
    @Transactional
    public ProFormaInvoiceValue createProFormaInvoice(ProFormaInvoiceValue proFormaInvoiceValue) {
        String tenantUUID = proFormaInvoiceValue.getTenantUuid();

        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUUID);

        if (ObjectUtils.isEmpty(tenantEntity)) {
            return null;
        } else {
            String piNumber = generatePiNumber(tenantEntity.getId());

            ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceValue.toEntity().toBuilder()
                    .tenantEntity(tenantRepository.findByUuid(proFormaInvoiceValue.getTenantUuid()))
                    .confirmThroughEntity(
                            null == proFormaInvoiceValue.getConfirmThroughUuid() ? null :
                                    confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUUID, proFormaInvoiceValue.getConfirmThroughUuid())
                    )
                    .piTypeEntity(piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(tenantUUID, proFormaInvoiceValue.getPiTypeUuid()))
                    .firm(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getFirmUuid()))
                    .companyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getCompanyBillToUuid()))
                    .companyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getCompanyShipToUuid()))
                    .piNumber(piNumber)
                    .invoiceDate(LocalDateTime.now())
                    .build();
            return proFormaInvoiceRepository.save(proFormaInvoiceEntity).toDTO();
        }
    }

    private String generatePiNumber(long tenantId) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findFirstByTenantEntityIdOrderByCreatedDateTimeDesc(tenantId);

        LocalDate currentDate = LocalDate.now();
        String currentMonth = currentDate.getMonth().toString().substring(0, 3);
        int newSequenceNumber = 1; // Default if no existing piNumber

        if (null != proFormaInvoiceEntity) {
            String piNumber = proFormaInvoiceEntity.getPiNumber();
            String sequenceMonth = piNumber.substring(0, 3);
            if (sequenceMonth.equals(currentMonth)) {
                int sequenceNumber = Integer.parseInt(piNumber.substring(4, piNumber.length()));
                newSequenceNumber = sequenceNumber + 1;
            }
        }
        return currentMonth + "/" + newSequenceNumber;
    }

    @Override
    public ProFormaInvoiceValue editProFormaInvoice(ProFormaInvoiceValue proFormaInvoiceValue) {

        String tenantUUID = proFormaInvoiceValue.getTenantUuid();
        ProFormaInvoiceEntity tempProFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(proFormaInvoiceValue.getTenantUuid(),
                proFormaInvoiceValue.getProFormaInvoiceUuid());

        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceValue.toEntity().toBuilder()
                .id(tempProFormaInvoiceEntity.getId())
                .tenantEntity(tenantRepository.findByUuid(proFormaInvoiceValue.getTenantUuid()))
                .confirmThroughEntity(
                        null == proFormaInvoiceValue.getConfirmThroughUuid() ? null :
                                confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUUID, proFormaInvoiceValue.getConfirmThroughUuid())
                )
                .piTypeEntity(piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(tenantUUID, proFormaInvoiceValue.getPiTypeUuid()))
                .firm(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getFirmUuid()))
                .companyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getCompanyBillToUuid()))
                .companyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getCompanyShipToUuid()))
                .piNumber(tempProFormaInvoiceEntity.getPiNumber())
                .invoiceDate(tempProFormaInvoiceEntity.getInvoiceDate())
                .createdDateTime(tempProFormaInvoiceEntity.getCreatedDateTime())
                .createdBy(tempProFormaInvoiceEntity.getCreatedBy())
                .build();
        return proFormaInvoiceRepository.save(proFormaInvoiceEntity).toDTO();
    }

    @Override
    public ProFormaInvoiceValue updateConfirmThrough(String tenantUuid, String proFormaInvoiceUuid, String confirmThroughUuid) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantUuid, proFormaInvoiceUuid);
        ConfirmThroughEntity confirmThroughEntity = confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUuid, confirmThroughUuid);
        if (confirmThroughEntity == null) {
            throw new ResourceNotFoundException();
        }
        proFormaInvoiceEntity.setConfirmThroughEntity(confirmThroughEntity);
        ProFormaInvoiceEntity proFormaInvoiceEntityUpdated = proFormaInvoiceRepository.save(proFormaInvoiceEntity);

        WorkOrderEntity workOrderEntity = new WorkOrderEntity();
        workOrderEntity.setProFormaInvoiceEntity(proFormaInvoiceEntity);
        workOrderEntity.setTenantEntity(proFormaInvoiceEntity.getTenantEntity());
        workOrderEntity.setFirm(proFormaInvoiceEntity.getFirm());
        WorkOrderEntity forCheck = workOrderRepository.save(workOrderEntity);


        if (proFormaInvoiceEntityUpdated.getConfirmThroughEntity() != null) {
            List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntityList = proFormaInvoiceItemRepository.findAllByProFormaInvoiceEntity_uuid(proFormaInvoiceEntity.getUuid());
            proFormaInvoiceItemEntityList.stream().map(item -> {
                item.setOptimizeBucket(item.getQuantity());
                return proFormaInvoiceEntity;
            }).collect(Collectors.toList());
            proFormaInvoiceItemRepository.saveAll(proFormaInvoiceItemEntityList);
        }

        return proFormaInvoiceRepository.save(proFormaInvoiceEntityUpdated).toDTO();
    }

    @Override
    public ProFormaInvoiceValue getProFormaInvoice(String tenantUuid, String proFormaInvoiceUuid) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantUuid, proFormaInvoiceUuid);
        return proFormaInvoiceEntity.toDTO();
    }

    @Override
    public int deleteProFormaInvoice(String tenantUuid, String proFormaInvoiceUuid) {
        return proFormaInvoiceRepository.deleteByUuid(proFormaInvoiceUuid);
    }
    //TODO: Remove this code lateron
/*    @Override
    public List<ProFormaInvoiceValue> getAllProFormaInvoice(String tenantUuid) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = proFormaInvoiceRepository.findAllByTenantEntity_Id(tenantEntity.getId());
        return proFormaInvoiceEntities.stream().map(pie -> pie.toDTO()).collect(Collectors.toList());
    }*/

    @Override
    public List<ProFormaInvoiceMinimal> getAllProFormaInvoice(String tenantUuid, LocalDateTime startDate, LocalDateTime endDate) {
        return proFormaInvoiceRepository.findAllByTenantUuid(tenantUuid, startDate, endDate);
    }

    @Override
    public List<ProFormaInvoiceOrdersProjection> getAllProFormOrdersDetails(String tenantUuid, String deptType) {
        List<ProFormaInvoiceOrdersProjection> proFormaInvoiceOrdersProjections;
        DeptTypeEnum deptTypeEnum;
        switch (deptType) {
            case  "optimize":
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfOptimize(tenantUuid);
                break;
            case "cutting":
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfCutting(tenantUuid);
                break;
            case "dispatch":
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfDispatch(tenantUuid);
                break;
            case "toughen":
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfToughen(tenantUuid);
                break;
            default:
                throw new ResourceNotFoundException();
        }
        return proFormaInvoiceOrdersProjections;
    }

    @Override
    public List<ProFormaInvoiceIndividualsOrdersProjection> getAllProFormIndividualsOrdersDetails(String tenantUuid, String proFormaInvoiceUuid,String deptType) {
        List<ProFormaInvoiceIndividualsOrdersProjection> proFormaInvoiceOrdersProjections;
        switch (deptType) {
            case "optimize":
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfOptimizeIndividual(tenantUuid,proFormaInvoiceUuid);
                break;
            case "cutting":
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfCuttingIndividual(tenantUuid,proFormaInvoiceUuid);
                break;
            case "dispatch":
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfDispatchIndividual(tenantUuid,proFormaInvoiceUuid);
                break;
            case "toughen":
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfToughenIndividual(tenantUuid,proFormaInvoiceUuid);
                break;
            default:
                throw new ResourceNotFoundException();
        }
        return proFormaInvoiceOrdersProjections;
    }
}
