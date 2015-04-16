package com.github.jesty.jbossconsoletojmx.mbeans;

import java.util.Map;

public interface ConnectionPoolManagedBean {

	Map<String, Object> getDatasource(String datasource);

}
