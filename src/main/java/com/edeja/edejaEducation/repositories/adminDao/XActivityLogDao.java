package com.edeja.edejaEducation.repositories.adminDao;

import com.edeja.edejaEducation.entity.adminEntity.XActivityLog;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface XActivityLogDao extends JpaRepository<XActivityLog, Integer> {

    @Query(value = "select u from XActivityLog u where" +
            " u.applicationName = :applicationName" +
            " and (:username is null or u.username = :username or :username = '')" +
            " and (:sendername is null or u.sendername = :sendername or :sendername = '')" +
            " and (:category is null or u.category = :category or :category = '')" +
            " and (:startDate is null or u.logDate >= :startDate or :startDate = '')" +
            " and (:endDate is null or u.logDate <= :endDate or :endDate = '')"
    )
    Page<XActivityLog> findActivityData(
            @Param("applicationName") String applicationName,
            @Param("username") String username,
            @Param("sendername") String sendername,
            @Param("category") String category,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query(value = "select u from XActivityLog u where" +
            " u.applicationName = :applicationName" +
            " and (:username is null or u.username = :username or :username = '')" +
            " and (:sendername is null or u.sendername = :sendername or :sendername = '')" +
            " and (:category is null or u.category = :category or :category = '')" +
            " and (:startDate is null or u.logDate >= :startDate or :startDate = '')" +
            " and (:endDate is null or u.logDate <= :endDate or :endDate = '')"
    )
    List<XActivityLog> findActivityDataList(
            @Param("applicationName") String applicationName,
            @Param("username") String username,
            @Param("sendername") String sendername,
            @Param("category") String category,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT u FROM XActivityLog u where u.username = :username and u.category = :category")
    Collection<XActivityLog> findAllLogs(@Param("username") String username, @Param("category") String category);

    @Query("SELECT u FROM XActivityLog u WHERE u.category = 'SALUTATION_CHANGED' and u.username = :username")
    Collection<XActivityLog> findAllSalutations(@Param("username") String username, Sort sort);
}
