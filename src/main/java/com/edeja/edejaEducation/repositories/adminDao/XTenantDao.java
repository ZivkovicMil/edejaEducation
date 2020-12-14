package com.edeja.edejaEducation.repositories.adminDao;

import com.edeja.edejaEducation.entity.adminEntity.XTenant;
import com.edeja.edejaEducation.models.TenantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XTenantDao extends JpaRepository<XTenant, Integer> {

    @Query("SELECT new com.edeja.edejaEducation.models.TenantId(t.tenantId) from XTenant t")
    List<TenantId> getAllTenantsNames();

}
