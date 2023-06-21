package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.AddressEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, String> {


    public AddressEntity findByAddressUuid (@Param("addressUuid")String addressUuid);

   // public AddressEntity deleteByAddressUuid (@Param("addressUuid")String addressUuid);

    public List<AddressEntity> findAll();
   // public List<AddressEntity> findByAddressId(int addressId);

    @Transactional
    @Modifying
    @Query("UPDATE AddressEntity a SET a.isActive = false WHERE a.addressUuid = :addressUuid")
    void softDelete(@Param("addressUuid") String uuid);

}
