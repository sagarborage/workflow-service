package com.sowermate.workflow.service.services.impl;

import com.sowermate.workflow.domain.entities.GatePassDetailsEntity;
import com.sowermate.workflow.domain.entities.GatePassEntity;
import com.sowermate.workflow.domain.entities.ProFormaInvoiceItemEntity;
import com.sowermate.workflow.domain.entities.value.GatePassDetailsValue;
import com.sowermate.workflow.persistence.repositories.GatePassDetailsRepository;
import com.sowermate.workflow.persistence.repositories.GatePassRepository;
import com.sowermate.workflow.persistence.repositories.ProFormaInvoiceItemRepository;
import com.sowermate.workflow.service.services.GatePassDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class GatePassDetailsServiceImpl implements GatePassDetailsService {
    @Autowired
    private GatePassDetailsRepository gatePassDetailsRepository;

    @Autowired
    private GatePassRepository gatePassRepository;

    @Autowired
    private ProFormaInvoiceItemRepository proFormaInvoiceItemRepository;

    @Override
    public GatePassDetailsValue createGatePassDetails(GatePassDetailsValue gatePassDetailsValue) {
        GatePassDetailsEntity gatePassDetailsEntity = gatePassDetailsValue.toEntity().toBuilder()
                .proFormaInvoiceItemEntity(proFormaInvoiceItemRepository.findByUuid(gatePassDetailsValue.getProFormaInvoiceItemUuid()))
                .gatePassEntity(gatePassRepository.findByUuid(gatePassDetailsValue.getGatePassUuid())).build();
        return gatePassDetailsRepository.save(gatePassDetailsEntity).toDTO();
    }

    @Override
    public GatePassDetailsValue updateGatePassDetails(GatePassDetailsValue gatePassDetailsValue) {
        GatePassDetailsEntity gatePassDetailsEntityTemp = gatePassDetailsRepository.findByProFormaInvoiceItemEntity_UuidAndGatePassEntityUuidAndGatePassDetailsUuid(gatePassDetailsValue.getUuid(),
                gatePassDetailsValue.getProFormaInvoiceItemUuid(), gatePassDetailsValue.getGatePassUuid());
        GatePassDetailsEntity gatePassDetailsEntity = gatePassDetailsValue.toEntity().toBuilder()
                .proFormaInvoiceItemEntity(proFormaInvoiceItemRepository.findByUuid(gatePassDetailsValue.getProFormaInvoiceItemUuid()))
                .gatePassEntity(gatePassRepository.findByUuid(gatePassDetailsValue.getGatePassUuid()))
                .id(gatePassDetailsEntityTemp.getId())
                .createdDateTime(gatePassDetailsEntityTemp.getCreatedDateTime())
                .createdBy(gatePassDetailsEntityTemp.getCreatedBy())
                .version(gatePassDetailsEntityTemp.getVersion())
                .build();
        return gatePassDetailsRepository.save(gatePassDetailsEntity).toDTO();
    }

    @Override
    public GatePassDetailsValue getGatePassDetails(String gatePassDetailsUuid, String proFormaInvoiceItemUuid, String gatePassUuid) {
        GatePassDetailsEntity gatePassDetailsEntity = gatePassDetailsRepository
                .findByProFormaInvoiceItemEntity_UuidAndGatePassEntityUuidAndGatePassDetailsUuid(gatePassDetailsUuid, proFormaInvoiceItemUuid, gatePassUuid);
        return gatePassDetailsEntity.toDTO();
    }

    @Override
    public List<GatePassDetailsValue> getAllGatePassDetails(String proFormaInvoiceItemUuid, String gatePassUuid) {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = proFormaInvoiceItemRepository.findByUuid(proFormaInvoiceItemUuid);
        GatePassEntity gatePassEntity = gatePassRepository.findByUuid(gatePassUuid);
        List<GatePassDetailsEntity> gatePassDetailsEntities = gatePassDetailsRepository.findByProFormaInvoiceItemEntityIdAndGatePassEntityId(proFormaInvoiceItemEntity.getId(), gatePassEntity.getId());
        return gatePassDetailsEntities.stream().map(gatePassDetails -> gatePassDetails.toDTO()).collect(Collectors.toList());
    }


//    @Override
//    public GatePassDetailsValue deleteGatePassDetails(String gatePassDetailsUuid, String proFormaInvoiceItemUuid, String gatePassUuid) {
//        GatePassDetailsEntity gatePassDetailsEntity = gatePassDetailsRepository.findByUuid(gatePassDetailsUuid);
//
//        gatePassDetailsRepository.delete(gatePassDetailsEntity);
//    }

    @Override
    public GatePassDetailsValue deleteGatePassDetails(String gatePassDetailsUuid, String proFormaInvoiceItemUuid, String gatePassUuid) {
        GatePassDetailsEntity gatePassDetailsEntity = getGatePassDetailsEntity(gatePassDetailsUuid, proFormaInvoiceItemUuid, gatePassUuid);
        gatePassDetailsEntity.setIsActive(false);
        return gatePassDetailsEntity.toDTO();
    }


    GatePassDetailsEntity getGatePassDetailsEntity(String gatePassDetailsUuid, String proFormaInvoiceItemUuid, String gatePassUuid) {
        GatePassDetailsEntity gatePassDetailsEntity = gatePassDetailsRepository.findByProFormaInvoiceItemEntity_UuidAndGatePassEntityUuidAndGatePassDetailsUuid(gatePassDetailsUuid, proFormaInvoiceItemUuid, gatePassUuid);
        if (gatePassDetailsEntity == null) {
            throw new RuntimeException("gate pass is not found with uuid : " + gatePassUuid);
        }
        return gatePassDetailsEntity;
    }

}
