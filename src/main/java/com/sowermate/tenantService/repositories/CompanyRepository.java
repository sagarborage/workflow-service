package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.CompanyEntity;
import com.sowermate.tenantService.entities.CompanyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    @Query("SELECT c FROM CompanyEntity c " +
            "JOIN c.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND c.companyUuid = :companyUuid")
    public CompanyEntity findByTenantEntity_UuidAndCompanyUuid(String tenantUuid, String companyUuid);

   // public List<CompanyEntity> deleteByUuid(@Param("uuid") String uuid);
   //@Query("SELECT c FROM CompanyEntity c JOIN FETCH c.companyTypeEntity ct WHERE c.tenantEntity.uuid = :tenantUuid")
    public List<CompanyEntity> findAllByTenantEntityTenantId(int tenantId);

   // public List<CompanyEntity> findByCompanyId(int companyId);
    @Transactional
    @Modifying
    @Query("UPDATE CompanyEntity c SET c.isActive = false WHERE c.companyUuid = :companyUuid")
    void softDelete(@Param("companyUuid") String companyUuid);

    CompanyEntity getCompanyEntityByCompanyUuid(@Param("companyUuid") String companyUuid);
}
