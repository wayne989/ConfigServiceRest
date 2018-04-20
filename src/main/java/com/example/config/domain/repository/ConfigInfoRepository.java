package com.example.config.domain.repository;

import com.example.config.domain.repository.entity.ConfigInfoDbo;
import com.example.config.domain.repository.entity.ConfigInfoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * as part of feature on the spring jpa framework, the interface will be automatically
 * implemented by spring for CRUD data access functionality.
 */
@Repository
public interface ConfigInfoRepository extends JpaRepository<ConfigInfoDbo, ConfigInfoPK>{
    List<ConfigInfoDbo> findAllByKeyAppCode(String appCode);
    Optional<ConfigInfoDbo> findById(ConfigInfoPK key);
}