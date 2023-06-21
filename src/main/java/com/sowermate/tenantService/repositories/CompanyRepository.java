package com.sowermate.tenantService.repositories;

import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    @Query("SELECT c FROM CompanyEntity c WHERE c.companyUuid = :companyUuid")
    public CompanyEntity findByUuid(@Param("companyUuid") String companyUuid);

   // public List<CompanyEntity> deleteByUuid(@Param("uuid") String uuid);

    public List<CompanyEntity> findAll();

    public List<CompanyEntity> findByCompanyId(int companyId);
    @Transactional
    @Modifying
    @Query("UPDATE CompanyEntity c SET c.isActive = false WHERE c.companyUuid = :companyUuid")
    void softDelete(@Param("companyUuid") String companyUuid);

}
