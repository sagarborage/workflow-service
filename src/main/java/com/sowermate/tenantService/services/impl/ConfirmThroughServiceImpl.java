package com.sowermate.tenantService.services.impl;

import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.repositories.ConfirmThroughRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.repositories.Utlity.CommonUtils;
import com.sowermate.tenantService.services.ConfirmThroughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackForClassName = {"Exception"})
public class ConfirmThroughServiceImpl implements ConfirmThroughService {

    @Autowired
    private ConfirmThroughRepository confirmThroughRepository;

    @Autowired
    private ProFormaInvoiceRepository proFormaInvoiceRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public ConfirmThroughValue createConfirmThrough(ConfirmThroughValue confirmThroughValue) throws Exception {

        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(confirmThroughValue.getTenantValue().getUuid());

        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(confirmThroughValue.getTenantValue().getUuid(),
                confirmThroughValue.getProFormaInvoice().getProFormInvoiceUuid());

        ConfirmThroughEntity confirmThroughEntity = confirmThroughValue.toEntity().toBuilder()
                .confirmThroughUuid(CommonUtils.generateUUID())
                .tenantEntity(tenantEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity).build();

        /*confirmThroughValue.toEntity().toBuilder()
                .tenantEntity(tenantEntity)
                .build();
        ConfirmThroughEntity confirmThroughEntity = new ConfirmThroughEntity();
        BeanUtils.copyProperties(confirmThroughValue, confirmThroughEntity);
        confirmThroughEntity.setConfirmThroughUuid(CommonUtils.generateUUID());
        confirmThroughEntity.setTenantEntity(tenantRepository.findByTenantUuid(confirmThroughValue.getTenantUuid()));
        BeanUtils.copyProperties(confirmThroughRepository.save(confirmThroughEntity), confirmThroughValue);*/
        return confirmThroughRepository.save(confirmThroughEntity).toDTO();
    }

    @Override
    public List<ConfirmThroughValue> getAllConfirmThrough(String tenantUuid) throws Exception {
        List<ConfirmThroughValue> confirmThroughValues = new ArrayList<>();
        ConfirmThroughValue confirmThroughValue = null;
        List<ConfirmThroughEntity> confirmThroughEntities = confirmThroughRepository.findAllByTenantEntity_Uuid(tenantUuid);
/*        for (int i = 0; i < confirmThroughEntities.size(); i++) {
            confirmThroughValue = new ConfirmThroughValue();
            BeanUtils.copyProperties(confirmThroughEntities.get(i), confirmThroughValue);
            confirmThroughValue.setTenantUuid(tenantUuid);
            confirmThroughValues.add(confirmThroughValue);
        }*/

        return confirmThroughEntities.stream().map(cte -> cte.toDTO()).collect(Collectors.toList());
    }

    @Override
    public ConfirmThroughValue editConfirmThrough(ConfirmThroughValue confirmThroughValue) throws Exception {
/*        ConfirmThroughEntity confirmThroughEntity = new ConfirmThroughEntity();
        BeanUtils.copyProperties(confirmThroughValue, confirmThroughEntity);

        // Check that UUID is not null before searching for the tenant
        if (confirmThroughValue.getConfirmThroughUuid() != null) {
            ConfirmThroughEntity matchingConfirmThrough = confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(confirmThroughValue.getTenantUuid(), confirmThroughValue.getConfirmThroughUuid());
            if (matchingConfirmThrough != null) {
                confirmThroughEntity.setConfirmThroughId(matchingConfirmThrough.getConfirmThroughId());
                confirmThroughEntity.setTenantEntity(tenantRepository.findByTenantUuid(confirmThroughValue.getTenantUuid()));
                BeanUtils.copyProperties(confirmThroughRepository.save(confirmThroughEntity), confirmThroughValue);
            } else {
            }
        } else {
            throw new Exception("UUID cannot be null");
        }

        return confirmThroughValue;*/
        TenantEntity tenantEntity = tenantRepository.findByTenantUuid(confirmThroughValue.getTenantValue().getUuid());

        ProFormaInvoiceEntity proFormaInvoiceEntity = proFormaInvoiceRepository.findByTenantEntity_UuidAndProFormInvoiceUuid(confirmThroughValue.getTenantValue().getUuid(),
                confirmThroughValue.getProFormaInvoice().getProFormInvoiceUuid());
        ConfirmThroughEntity tempConfirmThroughEntity = confirmThroughRepository
                .findByTenantEntity_UuidAndConfirmThroughUuid(confirmThroughValue.getConfirmThroughUuid(), confirmThroughValue.getTenantValue().getUuid());

        ConfirmThroughEntity confirmThroughEntity = confirmThroughValue.toEntity().toBuilder()
                .confirmThroughId(tempConfirmThroughEntity.getConfirmThroughId())
                .tenantEntity(tenantEntity)
                .proFormaInvoiceEntity(proFormaInvoiceEntity).build();
        return confirmThroughRepository.save(confirmThroughEntity).toDTO();
    }

    @Override
    public ConfirmThroughValue getConfirmThrough(String tenantUuid, String confirmThroughUuid) throws Exception {
/*        ConfirmThroughValue confirmThroughValue = new ConfirmThroughValue();
        ConfirmThroughEntity confirmThroughEntity = confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUuid, confirmThroughUuid);
        BeanUtils.copyProperties(confirmThroughEntity, confirmThroughValue);
        confirmThroughValue.setTenantUuid(tenantUuid);*/
        return confirmThroughRepository.findByTenantEntity_UuidAndConfirmThroughUuid(tenantUuid, confirmThroughUuid).toDTO();
    }

    @Override
    public int deleteConfirmThrough(String tenantUuid, String confirmThroughUuid) throws Exception {

        return confirmThroughRepository.deleteByConfirmThroughUuid(confirmThroughUuid);

    }
}
