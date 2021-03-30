package com.cls.toyDApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
//@ConfigurationProperties("fabric")
@ConfigurationProperties
@Configuration
public class FabricConfig {

	private String caOrgCert;
	private String caURL;
	private String caId;
	private String caPw;
	private String mspId;
	private String channelName;
	private String chaincodeName;
}
