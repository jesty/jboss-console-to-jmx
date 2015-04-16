package com.github.jesty.jbossconsoletojmx;

import java.util.Map;

/**
 * This bean exposes Web Metrics using JMX
 */
public interface WebManagedBean {
	
	Map<String, Object> getHttp();
	
	Map<String, Object> getHttps();
	
}
