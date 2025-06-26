package com.sowermate.tenantService.services.impl;

import com.sowermate.image.services.PdfService;
import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.DeptTypeEnum;
import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import com.sowermate.tenantService.entities.GlassThicknessEntity;
import com.sowermate.tenantService.entities.GlassTypeEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.WorkOrderEntity;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceOrdersProjection;
import com.sowermate.tenantService.entities.minimal.ProformaInvoiceProjection;
import com.sowermate.tenantService.entities.minimal.ProformaInvoiceWithWorkOrderProjection;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceHomeDetails;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.ConfirmThroughRepository;
import com.sowermate.tenantService.repositories.GlassSpecificationRepository;
import com.sowermate.tenantService.repositories.GlassThicknessRepository;
import com.sowermate.tenantService.repositories.GlassTypeRepository;
import com.sowermate.tenantService.repositories.PiTypeRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceItemRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.ServiceRateRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.WorkOrderRepository;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Synchronized;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ProFormaInvoiceServiceImpl implements ProFormaInvoiceService {

    @Autowired
    WorkOrderRepository workOrderRepository;
    @Autowired
    ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;
    @Autowired
    GlassSpecificationRepository glassSpecificationRepository;
    @Autowired
    GlassThicknessRepository glassThicknessRepository;
    @Autowired
    GlassTypeRepository glassTypeRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;
    @Autowired
    private ServiceRateRepository serviceRateRepository;
    @Autowired
    private ConfirmThroughRepository confirmThroughRepository;
    @Autowired
    private PiTypeRepository piTypeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired(required = true)
    private PdfService pdfService;

    @Override
    @Transactional
    @Synchronized
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
                    .companyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getPartyBillToUuid()))
                    .companyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getPartyShipToUuid()))
                    .piNumber(piNumber)
                    .invoiceDate(LocalDateTime.now())
                    .version(proFormaInvoiceValue.getVersion())
                    .build();
            return proFormaInvoiceRepository.save(proFormaInvoiceEntity).toDTO();
        }
    }

    private String generatePiNumber(long tenantId) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findFirstByTenantEntityIdOrderByIdDesc(tenantId);

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
        ProFormaInvoiceEntity tempProFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(proFormaInvoiceValue.getTenantUuid(),
                proFormaInvoiceValue.getProFormaInvoiceUuid());

        return updateInvoiceWithItems(tempProFormaInvoiceEntity, proFormaInvoiceValue).toDTO();
    }

    public ProFormaInvoiceEntity updateInvoiceWithItems(ProFormaInvoiceEntity existingInvoice, ProFormaInvoiceValue proFormaInvoiceValue) {
        String tenantUUID = proFormaInvoiceValue.getTenantUuid();

        List<ProFormaInvoiceItemValue> proFormaInvoiceItems = proFormaInvoiceValue.getProFormaInvoiceItems();
        List<ServiceRateInvoiceValue> serviceRateInvoiceItems = proFormaInvoiceValue.getServiceRateInvoices();

        // Create a set of IDs from the updated items for efficient comparison
        Map<String, GlassTypeEntity> glassTypeEntityMap = glassTypeRepository.findAllByTenantEntity_Uuid(existingInvoice.getTenantEntity().getUuid())
                .stream().collect(Collectors.toMap(GlassTypeEntity::getUuid, glassTypeEntity -> glassTypeEntity));
        Map<String, GlassSpecificationEntity> specificationEntityMap = glassSpecificationRepository.findAllByTenantEntity_Uuid(existingInvoice.getTenantEntity().getUuid())
                .stream().collect(Collectors.toMap(GlassSpecificationEntity::getUuid, glassTypeEntity -> glassTypeEntity));
        Map<String, GlassThicknessEntity> thicknessEntityMap = glassThicknessRepository.findAllByTenantEntity_Uuid(existingInvoice.getTenantEntity().getUuid()).
                stream().collect(Collectors.toMap(GlassThicknessEntity::getUuid, glassTypeEntity -> glassTypeEntity));

        Set<String> updatedItemIds = proFormaInvoiceItems.stream()
                .map(ProFormaInvoiceItemValue::getUuid)
                .collect(Collectors.toSet());
        Set<String> toBeUpdatedServiceRateInvoiceItems = serviceRateInvoiceItems.stream()
                .map(ServiceRateInvoiceValue::getUuid)
                .collect(Collectors.toSet());

        List<ProFormaInvoiceItemEntity> removedItems = existingInvoice.getProFormaInvoiceItemEntities().stream().peek(item -> {
            if (item.getId() != null && !updatedItemIds.contains(item.getUuid())) {
                if (item.getFileUrl() != null) {
                    if (!item.getFileUrl().equals("Error handling the PDF."))
                        pdfService.deleteFileAndParentDirectoryByUrl(item.getFileUrl());
                }
            }
        }).toList();

        // Remove items from the existing list that are not present in the updated list
        existingInvoice.getProFormaInvoiceItemEntities().removeIf(item ->
                item.getId() != null && !updatedItemIds.contains(item.getUuid()));


        // Remove items from the existing list that are not present in the updated list
        existingInvoice.getServiceRateInvoiceEntities().removeIf(serviceRate ->
                serviceRate.getId() != null && !toBeUpdatedServiceRateInvoiceItems.contains(serviceRate.getUuid()));

        // Update or add items to the existing list
        for (ProFormaInvoiceItemValue updatedItem : proFormaInvoiceItems) {
            ProFormaInvoiceItemEntity existingItem = findItemById(existingInvoice, updatedItem.getUuid());
            WorkOrderEntity workOrderEntity = existingInvoice.getWorkOrderEntity();
            if (existingItem != null) {
                // Update existing item
                updateItemFromValue(workOrderEntity,
                        existingItem,
                        updatedItem,
                        glassTypeEntityMap,
                        specificationEntityMap,
                        thicknessEntityMap
                );
            } else {
                // Add new item
                ProFormaInvoiceItemEntity newInvoiceItem = addNewInvoiceItem(workOrderEntity, proFormaInvoiceValue, updatedItem);
                existingInvoice.getProFormaInvoiceItemEntities().add(newInvoiceItem);
            }
        }

        for (ServiceRateInvoiceValue toBeUServiceRateInvoiceValue : serviceRateInvoiceItems) {
            ServiceRateInvoiceEntity existingItem = findByServiceRateId(existingInvoice, toBeUServiceRateInvoiceValue.getUuid());

            if (existingItem != null) {
                // Update existing item
                updateServiceRateFromValue(existingItem,
                        toBeUServiceRateInvoiceValue
                );
            } else {
                // Add new item
                ServiceRateInvoiceEntity serviceRateInvoice = addNewServiceRateItem(proFormaInvoiceValue, toBeUServiceRateInvoiceValue);
                existingInvoice.getServiceRateInvoiceEntities().add(serviceRateInvoice);
            }
        }

        existingInvoice.setConfirmThroughEntity(
                null == proFormaInvoiceValue.getConfirmThroughUuid() ? null :
                        confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUUID, proFormaInvoiceValue.getConfirmThroughUuid())
        );

        existingInvoice.setPiTypeEntity(piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(tenantUUID, proFormaInvoiceValue.getPiTypeUuid()));
        existingInvoice.setFirm(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getFirmUuid()));
        existingInvoice.setCompanyIdBill(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getPartyBillToUuid()));
        existingInvoice.setCompanyIdShip(companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUUID, proFormaInvoiceValue.getPartyShipToUuid()));
        existingInvoice.setProFormaInvoiceAmount(proFormaInvoiceValue.getProFormaInvoiceAmount());
        existingInvoice.setServiceRateInvoiceAmount(proFormaInvoiceValue.getServiceRateInvoiceAmount());
        existingInvoice.setBasicAmount(proFormaInvoiceValue.getBasicAmount());
        existingInvoice.setAdminCharges(proFormaInvoiceValue.getAdminCharges());
        existingInvoice.setInsurancePercent(proFormaInvoiceValue.getInsurancePercent());
        existingInvoice.setInsurancePercentAmount(proFormaInvoiceValue.getInsurancePercentAmount());
        existingInvoice.setProxSqft(proFormaInvoiceValue.getProxSqft());
        existingInvoice.setProxPerSqftRate(proFormaInvoiceValue.getProxPerSqftRate());
        existingInvoice.setProxCharges(proFormaInvoiceValue.getProxCharges());
        existingInvoice.setOtherCharges(proFormaInvoiceValue.getOtherCharges());
        existingInvoice.setTransportCharges(proFormaInvoiceValue.getTransportCharges());
        existingInvoice.setIsGstApplicable(proFormaInvoiceValue.getIsGstApplicable());
        existingInvoice.setGstCharges(proFormaInvoiceValue.getGstCharges());
        existingInvoice.setGrandTotal(proFormaInvoiceValue.getGrandTotal());
        existingInvoice.setRoundOffAmount(proFormaInvoiceValue.getRoundOffAmount());
        existingInvoice.setPayableAmount(proFormaInvoiceValue.getPayableAmount());
        existingInvoice.setPreviousBalance(proFormaInvoiceValue.getPreviousBalance());
        existingInvoice.setShippingAddress(proFormaInvoiceValue.getShippingAddress() == null || proFormaInvoiceValue.getShippingAddress().trim().equals("") ? null : proFormaInvoiceValue.getShippingAddress());
        existingInvoice.setAdjustmentAmount(proFormaInvoiceValue.getAdjustmentAmount());
        //existingInvoice.setStatus(proFormaInvoiceValue.getStatus()); //No need to update status as it has to be same as DB status
        return proFormaInvoiceRepository.save(existingInvoice);
    }

    private ProFormaInvoiceItemEntity findItemById(ProFormaInvoiceEntity invoice, String itemUuid) {
        return invoice.getProFormaInvoiceItemEntities().stream()
                .filter(item -> (item.getUuid() != null && item.getUuid().equals(itemUuid)))
                .findFirst()
                .orElse(null);
    }

    private ServiceRateInvoiceEntity findByServiceRateId(ProFormaInvoiceEntity invoice, String serviceUuid) {
        return invoice.getServiceRateInvoiceEntities().stream()
                .filter(serviceRate -> (serviceRate.getUuid() != null && serviceRate.getUuid().equals(serviceUuid)))
                .findFirst()
                .orElse(null);
    }

    private void updateItemFromValue(WorkOrderEntity workOrderEntity, ProFormaInvoiceItemEntity itemEntity, ProFormaInvoiceItemValue itemValue, Map<String, GlassTypeEntity> glassTypeEntityMap,
                                     Map<String, GlassSpecificationEntity> specificationEntityMap, Map<String, GlassThicknessEntity> thicknessEntityMap) {
        // Update fields based on your business logic
        itemEntity.setGlassTypeEntity(glassTypeEntityMap.get(itemValue.getGlassTypeUuid()));
        if (workOrderEntity != null) {
            if (!Objects.equals(itemEntity.getQuantity(), itemValue.getQuantity())) {
                if (itemEntity.getQuantity() < itemValue.getQuantity()) {
                    itemEntity.setOptimizeBucket(itemEntity.getOptimizeBucket() + (itemValue.getQuantity() - itemEntity.getQuantity()));
                } else {
                    var result = itemEntity.getOptimizeBucket() - (itemEntity.getQuantity() - itemValue.getQuantity());
                    if (result > 0) {
                        itemEntity.setOptimizeBucket(result);
                    } else {
                        throw new RuntimeException("Can not decrease Qty as optimize is completed");
                    }
                }
            }
        }
        itemEntity.setGlassSpecificationEntity(specificationEntityMap.get(itemValue.getGlassSpecificationUuid()));
        itemEntity.setGlassThicknessEntity(thicknessEntityMap.get(itemValue.getGlassThicknessUuid()));
        itemEntity.setWidthInch(itemValue.getWidthInch());
        itemEntity.setWidthMeasurement(itemValue.getWidthMeasurement());
        itemEntity.setWidthMeasurementLabel(itemValue.getWidthMeasurementLabel());
        itemEntity.setActualWidth(itemValue.getActualWidth());
        itemEntity.setChargeableWidth(itemValue.getChargeableWidth());
        itemEntity.setHeightInch(itemValue.getHeightInch());
        itemEntity.setHeightMeasurement(itemValue.getHeightMeasurement());
        itemEntity.setHeightMeasurementLabel(itemValue.getHeightMeasurementLabel());
        itemEntity.setActualHeight(itemValue.getActualHeight());
        itemEntity.setChargeableHeight(itemValue.getChargeableHeight());
        itemEntity.setExtraMm(itemValue.getExtraMm());
        itemEntity.setQuantity(itemValue.getQuantity());
        itemEntity.setUnitValue(itemValue.getUnitValue());
        itemEntity.setRatePerUnit(itemValue.getRatePerUnit());
        itemEntity.setUnitMeasurementLabel(itemValue.getUnitMeasurementLabel());
        itemEntity.setAmount(itemValue.getAmount());
    }

    private ProFormaInvoiceItemEntity addNewInvoiceItem(WorkOrderEntity workOrderEntity, ProFormaInvoiceValue proFormaInvoiceValue, ProFormaInvoiceItemValue proFormaInvoiceItemValue) {
        String tenantUuid = proFormaInvoiceValue.getTenantUuid();
        return proFormaInvoiceItemValue.toEntity().toBuilder()
                .tenantEntity(tenantRepository.findByUuid(tenantUuid))
                .optimizeBucket(workOrderEntity == null ? 0 : proFormaInvoiceItemValue.getQuantity())
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(
                        tenantUuid, proFormaInvoiceItemValue.getProFormaInvoiceUuid()))
                .glassThicknessEntity(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassThicknessUuid()))
                .glassTypeEntity(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassTypeUuid()))
                .glassSpecificationEntity(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(tenantUuid,
                        proFormaInvoiceItemValue.getGlassSpecificationUuid()))
                .build();
    }

    private ServiceRateInvoiceEntity addNewServiceRateItem(ProFormaInvoiceValue proFormaInvoiceValue, ServiceRateInvoiceValue serviceRateInvoiceValue) {
        String tenantUuid = proFormaInvoiceValue.getTenantUuid();
        return serviceRateInvoiceValue.toEntity().toBuilder()
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(
                        tenantUuid, proFormaInvoiceValue.getProFormaInvoiceUuid()))
                .serviceRateEntity(serviceRateRepository.findByTenantEntity_UuidAndServiceRateUuid(tenantUuid, serviceRateInvoiceValue.getServiceRateUuid()))
                .build();
    }


    private void updateServiceRateFromValue(ServiceRateInvoiceEntity serviceRateInvoice, ServiceRateInvoiceValue serviceRateInvoiceValue) {
        serviceRateInvoice.setRate(serviceRateInvoiceValue.getRate());
        serviceRateInvoice.setQuantity(serviceRateInvoiceValue.getQuantity());
        serviceRateInvoice.setTotal(serviceRateInvoiceValue.getTotal());
    }

    @Override
    public ProFormaInvoiceValue updateConfirmThrough(String tenantUuid, String proFormaInvoiceUuid, String confirmThroughUuid) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantUuidPIUuidAndPICurrentStatus(tenantUuid, proFormaInvoiceUuid, ProformaInvoiceStatusEnum.NEW);
        if (!ObjectUtils.isEmpty(proFormaInvoiceEntity)) {
            ConfirmThroughEntity confirmThroughEntity = confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUuid, confirmThroughUuid);
            if (confirmThroughEntity == null) {
                throw new ResourceNotFoundException();
            }
            proFormaInvoiceEntity.setConfirmThroughEntity(confirmThroughEntity);
            ProFormaInvoiceEntity proFormaInvoiceEntityUpdated = proFormaInvoiceRepository.save(proFormaInvoiceEntity.toBuilder()
                    .status(ProformaInvoiceStatusEnum.CONFIRM).build());

            WorkOrderEntity workOrderEntity = new WorkOrderEntity();
            workOrderEntity.setProFormaInvoiceEntity(proFormaInvoiceEntity);
            workOrderEntity.setTenantEntity(proFormaInvoiceEntity.getTenantEntity());
            workOrderEntity.setFirm(proFormaInvoiceEntity.getFirm());
            workOrderEntity.setIsActive(true);
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
        return null;
    }

    @Override
    public ProFormaInvoiceValue updatePIStatus(String tenantUuid, String proFormaInvoiceUuid, ProformaInvoiceStatusEnum currentStatus, ProformaInvoiceStatusEnum newStatus, String statusDetails) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantUuidPIUuidAndPICurrentStatus(tenantUuid, proFormaInvoiceUuid, currentStatus);

        if (!ObjectUtils.isEmpty(proFormaInvoiceEntity)) {
            if (newStatus.equals(ProformaInvoiceStatusEnum.CANCEL) || newStatus.equals(ProformaInvoiceStatusEnum.HOLD)) {
                proFormaInvoiceEntity = proFormaInvoiceEntity.toBuilder().status(newStatus).statusDetails(statusDetails).build();
            } else if (newStatus.equals(ProformaInvoiceStatusEnum.NEW)) {
                proFormaInvoiceEntity = proFormaInvoiceEntity.toBuilder().status(newStatus).statusDetails("").build();
            }
            return proFormaInvoiceRepository.save(proFormaInvoiceEntity).toDTO();
        }
        return null;
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

    @Override
    public List<ProFormaInvoiceHomeDetails> getAllProFormaInvoice(String tenantUuid, String companyUuid, LocalDateTime startDate, LocalDateTime endDate) {
        List<ProFormaInvoiceEntity> proFormInvoiceEntities = proFormaInvoiceRepository.findAllByTenantUuid(tenantUuid, /*, companyUuid,*/ startDate, endDate);
        List<ProFormaInvoiceHomeDetails> proFormaInvoiceHomeDetails = new ArrayList<>();
        for (ProFormaInvoiceEntity proFormaInvoiceEntity : proFormInvoiceEntities) {
            proFormaInvoiceHomeDetails.add(getProFormaInvoiceHomeDetails(proFormaInvoiceEntity));
        }
        return proFormaInvoiceHomeDetails;
    }

    private ProFormaInvoiceHomeDetails getProFormaInvoiceHomeDetails(ProFormaInvoiceEntity proFormaInvoiceEntity) {
        return ProFormaInvoiceHomeDetails.newBuilder()
                .uuid(proFormaInvoiceEntity.getUuid())
                .invoiceDate(proFormaInvoiceEntity.getInvoiceDate())
                .confirmThroughUuid(null == proFormaInvoiceEntity.getConfirmThroughEntity() ? null : proFormaInvoiceEntity.getConfirmThroughEntity().getUuid())
                .partyName(proFormaInvoiceEntity.getCompanyIdBill().getCompanyName())
                .payableAmount(proFormaInvoiceEntity.getPayableAmount())
                .piNumber(proFormaInvoiceEntity.getPiNumber())
                .workOrderUuid(null == proFormaInvoiceEntity.getWorkOrderEntity() ? null : proFormaInvoiceEntity.getWorkOrderEntity().getUuid())
                .workOrderNumber(null == proFormaInvoiceEntity.getWorkOrderEntity() ? null : proFormaInvoiceEntity.getWorkOrderEntity().getId())
                .firmShortName(proFormaInvoiceEntity.getFirm().getCompanyName().toUpperCase().contains("HIMYOUG") ? "HIMYOUG" : "HIMANSHU") //TODO: Sagar temp condition
                .isGatePassEnabled(proFormaInvoiceEntity.getProFormaInvoiceItemEntities().stream().anyMatch(e -> e.getDispatchCompleted() > 0))
                .isEditAllowed(proFormaInvoiceEntity.getProFormaInvoiceItemEntities().stream().noneMatch(e -> e.getDispatchCompleted() > 0))
                .status(proFormaInvoiceEntity.getStatus())
                .build();
    }

    @Override
    public List<ProFormaInvoiceOrdersProjection> getAllProFormOrdersDetails(String tenantUuid, String deptType) {
        List<ProFormaInvoiceOrdersProjection> proFormaInvoiceOrdersProjections;

        switch (DeptTypeEnum.valueOf(deptType.toUpperCase())) {
            case OPTIMIZE:
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfOptimize(tenantUuid);
                break;
            case CUTTING:
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfCutting(tenantUuid);
                break;
            case DISPATCH:
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfDispatch(tenantUuid);
                break;
            case TOUGHEN:
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfToughen(tenantUuid);
                break;
            default:
                throw new ResourceNotFoundException();
        }
        return proFormaInvoiceOrdersProjections;
    }

    @Override
    public List<ProFormaInvoiceIndividualsOrdersProjection> getAllProFormIndividualsOrdersDetails(String tenantUuid, Integer workOrderNumber, String deptType) {
        List<ProFormaInvoiceIndividualsOrdersProjection> proFormaInvoiceOrdersProjections;
        switch (DeptTypeEnum.valueOf(deptType.toUpperCase())) {
            case OPTIMIZE:
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfOptimizeIndividual(tenantUuid, workOrderNumber);
                break;
            case CUTTING:
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfCuttingIndividual(tenantUuid, workOrderNumber);
                break;
            case DISPATCH:
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfDispatchIndividual(tenantUuid, workOrderNumber);
                break;
            case TOUGHEN:
                proFormaInvoiceOrdersProjections = proFormaInvoiceRepository.findAllPiOrdersDetailsOfToughenIndividual(tenantUuid, workOrderNumber);
                break;
            default:
                throw new ResourceNotFoundException();
        }
        return proFormaInvoiceOrdersProjections;
    }

    @Override
    public List<Map<String, Object>> getAllPiOrdersDetails(String tenantUuid, String companyUuid, String partyUuid, LocalDate fromDate, LocalDate toDate, String status) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);
        companyUuid = StringUtils.isBlank(companyUuid) ? null : companyUuid;
        partyUuid = StringUtils.isBlank(partyUuid) ? null : partyUuid;
        ProformaInvoiceStatusEnum statusEnum = EnumUtils.getEnum(ProformaInvoiceStatusEnum.class, status);
        List<ProformaInvoiceProjection> projections = proFormaInvoiceRepository.findAllPiOrdersDetails(tenantUuid, companyUuid, partyUuid, fromDateTime, toDateTime, statusEnum);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return projections.stream().map(p -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("uuid", p.getUuid());
            map.put("tenantUuid", p.getTenantUuid());
            map.put("firm", p.getFirm());
            map.put("companyUuid", p.getCompanyUuid());
            map.put("piNumber", p.getPiNumber());
            map.put("partyName", p.getPartyName());
            map.put("amount", p.getAmount());
            map.put("user", p.getUser());
            map.put("invoiceDate", p.getInvoiceDateTime() != null ? p.getInvoiceDateTime().format(formatter) : null);
            map.put("status", p.getPiStatus());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getAllPiOrdersDetailsWithWorkOrderDetails(String tenantUuid, String workOrderUuid, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);
        workOrderUuid = StringUtils.isBlank(workOrderUuid) ? null : workOrderUuid;
        List<ProformaInvoiceWithWorkOrderProjection> projections = proFormaInvoiceRepository.findAllPiOrdersDetailsWithWorkOrderDetails(tenantUuid, workOrderUuid, fromDateTime, toDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return projections.stream().map(p -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("uuid", p.getUuid());
            map.put("tenantUuid", p.getTenantUuid());
            map.put("firmName", p.getFirmName());
            map.put("workOrderUuid", p.getWorkOrderUuid());
            map.put("piNumber", p.getPiNumber());
            map.put("piType", p.getPiType());
            map.put("partyName", p.getPartyName());
            map.put("piDate", p.getPIDateTime() != null ? p.getPIDateTime().format(formatter) : null);
            map.put("workOrderDate", p.getWorkOrderDateTime() != null ? p.getWorkOrderDateTime().format(formatter) : null);
            map.put("amount", p.getAmount());
            map.put("user", p.getUser());
            map.put("SQFT", p.getSQFT());
            map.put("SQMTR", p.getSQMTR());
            return map;
        }).collect(Collectors.toList());
    }

}
