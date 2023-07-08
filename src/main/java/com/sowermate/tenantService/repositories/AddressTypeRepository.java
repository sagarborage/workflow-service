package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.AddressTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypeRepository  extends JpaRepository<AddressTypeEntity, String> {

    public AddressTypeEntity findByAddressTypeUuid(@Param("addressTypeUuid")String addressTypeUuid);
}
