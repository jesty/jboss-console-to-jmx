# jboss-console-to-jmx
Starting from JBOSS AS 7 some information are not available on JMX console, but only using JBoss Console (cli, api, web, etc...). Using this project you can see some information about connection pool, web statistics and more using JMX.

How-to:

  <bean id="connectionPoolMonitor" class="com.ab.oneleo.status.monitor.mbean.ConnectionPoolManagedBeanImpl">
		<constructor-arg ref="jbossConsoleConnector" />
	</bean> 
	
	<bean id="webMonitor" class="com.ab.oneleo.status.monitor.mbean.WebManagedBeanImpl">
		<constructor-arg ref="jbossConsoleConnector" />
	</bean> 
	
	<bean id="jbossConsoleConnector" class="com.ab.oneleo.status.monitor.util.JBossConsoleConnectorImpl">
		<property name="protocol" value="http"></property>
		<property name="host" value="localhost"></property>
		<property name="portNumber" value="9999"></property>
		<property name="username" value="admin"></property>
		<property name="password" value="admin.01"></property>
	</bean> 
