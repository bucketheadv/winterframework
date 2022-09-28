package org.winterframework.jwt.env;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qinglinl
 * Created on 2022/9/27 2:02 PM
 */
@Data
public class Environment implements Serializable {
	private static final long serialVersionUID = -3793693911012753958L;

	private String platform;

	private String platformVersion;

	private String platformModel;

	private String platformBrand;

	private String bundle;

	private String clientVersion;

	private String deviceId;

	private String networkType;

	private String ip;

	private String utmSource;

	private transient String token;

	private String imei;

	private String carrier;

	private String lan;

	private String timezone;

	private Long uid;

	private String oaid;

	private String agent;

	private String appName;

	private String country;
}
