package com.edeja.edejaEducation.repositories.adminDao;

import com.edeja.edejaEducation.entity.adminEntity.XAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface XAdminUserDao extends JpaRepository<XAdminUser, Integer> {

    @Query("select u from XAdminUser u where u.userName = :userName")
    XAdminUser findAccountByLogin(@Param("userName") String email);
}
