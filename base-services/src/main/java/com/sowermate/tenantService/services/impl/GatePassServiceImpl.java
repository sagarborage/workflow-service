package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.minimal.GatePassDetailsInfoProjection;
import com.sowermate.tenantService.entities.minimal.GatePassInfoProjection;
import com.sowermate.tenantService.entities.minimal.GatePassProjection;
import com.sowermate.tenantService.entities.minimal.GlassInfoProjection;
import com.sowermate.tenantService.entities.value.GatePassDetailsInfo;
import com.sowermate.tenantService.entities.value.GatePassExcelReport;
import com.sowermate.tenantService.entities.value.GatePassExcelReportData;
import com.sowermate.tenantService.entities.value.GatePassInfo;
import com.sowermate.tenantService.entities.value.GatePassValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.GatePassService;
import com.sowermate.tenantService.services.PiInfoProjectionForReport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    @Transactional
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
        Integer gatePassNo = gatePassRepository.findMaxGatePassNoByCompanyUuid(companyEntity.getUuid());
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntityList = proFormaInvoiceEntity.getProFormaInvoiceItemEntities();
        List<GatePassDetailsEntity> gatePassDetailsEntities = new ArrayList<>();

        for (ProFormaInvoiceItemEntity proFormaInvoiceItemEntity: proFormaInvoiceItemEntityList){
            if (proFormaInvoiceItemEntity.getGatePassBucket()>0) {
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
        if (gatePassNo==null){
            gatePassEntity.setGatePassNo(1);
        }else {
            gatePassEntity.setGatePassNo(gatePassNo+1);
        }

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

    GatePassEntity getGatePassEntity(String gatePassUuid, String tenantUuid,String companyUuid) {
        GatePassEntity gatePassEntity = gatePassRepository.findByGatePassUuidAndTenantUuid(gatePassUuid, tenantUuid,companyUuid);
        if (gatePassEntity == null) {
            throw new RuntimeException("gate pass is not found with uuid : " + gatePassUuid);
        }
        return gatePassEntity;
    }

    @Override
    public List<Map<String, Object>> getAllGatePassWithProformaDetails(String tenantUuid, String companyUuid, String firmUuid, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);
        companyUuid = StringUtils.isBlank(companyUuid) ? null : companyUuid;
        firmUuid = StringUtils.isBlank(firmUuid) ? null : firmUuid;
        List<GatePassProjection> projections = gatePassRepository.findGatePassDetailsWithProformaDetails(tenantUuid, companyUuid, firmUuid, fromDateTime, toDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return projections.stream().map(p -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("uuid", p.getUuid());
            map.put("tenantUuid", p.getTenantUuid());
            map.put("companyUuid", p.getCompanyUuid());
            map.put("firmUuid", p.getFirmUuid());
            map.put("firmName", p.getFirmName());
            map.put("gatePassNo", p.getGatePassNo());
            map.put("piNumber", p.getPiNumber());
            map.put("partyName", p.getPartyName());
            map.put("companyName", p.getCompanyName());
            map.put("getPassDate", p.getDateTime() != null ? p.getDateTime().format(formatter) : null);
            map.put("quantity", p.getQuantity());
            map.put("vehicleDetails", p.getVehicleDetails());
            map.put("createdBy", p.getCreatedBy());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<GatePassExcelReportData> getGatePassExcel(GatePassExcelReport filter) {
        String tenantUuid = filter.getTenantUuid();
        String companyUuid = StringUtils.isBlank(filter.getPartyUuid()) ? null : filter.getPartyUuid(); // Assuming partyUuid = companyUuid
        String firmUuid = StringUtils.isBlank(filter.getFirmUuid()) ? null : filter.getFirmUuid();

        LocalDate fromDate = LocalDate.parse(filter.getFromDate());
        LocalDate toDate = LocalDate.parse(filter.getToDate());
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59, 999_999_999);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<GatePassProjection> projections = gatePassRepository.findGatePassDetailsWithProformaDetails(
                tenantUuid, companyUuid, firmUuid, fromDateTime, toDateTime);

        return projections.stream().map(p -> {
            GatePassExcelReportData data = new GatePassExcelReportData();
            data.setGatePassNo(p.getGatePassNo());
            data.setCreatedBy(p.getCreatedBy());
            data.setPiNumber(p.getPiNumber());
            data.setPartyName(p.getPartyName());
            data.setFirmName(p.getFirmName());
            data.setVehicleDetails(p.getVehicleDetails());
            data.setQuantity(p.getQuantity());
            data.setDateTime(p.getDateTime() != null ? p.getDateTime().format(formatter) : null);
            return data;
        }).collect(Collectors.toList());
    }

    @Override
    public List<GlassInfoProjection> getGlassInfoForReport(String gatePassUuid) {
        List<GlassInfoProjection> glassInfoProjections =  gatePassRepository.findGlassItemsInfoByGatePassUuid(gatePassUuid);
        return Objects.requireNonNullElseGet(glassInfoProjections, ArrayList::new);
    }

    @Override
    public PiInfoProjectionForReport getPiInfoForReport(String uuid) {
        return gatePassRepository.findPiItemsInfoByGatePassUuid(uuid);
    }

    @Override
    public GatePassValue getGatePass(String gatePassUuid, String tenantUuid,String companyUuid) {
        return getGatePassEntity(gatePassUuid, tenantUuid,companyUuid).toDTO();
    }


    @Override
    public GatePassValue deleteGatePass(String gatePassUuid, String tenantUuid, String companyUuid) {
        GatePassEntity gatePassEntity = getGatePassEntity(gatePassUuid, tenantUuid,companyUuid);
        gatePassEntity.setIsActive(false);
        return gatePassEntity.toDTO();
    }

    @Override
    public GatePassInfo getGatePassByProformaInvoice(String companyUuid, String proformaInvoiceUuid) {
        GatePassInfoProjection gatePassInfoProjection = gatePassRepository.findGatePassInfoByProformaInvoiceUuid(companyUuid, proformaInvoiceUuid);
        List<GatePassDetailsInfoProjection> gatePassDetailsInfoProjectionList = gatePassRepository.findGatePassDetailsInfoByProformaInvoiceUuid(companyUuid, proformaInvoiceUuid);
        GatePassInfo gatePassInfo = new GatePassInfo();
        if (gatePassInfoProjection!=null){
            gatePassInfo.setProFormInvoiceUuid(proformaInvoiceUuid);
            gatePassInfo.setTotalQuantity(gatePassInfoProjection.getTotalQuantity());
            gatePassInfo.setDispatchedQuantity(gatePassInfoProjection.getDispatchedQuantity());
            gatePassInfo.setGatePassBucket(gatePassInfoProjection.getGatePassBucket());
            gatePassInfo.setIsIsGatePassCreationEnable(gatePassInfoProjection.getGatePassBucket() !=null && gatePassInfoProjection.getGatePassBucket() > 0);
        }
        List<GatePassDetailsInfo> gatePassDetailsInfoList = gatePassDetailsInfoProjectionList.stream().map(gatePassDetailsInfoProjection->{
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
