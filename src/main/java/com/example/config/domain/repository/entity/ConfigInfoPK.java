package com.example.config.domain.repository.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * ConfigInfoDbo Primary Key
 */
@Embeddable
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ConfigInfoPK implements Serializable{
    @Column(name="APP_CODE", length=30)
    private String appCode;
    @Column(name="VERSION", length=20)
    private String version;
    @Tolerate
    public ConfigInfoPK(){super();};
}