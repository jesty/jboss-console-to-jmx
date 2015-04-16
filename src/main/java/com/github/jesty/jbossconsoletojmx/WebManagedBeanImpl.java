package com.github.jesty.jbossconsoletojmx;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "com.ab.oneleo.status.monitor.mbean:type=Web", description = "Informations about http and https.")
public class WebManagedBeanImpl implements WebManagedBean {

	private static final String HTTPS = "https";

	private static final String HTTP = "http";

	private String path = "/management/subsystem/web/connector/{0}?include-runtime=true";
	
	private final JBossConsoleConnector jBossConsoleConnector;
	
	public WebManagedBeanImpl(JBossConsoleConnector jBossConsoleConnector){
		this.jBossConsoleConnector = jBossConsoleConnector;
	}

	@Override
	@ManagedOperation(description = "Informations about http and http.")
	public Map<String, Object> getHttp() {
		return getForProtocol(HTTP);
	}

	@Override
	@ManagedOperation(description = "Informations about http and https.")
	public Map<String, Object> getHttps() {
		return getForProtocol(HTTPS);
	}

	private Map<String, Object> getForProtocol(String protocol) {
		String url = getUrl(protocol);
		return this.jBossConsoleConnector.invokeAndGetMap(url);
	}

	private String getUrl(String datasource) {
		return MessageFormat.format(path, datasource);
	}
}
