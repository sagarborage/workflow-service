package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyTypeEntity ,String> {

  /*// public List<CompanyTypeEntity> findByCompanyTypeId(int companyTypeId);

    public CompanyTypeEntity findByCompanyTypeUuid(@Param("companyTypeUuid")String companyTypeUuid);

    public List<CompanyTypeEntity> findAllByTenantEntity_Uuid(String tenantUuid);*/

    public CompanyTypeEntity findByUuid(@Param("companyTypeUuid")String companyTypeUuid);


  /*  @Query("SELECT s FROM CompanyTypeEntity s " +
            "JOIN s.tenantEntity t " +
            "WHERE t.uuid = :tenantUuid " +
            "AND s.companyTypeUuid = :companyTypeUuid")
    public CompanyTypeEntity findByTenantEntity_UuidAndCompanyTypeUuid(@Param("tenantUuid") String tenantUuid, @Param("companyTypeUuid") String companyTypeUuid);*/
    //public List<ServiceRateEntity> findByCompanyTypeId(int companyTypeId);

    @Transactional
    @Modifying
    @Query("UPDATE CompanyTypeEntity s SET s.isActive = false WHERE s.uuid = :companyTypeUuid")
    void softDelete(@Param("companyTypeUuid") String companyTypeUuid);

    //public List<CompanyTypeEntity> findAllByTenantEntity_Uuid(String tenantUuid);

}
