package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.minimal.CompanyInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query("SELECT c FROM CompanyEntity c " +
            "JOIN c.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND c.uuid = :companyUuid")
    CompanyEntity findByTenantEntity_UuidAndCompanyEntityUuid(String tenantUuid, String companyUuid);
    //CompanyEntity findByCompanyUuid(String companyUuid);
    List<CompanyEntity> findAllByTenantEntityId(Long tenantId);

    @Transactional
    @Modifying
    @Query("UPDATE CompanyEntity c SET c.isActive = false WHERE c.tenantEntity.uuid = :tenantUuid and c.uuid = :companyUuid")
    void softDelete(@Param("tenantUuid") String tenantUuid, @Param("companyUuid") String companyUuid);

    CompanyEntity getCompanyEntityByUuid(@Param("companyUuid") String companyUuid);

    @Query("SELECT c.companyName as companyName, " +
            "t.address as address," +
            "t.state as state," +
            "t.pinCode as pinCode," +
            "t.phoneNumber as phoneNumber," +
            "t.emailId as emailId" +
            " FROM CompanyEntity c " +
            "JOIN c.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND c.uuid = :companyUuid")
    CompanyInfoProjection getCompanyInfo(String tenantUuid, String companyUuid);
}
