package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.CompanyAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface    CompanyAddressRepository extends JpaRepository<CompanyAddressEntity ,String> {
    @Query("SELECT c FROM CompanyAddressEntity c " +
            "JOIN c.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND c.companyAddressUuid = :companyAddressUuid")
    public CompanyAddressEntity findByTenantEntity_UuidAndCompanyAddressUuid(String tenantUuid, String companyAddressUuid);
    public List<CompanyAddressEntity> findAllByTenantEntity_Uuid(String tenantUuid);

    @Transactional
    @Modifying
    @Query("UPDATE CompanyAddressEntity c SET c.isActive = false WHERE c.companyAddressUuid = :companyAddressUuid")
    int softDelete(@Param("companyAddressUuid") String companyAddressUuid);

}
