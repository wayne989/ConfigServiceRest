package com.example.config.api;

import com.example.config.common.Constants;
import com.example.config.domain.service.ConfigService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Wayne on 2018-04-19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class ConfigControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @MockBean(reset= MockReset.BEFORE)
    private ConfigService configService;

    private MockMvc mockMvc;

    private String existingAppCode;
    private String existingVersion;
    private String acctsvc_v1_return;
    private String acctsvc_return;
    private String nonexistingAppCode;
    private String nonexistingVersion;

    @Before
    public void setup(){
        existingAppCode = "ACCTSVC";
        existingVersion = "v1";
        nonexistingAppCode = "M16";
        nonexistingVersion = "v10";
        acctsvc_v1_return = "{\"appCode\":\"ACCTSVC\", \"version\":\"v1\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";
        acctsvc_return = "{configInfoList: [{\"appCode\":\"ACCTSVC\", \"version\":\"v4\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}},{\"appCode\":\"ACCTSVC\", \"version\":\"v2\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}},{\"appCode\":\"ACCTSVC\", \"version\":\"v1\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}]}";
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void getConfigTestReturnFoundRecord() throws Exception{
        when(this.configService.findConfig(existingAppCode, existingVersion)).thenReturn(acctsvc_v1_return);
        mockMvc.perform(get("/api/{appCode}/config/{version}", existingAppCode, existingVersion)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getConfigTestRecordNotFound() throws Exception{
        when(this.configService.findConfig(nonexistingAppCode, nonexistingVersion)).thenReturn(Constants.EMPTY_JSON);
        mockMvc.perform(get("/api/{appCode}/config/{version}", nonexistingAppCode, nonexistingVersion)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void listConfigTestReturnFoundRecords() throws Exception{
        when(this.configService.findConfigAllByAppCode(existingAppCode)).thenReturn(acctsvc_return);
        mockMvc.perform(get("/api/{appCode}/config", existingAppCode)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void listConfigTestRecordNotFound() throws Exception{
        when(this.configService.findConfigAllByAppCode(nonexistingAppCode)).thenReturn(Constants.EMPTY_JSON);
        mockMvc.perform(get("/api/{appCode}/config", nonexistingAppCode)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addOrUpdateConfigTestAddRecordSuccessfully() throws Exception{
        String newConfigDetail = "{\"appCode\":\"ACCTSVC\", \"version\":\"v4\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"AccountMaintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";
        when(this.configService.saveConfig(existingAppCode,nonexistingVersion,newConfigDetail)).thenReturn(newConfigDetail);
        mockMvc.perform(post("/api/{appCode}/config/{version}", existingAppCode,nonexistingVersion).contentType(MediaType.APPLICATION_JSON).content(newConfigDetail)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addOrUpdateConfigTestUpdateRecordSuccessfully() throws Exception{
        String updConfigDetail = "{\"appCode\":\"ACCTSVC\", \"version\":\"v1\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"Account-Maintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";
        when(this.configService.saveConfig(existingAppCode,existingVersion,updConfigDetail)).thenReturn(updConfigDetail);
        mockMvc.perform(post("/api/{appCode}/config/{version}", existingAppCode,existingVersion).contentType(MediaType.APPLICATION_JSON).content(updConfigDetail)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addOrUpdateConfigTestInvalidRequest() throws Exception{
        String badConfigDetail = "{\"appCode\":\"ACCTSVC, \"version\":\"v1\", \"name\": \"account-service\", \"configDetail\":{\"deptCode\": \"Account-Maintenance\", \"channel\":\"mobile\", \"endpont\":\"http://bank/account-service\"}}";
        when(this.configService.saveConfig(existingAppCode,existingVersion,badConfigDetail)).thenReturn(badConfigDetail);
        mockMvc.perform(post("/api/{appCode}/config/{version}", existingAppCode,existingVersion).contentType(MediaType.APPLICATION_JSON).content(badConfigDetail)).andDo(print()).andExpect(status().isBadRequest());
    }
}
