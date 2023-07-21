package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.AddressTypeEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.ServiceRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressTypeRepository  extends JpaRepository<AddressTypeEntity, String> {

    //   public AddressTypeEntity findByAddressTypeUuid(@Param("addressTypeUuid")String addressTypeUuid);


    @Query("SELECT s FROM AddressTypeEntity s " +
            "JOIN s.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND s.addressTypeUuid = :addressTypeUuid")
    public AddressTypeEntity findByTenantEntity_UuidAndAddressTypeUuid(@Param("tenantUuid") String tenantUuid, @Param("addressTypeUuid") String addressTypeUuid);
    //public List<AddressTypeEntity> findByAddressTypeId(int addressTypeId);

    @Transactional
    @Modifying
    @Query("UPDATE ServiceRateEntity s SET s.isActive = false WHERE s.serviceRateUuid = :serviceRateUuid")
    void softDelete(@Param("serviceRateUuid") String serviceRateUuid);

    public List<AddressTypeEntity> findAllByTenantEntity_Uuid(String tenantUuid);
}
