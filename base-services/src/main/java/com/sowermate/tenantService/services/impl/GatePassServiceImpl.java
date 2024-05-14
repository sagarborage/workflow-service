package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.GatePassEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.GatePassValue;
import com.sowermate.tenantService.repositories.CompanyRepository;
import com.sowermate.tenantService.repositories.GatePassRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.GatePassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GatePassServiceImpl implements GatePassService {

    @Autowired
    private GatePassRepository gatePassRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Override
    public GatePassValue createGatePass(GatePassValue gatePassValue) {

        TenantEntity tenantEntity = tenantRepository.findByUuid(gatePassValue.getTenantUuid());
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantEntity.getUuid(), gatePassValue.getProFormaInvoiceUuid());
        CompanyEntity companyEntity = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantEntity.getUuid(), gatePassValue.getCompanyUuid());
        CompanyEntity partyCompanyEntity = companyRepository.getCompanyEntityByUuid(proFormaInvoiceEntity.getCompanyIdBill().getUuid());

        GatePassEntity gatePassEntity = gatePassValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .companyEntity(companyEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity)
                .partyCompanyEntity(partyCompanyEntity)
                .build();
        return gatePassRepository.save(gatePassEntity).toDTO();
    }

    @Override
    public GatePassValue updateGatePass(GatePassValue gatePassValue) {
        TenantEntity tenantEntity = tenantRepository.findByUuid(gatePassValue.getTenantUuid());
        GatePassEntity tempGatePassEntity = gatePassRepository.findByUuid(gatePassValue.getUuid());
        GatePassEntity gatePassEntity = gatePassValue.toEntity().toBuilder()
                .id(tempGatePassEntity.getId())
                .createdDateTime(tempGatePassEntity.getCreatedDateTime())
                .createdBy(tempGatePassEntity.getCreatedBy())
                .tenantEntity(tenantEntity)
                .companyEntity(companyRepository.getCompanyEntityByUuid(gatePassValue.getCompanyUuid()))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByUuid(gatePassValue.getProFormaInvoiceUuid()))
                .partyCompanyEntity(companyRepository.getCompanyEntityByUuid(gatePassValue.getPartyCompanyUuid()))
                .gatePassNo(gatePassValue.getGatePassNo())
                .createdDateTime(tempGatePassEntity.getCreatedDateTime())
                .createdBy(tempGatePassEntity.getCreatedBy())
                .version(tempGatePassEntity.getVersion())
                .build();
        return gatePassRepository.save(gatePassEntity).toDTO();
    }

    GatePassEntity getGatePassEntity(String gatePassUuid, String tenantUuid) {
        GatePassEntity gatePassEntity = gatePassRepository.findByGatePassUuidAndTenantUuid(gatePassUuid, tenantUuid);
        if (gatePassEntity == null) {
            throw new RuntimeException("gate pass is not found with uuid : " + gatePassUuid);
        }
        return gatePassEntity;
    }

    @Override
    public GatePassValue getGatePass(String gatePassUuid, String tenantUuid) {
        return getGatePassEntity(gatePassUuid, tenantUuid).toDTO();
    }


    @Override
    public GatePassValue deleteGatePass(String tenantUuid, String gatePassUuid) {
        GatePassEntity gatePassEntity = getGatePassEntity(gatePassUuid, tenantUuid);
        gatePassEntity.setIsActive(false);
        return gatePassEntity.toDTO();
    }

    @Override
    public List<GatePassValue> getAllGatePass(String tenantUuid, String companyUuid) {
        List<GatePassEntity> gatePassEntities = gatePassRepository.findByTenantUuidAndCompanyUuid(tenantUuid, companyUuid);
        return gatePassEntities.stream().map(GatePassEntity::toDTO).collect(Collectors.toList());
    }

}
