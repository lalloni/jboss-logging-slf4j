JBoss: Reemplazo de Log4J por LogBack
=====================================

Supuestos
~~~~~~~~~

* Se utiliza la distribución de LogBack versión 0.9.26
* Se utiliza la distribución de SLF4J versión 1.6.1
* Se tiene instalado JBoss versión 5.1.0 GA

Convenciones
~~~~~~~~~~~~

* La ubicación de la instalación de JBoss se referenciará como JBOSS_HOME
* La ubicación de la instancia de JBoss usada se referenciará como SERVER_HOME 
  (por ejemplo SERVER_HOME=$JBOSS_HOME/server/default)
* La ubicación del proyecto jboss-logging-slf4j se referenciará como PROJECT 

Instalación
~~~~~~~~~~~

1. De la distribución de LogBack copiar a $JBOSS_HOME/lib los siguientes 
   archivos:

 - logback-core-0.9.26.jar 
 - logback-classic-0.9.26.jar

2. De la distribución de SLF4J 1.6.1 copiar a $JBOSS_HOME/lib los siguentes 
   archivos:

 - slf4j-api-1.6.1.jar
 - log4j-over-slf4j-1.6.1.jar
 - jcl-over-slf4j-1.6.1.jar
 - jul-to-slf4j-1.6.1.jar

3. Descargar jboss-logging-slf4j-1.jar_

4. Copiar el archivo descargado en el paso anterior a $JBOSS_HOME/lib.
  
Configuración
~~~~~~~~~~~~~

#. Reemplazar bridge JUL-Log4J por bridge JUL-SLF4J en 
   $SERVER_HOME/conf/bootstrap/logging.xml siguiendo estos pasos:

   #. Reemplazar la línea::

        <bean name="LogBridgeHandler" class="org.jboss.logbridge.LogBridgeHandler"/>
    
   #. Por::

        <bean name="LogBridgeHandler" class="org.slf4j.bridge.SLF4JBridgeHandler"/>
        
#. Eliminar o comentar en la sección "Log4j Initialization" del archivo 
   $SERVER_HOME/conf/jboss-service.xml el MBean cuyo name es 
   "jboss.system:type=Log4jService,service=Logging"

#. Configurar LogBack

   Configurar LogBack en archivo $SERVER_HOME/conf/logback.xml.
 
   A modo de ejemplo, se transcribe una configuración de LogBack que simula la 
   configuración de Log4J que trae JBoss mas algunos detalles posibles en 
   LogBack para mostrar excepciones::
   
        <?xml version="1.0" encoding="UTF-8"?>
        <configuration debug="false" scan="true" scanPeriod="30 seconds">
           <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
              <file>${jboss.server.log.dir}/server.log</file>
              <append>true</append>
              <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                 <fileNamePattern>${jboss.server.log.dir}/server.%d.log</fileNamePattern>
                 <maxHistory>30</maxHistory>
              </rollingPolicy>
              <encoder>
                <pattern>%contextName %date %-5level \(%thread\) [%logger{20}] %message %mdc %xThrowable{full} %n</pattern>
              </encoder>
           </appender>
           <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
              <encoder>
                <pattern>%contextName %date %-5level \(%thread\) [%logger{20}] %message %mdc %xThrowable{full} %n</pattern>
              </encoder>
           </appender>
           <logger name="org.apache" level="INFO"/>
           <logger name="jacorb" level="WARN"/>
           <logger name="javax.enterprise.resource.webcontainer.jsf" level="INFO"/>
           <logger name="org.jgroups" level="WARN"/>
           <logger name="org.quartz" level="INFO"/>
           <logger name="com.sun" level="INFO"/>
           <logger name="sun" level="INFO"/>
           <logger name="javax.xml.bind" level="INFO"/>
           <logger name="org.jboss.management" level="INFO"/>
           <logger name="facelets.compiler" level="WARN"/>
           <logger name="org.ajax4jsf.cache" level="WARN"/>
           <logger name="org.rhq" level="WARN"/>
           <logger name="org.jboss.seam" level="WARN"/>
           <logger name="org.jboss.serial" level="INFO"/>
           <root level="INFO">
              <appender-ref ref="CONSOLE"/>
              <appender-ref ref="FILE"/>
           </root>
        </configuration>
 
Ejecución
~~~~~~~~~

En un shell::

    $ cd $JBOSS_HOME
    $ bin/run.sh -b 0.0.0.0 \                     
      -L logback-core-0.9.26.jar \
      -L logback-classic-0.9.26.jar \
      -L jboss-logging-slf4j-1-SNAPSHOT.jar \
      -L slf4j-api-1.6.1.jar \
      -L log4j-over-slf4j-1.6.1.jar \
      -L jcl-over-slf4j-1.6.1.jar \
      -L jul-to-slf4j-1.6.1.jar \
      -Dorg.jboss.logging.Logger.pluginClass=org.lalloni.jboss.logging.slf4j.SLF4JLoggerPlugin \
      -Dlogback.configurationFile="$SERVER_HOME/conf/logback.xml"

.. _jboss-logging-slf4j-1.jar: https://github.com/downloads/plalloni/jboss-logging-slf4j/jboss-logging-slf4j-1.jar
