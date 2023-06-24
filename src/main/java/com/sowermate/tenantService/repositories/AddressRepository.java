package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.AddressEntity;

import com.sowermate.tenantService.entities.GlassTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, String> {


        @Query("SELECT a FROM AddressEntity a " +
                "JOIN a.tenantEntity t " +
                "WHERE t.uuid = :tenantUuid " +
                "AND a.addressUuid = :addressUuid")
        public AddressEntity findByTenantEntity_UuidAndAddressUuid(String tenantUuid, String addressUuid);

   // public AddressEntity findByAddressUuid (@Param("addressUuid")String addressUuid);

   // public AddressEntity deleteByAddressUuid (@Param("addressUuid")String addressUuid);

    public List<AddressEntity> findAllByTenantEntity_Uuid(String tenantUuid);
   // public List<AddressEntity> findByAddressId(int addressId);

    @Transactional
    @Modifying
    @Query("UPDATE AddressEntity a SET a.isActive = false WHERE a.addressUuid = :addressUuid")
    void softDelete(@Param("addressUuid") String addressUuid);

}
