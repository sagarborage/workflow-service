package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceMinimal;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.exceptions.ResourceNotFoundException;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
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
                    .piTypeEntity(getPiType(tenantUUID, proFormaInvoiceValue.getPiTypeUuid()))
                    .firm(getFirm(tenantUUID, proFormaInvoiceValue.getFirmUuid()))
                    .companyIdBill(getCompanyIdBill(tenantUUID, proFormaInvoiceValue.getCompanyBillToUuid()))
                    .companyIdShip(getCompanyIdShip(tenantUUID, proFormaInvoiceValue.getCompanyShipToUuid()))
                    .piNumber(piNumber)
                    .invoiceDate(LocalDateTime.now())
                    .build();
            return proFormaInvoiceRepository.save(proFormaInvoiceEntity).toDTO();
        }
    }


    public CompanyEntity getCompanyIdShip(String tenantUuid, String companyIdShipUuid){
        CompanyEntity companyIdShip = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid,companyIdShipUuid);
        if(companyIdShip == null){
            throw new ResourceNotFoundException("CompanyEntity","tenantUuid or companyIdShipUuid",tenantUuid+" or "+companyIdShipUuid);
        }
        return companyIdShip;
    }
    public CompanyEntity getCompanyIdBill(String tenantUuid, String companyIdBillUuid){
        CompanyEntity companyIdBill = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid,companyIdBillUuid);
        if(companyIdBill == null){
            throw new ResourceNotFoundException("CompanyEntity","tenantUuid or companyIdBillUuid",tenantUuid+" or "+companyIdBillUuid);
        }
        return companyIdBill;
    }

    public CompanyEntity getFirm(String tenantUuid, String firmUuid){
        CompanyEntity firm = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantUuid,firmUuid);
        if(firm == null){
            throw new ResourceNotFoundException("CompanyEntity","tenantUuid or firmUuid",tenantUuid+" or "+firmUuid);
        }
        return firm;
    }

    public PiTypeEntity getPiType(String tenantUuid, String PiTypeUuid){
        PiTypeEntity piTypeEntity = piTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(tenantUuid,PiTypeUuid);
        if(piTypeEntity == null){
            throw new ResourceNotFoundException("PiTypeEntity","tenantUuid or PiTypeUuid",tenantUuid+" or "+PiTypeUuid);
        }
        return piTypeEntity;
    }

    public ConfirmThroughEntity getConfirmThroughEntity(String tenantUuid,String confirmThroughUuid){
        ConfirmThroughEntity confirmThrough = confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUuid,confirmThroughUuid);
        if(confirmThrough == null){
            throw new ResourceNotFoundException("ConfirmThroughEntity","tenantUuid or ConfirmThroughUuid",tenantUuid+" or "+confirmThroughUuid);
        }
        return confirmThrough;
    }

    public TenantEntity getTenant(String tenantUuid){
        TenantEntity tenant = tenantRepository.findByUuid(tenantUuid);
        if(tenant == null){
            throw new ResourceNotFoundException("TenantEntity","tenantUuid",tenantUuid);
        }
        return tenant;
    }
    public ProFormaInvoiceEntity getProFormaInvoiceEntity(String tenantUuid, String proFormaInvoiceUuid){
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantUuid, proFormaInvoiceUuid);
        if(proFormaInvoiceEntity == null){
            throw new ResourceNotFoundException("ProformInvoiceEntity","ProformInvoiceUuid or tenantUuid",tenantUuid+" or "+proFormaInvoiceUuid);
        }
        return proFormaInvoiceEntity;
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
                proFormaInvoiceValue.getUuid());

        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceValue.toEntity().toBuilder()
                .id(tempProFormaInvoiceEntity.getId())
                .tenantEntity(tenantRepository.findByUuid(proFormaInvoiceValue.getTenantUuid()))
                .confirmThroughEntity(
                        null == proFormaInvoiceValue.getConfirmThroughUuid() ? null :
                                confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUUID, proFormaInvoiceValue.getConfirmThroughUuid())
                )
                .proFormaInvoiceAmount(proFormaInvoiceValue.getProFormaInvoiceAmount())
                .piTypeEntity(getPiType(tenantUUID, proFormaInvoiceValue.getPiTypeUuid()))
                .firm(getFirm(tenantUUID, proFormaInvoiceValue.getFirmUuid()))
                .companyIdBill(getCompanyIdBill(tenantUUID, proFormaInvoiceValue.getCompanyBillToUuid()))
                .companyIdShip(getCompanyIdShip(tenantUUID, proFormaInvoiceValue.getCompanyShipToUuid()))
                .invoiceDate(LocalDateTime.now())
                .build();
        return proFormaInvoiceRepository.save(proFormaInvoiceEntity).toDTO().toBuilder().tenantUuid(tenantUUID).build();
    }

    @Override
    public ProFormaInvoiceValue getProFormaInvoice(String tenantUuid, String proFormaInvoiceUuid) {
        ProFormaInvoiceEntity proFormaInvoiceEntity = getProFormaInvoiceEntity(tenantUuid,proFormaInvoiceUuid);
        return proFormaInvoiceEntity.toDTO();
    }

    @Override
    public void deleteProFormaInvoice(String tenantUuid, String proFormaInvoiceUuid) {
        int status= proFormaInvoiceRepository.deleteByUuid(proFormaInvoiceUuid);
        if(status==0){
            throw new ResourceNotFoundException("ProformInvoice","ProformInvoiceUuid or tenantUuid",tenantUuid+" or "+proFormaInvoiceUuid);
        }
    }
    //TODO: Remove this code lateron
/*    @Override
    public List<ProFormaInvoiceValue> getAllProFormaInvoice(String tenantUuid) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(tenantUuid);
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = proFormaInvoiceRepository.findAllByTenantEntity_Id(tenantEntity.getId());
        return proFormaInvoiceEntities.stream().map(pie -> pie.toDTO()).collect(Collectors.toList());
    }*/

    @Override
    public List<ProFormaInvoiceMinimal> getAllProFormaInvoice(String tenantUuid,LocalDateTime startDate, LocalDateTime endDate) {
        return  proFormaInvoiceRepository.findAllByTenantUuid(tenantUuid,startDate,endDate);
    }
}
