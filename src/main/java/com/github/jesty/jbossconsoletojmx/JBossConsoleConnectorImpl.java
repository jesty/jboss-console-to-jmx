package com.github.jesty.jbossconsoletojmx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Required;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JBossConsoleConnectorImpl implements JBossConsoleConnector {
	
	private String protocol;
	private String host;
	private int portNumber;
	private String username;
	private String password;
	
	public JBossConsoleConnectorImpl(){
	}
	
	public JBossConsoleConnectorImpl(String protocol, String host, int portNumber, String username, String password) {
		this.protocol = protocol;
		this.host = host;
		this.portNumber = portNumber;
		this.username = username;
		this.password = password;
	}

	@Override
	public String invokeAndGetString(String path) {
		String url = buildUrl(path);
		HttpResponse response = null;
		HttpHost targetHost = new HttpHost(host, portNumber, protocol);

		DefaultHttpClient httpclient = new DefaultHttpClient();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			AuthScope authscope = new AuthScope(targetHost.getHostName(),  targetHost.getPort());
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
			httpclient.getCredentialsProvider().setCredentials(authscope, credentials);

			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(targetHost, httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200){
				response.getEntity().writeTo(outputStream);
			} else {
				throw new RuntimeException("Cannot contact JBoss Cosole at " + url + ". Caused by HTTP error code nr. " + statusCode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		return outputStream.toString();
	}

	@Override
	public Map<String, Object> invokeAndGetMap(String path) {
		String result = invokeAndGetString(path);
		return jsonToMap(result);
	}
	
	@Required
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Required
	public void setHost(String host) {
		this.host = host;
	}
	
	@Required
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	@Required
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Required
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String buildUrl(String path) {
		return protocol + "://" + host + ":" + portNumber + path;
	}


	@SuppressWarnings("unchecked")
	private Map<String, Object> jsonToMap(String string) {
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(string, Map.class);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}


}
