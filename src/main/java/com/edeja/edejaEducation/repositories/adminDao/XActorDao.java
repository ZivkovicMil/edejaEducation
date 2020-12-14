package com.edeja.edejaEducation.repositories.adminDao;

import com.edeja.edejaEducation.entity.adminEntity.XActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface XActorDao extends JpaRepository<XActor, Integer> {

    @Modifying
    @Query("update XActor u set u.userLanguage = :userLanguage where u.id = :x_id")
    void updateLanguage(@Param("userLanguage") String userLanguage, @Param("x_id") Integer x_id);
}
