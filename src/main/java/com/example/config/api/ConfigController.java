package com.example.config.api;

import com.example.config.common.Utils;
import com.example.config.domain.service.ConfigService;
import com.example.config.common.exception.InvalidJSONFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ConfigController{
    @Autowired
    private ConfigService configService;

    @GetMapping(value = "/{appCode}/config/{version}", produces={"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Object getConfig(@PathVariable("appCode") String appCode, @PathVariable("version") String version){
        String configDetail=configService.findConfig(appCode, version);
        return configDetail;
    }

    @GetMapping(value = "/{appCode}/config", produces={"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Object listConfig(@PathVariable("appCode") String appCode){
        String configInfoList = configService.findConfigAllByAppCode(appCode);
        return configInfoList;
    }

    @PostMapping(value = "/{appCode}/config/{version}", produces={"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Object addOrUpdateConfig(@PathVariable("appCode") String appCode, @PathVariable("version") String version, @RequestBody String configDetail){
        if(!Utils.isValidJSON(configDetail)){
            throw new InvalidJSONFormatException(configDetail);
        }
        String configInfoList = configService.saveConfig(appCode, version, configDetail);
        return configInfoList;
    }
}