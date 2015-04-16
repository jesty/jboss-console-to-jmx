package com.github.jesty.jbossconsoletojmx;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "com.ab.oneleo.status.monitor.mbean:type=ConnectionPool", description = "Informations about database connection pool")
public class ConnectionPoolManagedBeanImpl implements ConnectionPoolManagedBean {

	private String path = "/management/subsystem/datasources/data-source/{0}/statistics/pool?include-runtime=true";
	
	private final JBossConsoleConnector jBossConsoleConnector;
	
	public ConnectionPoolManagedBeanImpl(JBossConsoleConnector jBossConsoleConnector){
		this.jBossConsoleConnector = jBossConsoleConnector;
	}

	@Override
	@ManagedOperation(description = "Informations about database connection pool")
	public Map<String, Object> getDatasource(String datasource) {
		String url = getUrl(datasource);
		return this.jBossConsoleConnector.invokeAndGetMap(url);
	}

	private String getUrl(String datasource) {
		return MessageFormat.format(path, datasource);
	}



}
