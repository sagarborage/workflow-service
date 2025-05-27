package com.sowermate.workflow.service.services.impl;

import com.sowermate.core.tenant.entities.Company;
import com.sowermate.core.tenant.entities.Tenant;
import com.sowermate.core.tenant.repositories.CompanyRepository;
import com.sowermate.core.tenant.repositories.TenantRepository;
import com.sowermate.core.tenant.services.CompanyService;
import com.sowermate.workflow.domain.entities.GatePassDetailsEntity;
import com.sowermate.workflow.domain.entities.GatePassEntity;
import com.sowermate.workflow.domain.entities.ProFormaInvoiceEntity;
import com.sowermate.workflow.domain.entities.ProFormaInvoiceItemEntity;
import com.sowermate.workflow.domain.entities.minimal.GatePassDetailsInfoProjection;
import com.sowermate.workflow.domain.entities.minimal.GatePassInfoProjection;
import com.sowermate.workflow.domain.entities.minimal.GlassInfoProjection;
import com.sowermate.workflow.domain.entities.value.GatePassDetailsInfo;
import com.sowermate.workflow.domain.entities.value.GatePassInfo;
import com.sowermate.workflow.domain.entities.value.GatePassValue;
import com.sowermate.workflow.domain.projection.PiInfoProjectionForReport;
import com.sowermate.workflow.persistence.repositories.GatePassRepository;
import com.sowermate.workflow.persistence.repositories.ProFormaInvoiceItemRepository;
import com.sowermate.workflow.persistence.repositories.ProFormaInvoiceRepository;
import com.sowermate.workflow.service.services.GatePassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    private ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;

    @Autowired
    private CompanyService companyService;

    @Override
    @Transactional
    public GatePassValue createGatePass(GatePassValue gatePassValue) {

        Tenant tenantEntity = tenantRepository.findByUuid(gatePassValue.getTenantUuid());
        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndproFormaInvoiceUuid(tenantEntity.getUuid(), gatePassValue.getProFormaInvoiceUuid());
        //Company companyEntity = companyRepository.findByTenantEntity_UuidAndCompanyEntityUuid(tenantEntity.getUuid(), gatePassValue.getCompanyUuid());
        Company companyEntity = companyService.getCompanyEntity(gatePassValue.getCompanyUuid(), tenantEntity.getUuid());
        Company partyCompanyEntity = companyService.getCompanyEntity(proFormaInvoiceEntity.getCompanyIdBill().getUuid());

        GatePassEntity gatePassEntity = gatePassValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .companyEntity(companyEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity)
                .partyCompanyEntity(partyCompanyEntity)
                .build();
        Integer gatePassNo = gatePassRepository.findMaxGatePassNoByCompanyUuid(companyEntity.getUuid());
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntityList = proFormaInvoiceEntity.getProFormaInvoiceItemEntities();
        List<GatePassDetailsEntity> gatePassDetailsEntities = new ArrayList<>();

        for (ProFormaInvoiceItemEntity proFormaInvoiceItemEntity : proFormaInvoiceItemEntityList) {
            if (proFormaInvoiceItemEntity.getGatePassBucket() > 0) {
                GatePassDetailsEntity gatePassDetailsEntity = new GatePassDetailsEntity();
                gatePassDetailsEntity.setProFormaInvoiceItemEntity(proFormaInvoiceItemEntity);
                gatePassDetailsEntity.setGatePassEntity(gatePassEntity);
                gatePassDetailsEntity.setGatePassQty(proFormaInvoiceItemEntity.getGatePassBucket());
                gatePassDetailsEntity.setIsActive(gatePassEntity.getIsActive());
                gatePassDetailsEntities.add(gatePassDetailsEntity);
                proFormaInvoiceItemEntity.setGatePassCompleted(proFormaInvoiceItemEntity.getGatePassCompleted() + proFormaInvoiceItemEntity.getGatePassBucket());
                proFormaInvoiceItemEntity.setGatePassBucket(0);
                proFormaInvoiceItemRepository.save(proFormaInvoiceItemEntity);
            }
        }
        gatePassEntity.setGatePassDetailsEntities(gatePassDetailsEntities);
        if (gatePassNo == null) {
            gatePassEntity.setGatePassNo(1);
        } else {
            gatePassEntity.setGatePassNo(gatePassNo + 1);
        }

        return gatePassRepository.save(gatePassEntity).toDTO();
    }

    @Override
    public GatePassValue updateGatePass(GatePassValue gatePassValue) {
        Tenant tenantEntity = tenantRepository.findByUuid(gatePassValue.getTenantUuid());
        GatePassEntity tempGatePassEntity = gatePassRepository.findByUuid(gatePassValue.getUuid());
        GatePassEntity gatePassEntity = gatePassValue.toEntity().toBuilder()
                .id(tempGatePassEntity.getId())
                .createdDateTime(tempGatePassEntity.getCreatedDateTime())
                .createdBy(tempGatePassEntity.getCreatedBy())
                .tenantEntity(tenantEntity)
                .companyEntity(companyService.getCompanyEntity(gatePassValue.getCompanyUuid()))
                .proFormaInvoiceEntity(proFormaInvoiceRepository.findByUuid(gatePassValue.getProFormaInvoiceUuid()))
                .partyCompanyEntity(companyService.getCompanyEntity(gatePassValue.getPartyCompanyUuid()))
                .gatePassNo(gatePassValue.getGatePassNo())
                .createdDateTime(tempGatePassEntity.getCreatedDateTime())
                .createdBy(tempGatePassEntity.getCreatedBy())
                .version(tempGatePassEntity.getVersion())
                .build();
        return gatePassRepository.save(gatePassEntity).toDTO();
    }

    GatePassEntity getGatePassEntity(String gatePassUuid, String tenantUuid, String companyUuid) {
        GatePassEntity gatePassEntity = gatePassRepository.findByGatePassUuidAndTenantUuid(gatePassUuid, tenantUuid, companyUuid);
        if (gatePassEntity == null) {
            throw new RuntimeException("gate pass is not found with uuid : " + gatePassUuid);
        }
        return gatePassEntity;
    }

    @Override
    public List<GlassInfoProjection> getGlassInfoForReport(String gatePassUuid) {
        List<GlassInfoProjection> glassInfoProjections = gatePassRepository.findGlassItemsInfoByGatePassUuid(gatePassUuid);
        return Objects.requireNonNullElseGet(glassInfoProjections, ArrayList::new);
    }

    @Override
    public PiInfoProjectionForReport getPiInfoForReport(String uuid) {
        return gatePassRepository.findPiItemsInfoByGatePassUuid(uuid);
    }

    @Override
    public GatePassValue getGatePass(String gatePassUuid, String tenantUuid, String companyUuid) {
        return getGatePassEntity(gatePassUuid, tenantUuid, companyUuid).toDTO();
    }


    @Override
    public GatePassValue deleteGatePass(String gatePassUuid, String tenantUuid, String companyUuid) {
        GatePassEntity gatePassEntity = getGatePassEntity(gatePassUuid, tenantUuid, companyUuid);
        gatePassEntity.setIsActive(false);
        return gatePassRepository.save(gatePassEntity).toDTO();
    }

    @Override
    public GatePassInfo getGatePassByProformaInvoice(String companyUuid, String proformaInvoiceUuid) {
        GatePassInfoProjection gatePassInfoProjection = gatePassRepository.findGatePassInfoByProformaInvoiceUuid(companyUuid, proformaInvoiceUuid);
        List<GatePassDetailsInfoProjection> gatePassDetailsInfoProjectionList = gatePassRepository.findGatePassDetailsInfoByProformaInvoiceUuid(companyUuid, proformaInvoiceUuid);
        GatePassInfo gatePassInfo = new GatePassInfo();
        if (gatePassInfoProjection != null) {
            gatePassInfo.setProFormInvoiceUuid(proformaInvoiceUuid);
            gatePassInfo.setTotalQuantity(gatePassInfoProjection.getTotalQuantity());
            gatePassInfo.setDispatchedQuantity(gatePassInfoProjection.getDispatchedQuantity());
            gatePassInfo.setGatePassBucket(gatePassInfoProjection.getGatePassBucket());
            gatePassInfo.setIsIsGatePassCreationEnable(gatePassInfoProjection.getGatePassBucket() != null && gatePassInfoProjection.getGatePassBucket() > 0);
        }
        List<GatePassDetailsInfo> gatePassDetailsInfoList = gatePassDetailsInfoProjectionList.stream().map(gatePassDetailsInfoProjection -> {
            GatePassDetailsInfo gatePassDetailsInfo = new GatePassDetailsInfo();
            gatePassDetailsInfo.setPartyName(gatePassDetailsInfoProjection.getPartyName());
            gatePassDetailsInfo.setProFormInvoiceUuid(proformaInvoiceUuid);
            gatePassDetailsInfo.setGatePassUuid(gatePassDetailsInfoProjection.getGatePassUuid());
            gatePassDetailsInfo.setGatePassNo(gatePassDetailsInfoProjection.getGatePassNo());
            gatePassDetailsInfo.setQuantity(gatePassDetailsInfoProjection.getQuantity());
            return gatePassDetailsInfo;
        }).toList();
        gatePassInfo.setGatePassDetailsInfoList(gatePassDetailsInfoList);
        return gatePassInfo;
    }

    @Override
    public List<GatePassValue> getAllGatePass(String tenantUuid, String companyUuid) {
        List<GatePassEntity> gatePassEntities = gatePassRepository.findByTenantUuidAndCompanyUuid(tenantUuid, companyUuid);
        return gatePassEntities.stream().map(GatePassEntity::toDTO).collect(Collectors.toList());
    }


}
