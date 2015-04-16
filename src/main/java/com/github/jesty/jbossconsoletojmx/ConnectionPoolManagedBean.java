package com.github.jesty.jbossconsoletojmx;

import java.util.Map;

public interface ConnectionPoolManagedBean {

	Map<String, Object> getDatasource(String datasource);

}
