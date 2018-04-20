package com.example.config.domain.service.impl;

import com.example.config.common.Constants;
import com.example.config.domain.repository.ConfigInfoRepository;
import com.example.config.domain.repository.entity.ConfigInfoDbo;
import com.example.config.domain.repository.entity.ConfigInfoPK;
import com.example.config.domain.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


/*
 * Service class to encapsulate the data access layer
 *
 * as the json document stored in one field within the table
 * it is much easier to return all the retrieved Object as String
 * to avoid too much post processing.
 */
@Service
public class ConfigServiceImpl implements ConfigService{

    private ConfigInfoRepository configInfoRepository;

    public ConfigServiceImpl(@Autowired ConfigInfoRepository configInfoRepository){
        super();
        this.configInfoRepository = configInfoRepository;
    }

    /**
     * retrieve a string that contains the list of Config detail by appCode in JSON format
     * @param appCode
     * @return
     */
    @Override
    public String findConfigAllByAppCode(String appCode) {
        List<ConfigInfoDbo> configIngoDboList = configInfoRepository.findAllByKeyAppCode(appCode);
        StringBuilder configDetailList = new StringBuilder("{configInfoList: [");
        int initialLength = configDetailList.length();
        configIngoDboList.stream().sorted().forEach(configInfo -> configDetailList.append(configInfo.getConfigDetail()).append(","));
        int len = configDetailList.length();
        if(len > initialLength) {
            configDetailList.deleteCharAt(len - 1);
        }
        configDetailList.append("]}");
        return configDetailList.toString();
    }

    /**
     * find the Config detail by appCode, version
     * @param appCode
     * @param version
     * @return
     */
    @Override
    public String findConfig(String appCode, String version) {
        String configDetail = Constants.EMPTY_JSON;
        ConfigInfoPK key = new ConfigInfoPK(appCode, version);
        Optional<ConfigInfoDbo> configInfoDboOpt =  configInfoRepository.findById(key);
        if(configInfoDboOpt.isPresent()){
            ConfigInfoDbo configInfoDbo = configInfoDboOpt.get();
            configDetail = configInfoDbo.getConfigDetail();
        }

        return configDetail;
    }

    /**
     * update existing configInfoDbo in repository, or add a new one if not found in existing data.
     * @param configInfo
     * @return
     */
    @Override
    public String saveConfig(String appCode, String version, String configDetail) {

        String updatedConfigDetail=Constants.EMPTY_JSON;
        ConfigInfoDbo configInfoDbo = new ConfigInfoDbo(appCode, version, configDetail);
        ConfigInfoPK configInfoPK = configInfoDbo.getKey();
        ConfigInfoDbo updatedConfigInfoDbo = null;

        try {
            /*
            getOne(ID id) gets you only a reference (proxy) object
            and does not fetch it from the DB.
            On this reference you can set what you want and on save()
            it will do just an SQL UPDATE statement
             */
            ConfigInfoDbo existingConfigInfoDbo = configInfoRepository.getOne(configInfoPK);
            if(existingConfigInfoDbo != null){
                existingConfigInfoDbo.setConfigDetail(configInfoDbo.getConfigDetail());
                updatedConfigInfoDbo = configInfoRepository.save(existingConfigInfoDbo);
            }else{
                updatedConfigInfoDbo = configInfoRepository.save(configInfoDbo);
            }
        }catch (EntityNotFoundException e){
            updatedConfigInfoDbo = configInfoRepository.save(configInfoDbo);
        }
        if(updatedConfigInfoDbo!=null){
            updatedConfigDetail = updatedConfigInfoDbo.getConfigDetail();
        }
        return updatedConfigDetail;
    }
}