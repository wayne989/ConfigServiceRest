package com.example.config.domain.repository.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Tolerate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * This is Entity class for database object, corresponding to config table in the database.
 * @Data generates all the boilerplate that is normally associated with simple POJO
 * @Entity to indicate this is JPA entity
 * @Table to indicate the name of table
 * @AllArgsConstructor is from lombok pre-compiler to generate the public constructor with all arguments
 * EntityListeners is to enable auditing entity events.
 */
@Data
@Entity
@Table(name="config")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
public class ConfigInfoDbo implements Comparable<ConfigInfoDbo> {

    @NonNull
    @EmbeddedId
    private ConfigInfoPK key;

    @Lob
    @Column(name = "CONFIG_DETAIL")
    private String configDetail;

    // lastUpdate is the version field for jpa optimistic locking
    // the framework will compare the version number prior to performing update.
    // if version at update time is different from the previous retrival time,
    // then OptimisticLockException will be thrown
    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE")
    private Date lastUpdate;

    @Tolerate
    public ConfigInfoDbo() {
        super();
    }

    ;

    @Tolerate
    public ConfigInfoDbo(String appCode, String version, String configDetail) {
        this.key = new ConfigInfoPK(appCode, version);
        this.configDetail = configDetail;
    }

    @Override
    public int compareTo(ConfigInfoDbo anotherConfigInfoDbo) throws ClassCastException {
        int rtnValue = 0;
        if (this.getLastUpdate().before(anotherConfigInfoDbo.getLastUpdate())) {
            rtnValue = 1;
        } else {
            rtnValue = -1;
        }
        return rtnValue;
    }
}