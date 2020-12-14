package com.edeja.edejaEducation.repositories.adminDao;

import com.edeja.edejaEducation.entity.adminEntity.XUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface XUserDao extends JpaRepository<XUser, Integer> {

    @Query("select u from XUser u where u.email = :email")
    XUser findAccountByLogin(@Param("email") String email);

    @Modifying
    @Query("update XUser u set u.salutation = :salutation where u.xId = :xId")
    void updatexUserSalutation(@Param("xId") Integer xId, @Param("salutation") String salutation);

    @Modifying
    @Transactional
    @Query("update XUser u set u.sendStatistic = :sendStatistic where u.email = :email")
    void updateFlagStatistics(@Param("email") String email, @Param("sendStatistic") boolean sendStatistic);

    @Query("select u from XUser u where u.email = :email")
    XUser getSendStatistic(@Param("email") String email);
}
