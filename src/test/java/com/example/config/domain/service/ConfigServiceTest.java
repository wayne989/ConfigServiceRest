package com.example.config.domain.service;

import com.example.config.common.Constants;
import com.example.config.domain.repository.ConfigInfoRepository;
import com.example.config.domain.repository.entity.ConfigInfoDbo;
import com.example.config.domain.repository.entity.ConfigInfoPK;
import com.example.config.domain.service.impl.ConfigServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Mockito.when;

/**
 * Created by Wayne on 2018-04-20.
 */
@RunWith(SpringRunner.class)
public class ConfigServiceTest {
    @MockBean(reset= MockReset.BEFORE)
    private ConfigInfoRepository configInfoRepository;

    private ConfigService configService;

    private String existingAppCode;
    private String existingVersion;
    private String acctsvcV1;
    private String acctsvcV2;
    private String acctsvcV4;
    private String acctsvcV9;
    private String nonexistingAppCode;
    private String nonexistingVersion;

    private ConfigInfoPK existingConfigInfoPK;
    private ConfigInfoPK v9ConfigInfoPK;
    private ConfigInfoDbo configInfoDbo1;
    private ConfigInfoDbo configInfoDbo2;
    private ConfigInfoDbo configInfoDbo3;
    private ConfigInfoDbo configInfoDbo9;
    List<ConfigInfoDbo> configInfoDboList;
    private String configInfoDetailList;

    @Before
    public void setup(){

        existingAppCode = "ACCTSVC";
        existingVersion = "v1";
        nonexistingAppCode = "M16";
        nonexistingVersion = "v10";
        acctsvcV1 = "{\"appCode\":\"ACCTSVC\", \"version\":\"v1\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";
        acctsvcV2 = "{\"appCode\":\"ACCTSVC\", \"version\":\"v2\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";
        acctsvcV4 = "{\"appCode\":\"ACCTSVC\", \"version\":\"v4\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";
        acctsvcV9 = "{\"appCode\":\"ACCTSVC\", \"version\":\"v9\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-serviceV9\"}}";
        configInfoDetailList = "{configInfoList: [{\"appCode\":\"ACCTSVC\", \"version\":\"v4\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}},{\"appCode\":\"ACCTSVC\", \"version\":\"v2\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}},{\"appCode\":\"ACCTSVC\", \"version\":\"v1\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}]}";
        existingConfigInfoPK = new ConfigInfoPK(existingAppCode,existingVersion);
        v9ConfigInfoPK = new ConfigInfoPK(existingAppCode,"v9");
        Date currentTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        calendar.add(Calendar.DATE, -3);
        Date ealierThanYesterday = calendar.getTime();

        List<ConfigInfoDbo> configInfoDboList = new LinkedList<>();
        configInfoDbo1 = new ConfigInfoDbo("ACCTSVC", "v1", acctsvcV1);
        configInfoDbo1.setLastUpdate(ealierThanYesterday);
        configInfoDbo2 = new ConfigInfoDbo("ACCTSVC", "v2", acctsvcV2);
        configInfoDbo2.setLastUpdate(yesterday);
        configInfoDbo3 = new ConfigInfoDbo("ACCTSVC", "v4", acctsvcV4);
        configInfoDbo3.setLastUpdate(currentTime);
        configInfoDboList.add(configInfoDbo2);
        configInfoDboList.add(configInfoDbo3);
        configInfoDboList.add(configInfoDbo1);
        configInfoDbo9 = new ConfigInfoDbo("ACCTSVC", "v9", acctsvcV9);
        configInfoDbo9.setLastUpdate(null);
        when(this.configInfoRepository.findAllByKeyAppCode(existingAppCode)).thenReturn(configInfoDboList);
        when(this.configInfoRepository.findById(existingConfigInfoPK)).thenReturn(Optional.of(configInfoDbo1));
        when(this.configInfoRepository.save(configInfoDbo1)).thenReturn(configInfoDbo1);
        when(this.configInfoRepository.save(configInfoDbo9)).thenReturn(configInfoDbo9);
        when(this.configInfoRepository.getOne(existingConfigInfoPK)).thenReturn(configInfoDbo1);
        when(this.configInfoRepository.getOne(v9ConfigInfoPK)).thenReturn(null);
        configService = new ConfigServiceImpl(configInfoRepository);
    }

    @Test
    public void findConfigAllByAppCodeRecordFound(){
        String configDetailList = configService.findConfigAllByAppCode(existingAppCode);
        Assert.assertEquals(configDetailList, configInfoDetailList);
    }

    @Test
    public void findConfigAllByAppCodeRecordNotFound(){
        String configDetailList = configService.findConfigAllByAppCode(nonexistingAppCode);
        Assert.assertEquals("{configInfoList: []}",configDetailList);
    }
    @Test
    public void findConfigRecordFound(){
        String rtnDetail = configService.findConfig(existingAppCode,existingVersion);
        Assert.assertEquals(acctsvcV1, rtnDetail);
    }
    @Test
    public void findConfigRecordNotFound(){
        String rtnDetail = configService.findConfig(existingAppCode,nonexistingVersion);
        Assert.assertEquals(Constants.EMPTY_JSON, rtnDetail);
    }

    @Test
    public void saveConfigUpdateExistingRecord(){
        String rtnDetail = configService.saveConfig(existingAppCode,existingVersion,acctsvcV1);
        Assert.assertEquals(acctsvcV1, rtnDetail);
    }

    @Test
    public void saveConfigAddRecord(){
        String rtnDetail = configService.saveConfig(existingAppCode,"v9",acctsvcV9);
        Assert.assertEquals(acctsvcV9, rtnDetail);
    }
}
