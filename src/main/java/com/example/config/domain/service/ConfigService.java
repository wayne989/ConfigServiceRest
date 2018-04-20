package com.example.config.domain.service;

/*
 * Service interface to decouple from its service implementation
 * as the json document stored in one field within the table
 * it is much easier to return all the retrieved Object as String
 * to avoid too much post processing.
 */
public interface ConfigService{
    String findConfigAllByAppCode(String appCode);
    String findConfig(String appCode, String version);
    String saveConfig(String appCode, String version, String configDetail);
}
