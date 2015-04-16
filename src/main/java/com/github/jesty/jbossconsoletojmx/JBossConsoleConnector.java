package com.github.jesty.jbossconsoletojmx;

import java.util.Map;

public interface JBossConsoleConnector {

	String invokeAndGetString(String path);

	Map<String, Object> invokeAndGetMap(String path);

}
