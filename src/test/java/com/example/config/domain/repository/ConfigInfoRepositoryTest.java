package com.example.config.domain.repository;

import com.example.config.domain.repository.entity.ConfigInfoDbo;
import com.example.config.domain.repository.entity.ConfigInfoPK;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Wayne on 2018-04-20.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ConfigInfoRepositoryTest {
    @Autowired
    private ConfigInfoRepository configInfoRepository;
    private ConfigInfoDbo configInfoDbo1;
    private ConfigInfoDbo configInfoDbo2;


    private final static String existingAppCode = "ACCTSVC";
    private final static String existingVersion = "v1";
    private final static String nonexistingAppCode = "M16";
    private final static String nonexistingVersion = "v10";
    private final static String acctsvcV1 = "{\"appCode\":\"ACCTSVC\", \"version\":\"v1\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";
    private final static String acctsvcV2 = "{\"appCode\":\"ACCTSVC\", \"version\":\"v2\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";


    @Before
    public void setUp() throws Exception{
        configInfoDbo1 = new ConfigInfoDbo("ACCTSVC", "v1", acctsvcV1);
        configInfoDbo2 = new ConfigInfoDbo("ACCTSVC", "v2", acctsvcV2);
    }

    @Test
    public void findAllByKeyAppCodeRecordFound(){

        LinkedList<ConfigInfoDbo> configInfoDboListExpected = new LinkedList<>();
        configInfoDboListExpected.addFirst(configInfoDbo1);
        configInfoDboListExpected.addLast(configInfoDbo2);
        List<ConfigInfoDbo> configInfoDboList = configInfoRepository.findAllByKeyAppCode(existingAppCode);
        configInfoDboList.stream().forEach(configInfoDbo -> Assert.assertEquals(configInfoDboListExpected.remove().getConfigDetail() ,configInfoDbo.getConfigDetail()));
    }

    @Test
    public void findAllByKeyAppCodeRecordNotFound(){
        List<ConfigInfoDbo> configInfoDboList = configInfoRepository.findAllByKeyAppCode(nonexistingAppCode);
        Assert.assertEquals(0, configInfoDboList.size());
    }

    @Test
    public void findByIdRecordFound(){
        ConfigInfoPK  configInfoPK = configInfoDbo1.getKey();
        Optional<ConfigInfoDbo> configInfoDboOpt =  configInfoRepository.findById(configInfoPK);
        ConfigInfoDbo configInfoDbo = configInfoDboOpt.get();
        Assert.assertEquals(acctsvcV1, configInfoDbo.getConfigDetail());
    }

    @Test
    public void findByIdRecordNotFound(){
        ConfigInfoPK  configInfoPK = new ConfigInfoPK(existingAppCode, nonexistingVersion);
        Optional<ConfigInfoDbo> configInfoDboOpt =  configInfoRepository.findById(configInfoPK);
        Assert.assertFalse(configInfoDboOpt.isPresent());

        configInfoPK = new ConfigInfoPK(nonexistingAppCode, nonexistingVersion);
        configInfoDboOpt =  configInfoRepository.findById(configInfoPK);
        Assert.assertFalse(configInfoDboOpt.isPresent());

        configInfoPK = new ConfigInfoPK(nonexistingAppCode, existingVersion);
        configInfoDboOpt =  configInfoRepository.findById(configInfoPK);
        Assert.assertFalse(configInfoDboOpt.isPresent());
    }
}
