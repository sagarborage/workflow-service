package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.AddressTypeEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressTypeRepository  extends JpaRepository<AddressTypeEntity, String> {

    public AddressTypeEntity findByAddressTypeUuid(@Param("addressTypeUuid")String addressTypeUuid);

    public List<AddressTypeEntity> findAllByTenantEntity_Uuid(String tenantUuid);
}
