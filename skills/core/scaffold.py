import os

APP_NAME = "app-welcome"
APP_PACKAGE = "welcome"
APP_PREFIX = "welcome"
BASE_DIR = r"c:\desenv\tedros\github\tedros-apps\app-welcome"

files = {
    "pom.xml": """<?xml version="1.0" encoding="UTF-8"?><project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.tedros</groupId>
  <artifactId>{APP_NAME}</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>pom</packaging>
  <name>App Tedros Box parent project</name>
  <description>Build a tedros application with all required projects</description>
  <organization>
    <name>organizationname</name>
  </organization>
  <!-- PROPERTIES -->
  <properties>
    <app.ext>17-1.0.0</app.ext>
    <app.person>17-1.0.0</app.person>
    <app.services>17-1.0.0</app.services>
    <app.stock>17-1.0.0</app.stock>
    <tedros.fx>17-0.0.1</tedros.fx>
    <app.settings>17-0.0.1</app.settings>
    <tedros.jee>17-0.0.1</tedros.jee>
    <javafx.version>17.0.11</javafx.version>
    <java.version>17</java.version>
    <maven.compiler.target>17</maven.compiler.target>
    <maven.compiler.source>17</maven.compiler.source>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <java.home>D:\\java\\jdk\\jdk-17.0.10\\bin</java.home>
    <commons-collections.version>4.4</commons-collections.version>
    <commons.logging.version>1.3.3</commons.logging.version>
    <commons.io.version>2.16.1</commons.io.version>
    <commons.lang.version>3.14.0</commons.lang.version>
    <commons.text.version>1.12.0</commons.text.version>
    <!-- Define the version of OPEN HTML TO PDF in the properties section of your POM. -->
    <openhtml.version>1.0.10</openhtml.version>
    <junit.version>4.13</junit.version>
    <cargo.version>1.10.14</cargo.version>
    <h2.version>1.4.199</h2.version>
    <eclipselink.version>4.0.3</eclipselink.version>
    <jackson.jaxrs.json.provider>2.11.2</jackson.jaxrs.json.provider>
    <javaee.api.version>9.1.1</javaee.api.version>
    <openejb.version>9.1.3</openejb.version>
    <tomee.version>9.1.3</tomee.version>
    <tomee.ejb.url>http://127.0.0.1:8080/tomee/ejb</tomee.ejb.url>
    <ejb.version>3.2</ejb.version>
    <compiler.plugin>3.13.0</compiler.plugin>
    <source.plugin>3.0.0</source.plugin>
    <ejb.plugin>3.2.1</ejb.plugin>
    <resource.plugin>3.3.1</resource.plugin>
    <ear.plugin>3.3.0</ear.plugin>
    <!-- Define the folder path for Docker deployment -->
    <docker.app.folder>D:\GitHub\Tedros-Box\Tedros\init\docker\deployment_app</docker.app.folder>
  </properties>
  <!-- DEVELOPERS -->
  <developers>
    <developer>
      <name>myname</name>
      <email>myemail</email>
      <organization>organizationname</organization>
    </developer>
  </developers>
  <!-- BUILD -->
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>${compiler.plugin}</version>
        <configuration>
          <source>${java.version}</source>
          <target>${java.version}</target>
        </configuration>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-source-plugin</artifactId>
        <version>${source.plugin}</version>
        <executions>
          <execution>
            <id>attach-sources</id>
            <phase>verify</phase>
            <goals>
              <goal>jar-no-fork</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-archetype-plugin</artifactId>
        <version>3.0.0</version>
      </plugin>
    </plugins>
  </build>
  <!-- REPOSITORIES -->
  <repositories>
    <repository>
      <id>oss.sonatype.org</id>
      <name>OSS Sonatype Staging</name>
      <url>https://oss.sonatype.org/content/groups/staging</url>
    </repository>
    <repository>
      <id>apache.releases.https</id>
      <name>Apache</name>
      <url>https://repository.apache.org/service/local/staging/deploy/maven2</url>
    </repository>
    <repository>
      <id>Tedros-Box</id>
      <name>Tedros</name>
      <url>https://maven.pkg.github.com/tedros-box/package</url>
    </repository>
  </repositories>
  <!-- MODULES -->
  <modules>
    <module>{APP_NAME}-ejb</module>
    <module>{APP_NAME}-ejb-client</module>
    <module>{APP_NAME}-ejb-ear</module>
    <module>{APP_NAME}-model</module>
    <module>{APP_NAME}-fx</module>
  </modules>
</project>""",
    "{APP_NAME}-ejb/pom.xml": """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<!-- PARENT INFO -->
	<parent>
		<groupId>org.tedros</groupId>
		<artifactId>{APP_NAME}</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<!-- MODULE INFO -->
	<artifactId>{APP_NAME}-ejb</artifactId>
	<packaging>ejb</packaging>
	<name>{APP_NAME}-ejb</name>
	<description>The app ejb service</description>
	<profiles>
		<profile>
			<id>prod</id>
		</profile>
		<profile>
			<id>des</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
		</profile>
	</profiles>
	<!-- DEPENDENCIES -->
	<dependencies>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>{APP_NAME}-ejb-client</artifactId>
			<version>1.0-SNAPSHOT</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>tedros-server-api</artifactId>
			<version>${tedros.jee}</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>tedros-core-ejb-client</artifactId>
			<version>${tedros.jee}</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.eclipse.persistence</groupId>
			<artifactId>eclipselink</artifactId>
			<version>${eclipselink.version}</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
			<version>${commons.lang.version}</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.tomee</groupId>
			<artifactId>jakartaee-api</artifactId>
			<version>${javaee.api.version}</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.tomee</groupId>
			<artifactId>openejb-core</artifactId>
			<version>${openejb.version}</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>${junit.version}</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<!-- BUILD -->
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-ejb-plugin</artifactId>
				<version>${ejb.plugin}</version>
				<configuration>
					<ejbVersion>${ejb.version}</ejbVersion>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>${compiler.plugin}</version>
				<configuration>
					<source>${java.version}</source>
					<target>${java.version}</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-source-plugin</artifactId>
				<version>${source.plugin}</version>
				<executions>
					<execution>
						<id>attach-sources</id>
						<phase>verify</phase>
						<goals>
							<goal>jar-no-fork</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<filtering>true</filtering>
				<includes>
					<include>**/*.*</include>
				</includes>
			</resource>
		</resources>
	</build>
</project>""",
    "{APP_NAME}-ejb/src/main/resources/META-INF/persistence.xml": """<persistence 	xmlns="http://java.sun.com/xml/ns/persistence" 
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
				xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd" 
				version="2.0">
	<persistence-unit name="tedros_core_pu">
		<provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
		<jta-data-source>tedrosDataSource</jta-data-source>
		
		<!-- Core entities required by Tedros architecture -->
		<class>org.tedros.core.notify.model.TNotify</class>
		<class>org.tedros.core.notify.model.TNotifyLog</class>
		<class>org.tedros.common.model.TFileEntity</class>
		<class>org.tedros.common.model.TBarcode</class>
		<class>org.tedros.common.model.TByteEntity</class>
		
		<class>org.tedros.extension.model.Contact</class>
		<class>org.tedros.extension.model.Document</class>
		<class>org.tedros.extension.model.DocumentEvent</class>
		<class>org.tedros.extension.model.DocumentStatus</class>
		<class>org.tedros.extension.model.DocumentType</class>
		<class>org.tedros.extension.model.ExtensionDomain</class>
		
		<class>org.tedros.extension.model.Country</class>
		<class>org.tedros.extension.model.City</class>
		<class>org.tedros.extension.model.AdminArea</class>
		<class>org.tedros.extension.model.Place</class>
		<class>org.tedros.extension.model.Address</class>
		<class>org.tedros.extension.model.StreetType</class>
		<class>org.tedros.extension.model.PlaceType</class>
		
		<class>org.tedros.person.model.Person</class>
		<class>org.tedros.person.model.NaturalPerson</class>
		<class>org.tedros.person.model.LegalPerson</class>
		<class>org.tedros.person.model.CostCenter</class>
		<class>org.tedros.person.model.Employee</class>
		<class>org.tedros.person.model.Customer</class>
		<class>org.tedros.person.model.LegalType</class>
		<class>org.tedros.person.model.PersonAttributes</class>
		<class>org.tedros.person.model.PersonEvent</class>
		<class>org.tedros.person.model.ClientCompany</class>
		<class>org.tedros.person.model.ClientCompanyStatus</class>
		<class>org.tedros.person.model.ClientCompanyType</class>
		<class>org.tedros.person.model.CustomerStatus</class>
		<class>org.tedros.person.model.CustomerType</class>
		<class>org.tedros.person.model.EmployeeStatus</class>
		<class>org.tedros.person.model.LegalStatus</class>
		<class>org.tedros.person.model.Member</class>
		<class>org.tedros.person.model.MemberStatus</class>
		<class>org.tedros.person.model.MemberType</class>
		<class>org.tedros.person.model.PersonCategory</class>
		<class>org.tedros.person.model.PersonStatus</class>
		<class>org.tedros.person.model.PersonType</class>
		<class>org.tedros.person.model.Philanthrope</class>
		<class>org.tedros.person.model.PhilanthropeStatus</class>
		<class>org.tedros.person.model.PhilanthropeType</class>
		<class>org.tedros.person.model.StaffType</class>
		<class>org.tedros.person.model.Voluntary</class>
		<class>org.tedros.person.model.VoluntaryStatus</class>
		<class>org.tedros.person.model.VoluntaryType</class>
		<class>org.tedros.person.model.NaturalType</class>
		<class>org.tedros.person.model.NaturalStatus</class> 
		
		<class>org.tedros.it.tools.entity.JobEvidence</class>
		<class>org.tedros.it.tools.entity.JobEvidenceItem</class>
		<class>org.tedros.it.tools.entity.Gmud</class>
		<class>org.tedros.it.tools.entity.GmudItem</class>
		<class>org.tedros.it.tools.entity.GmudReview</class>
		<class>org.tedros.it.tools.entity.GmudIssueReference</class>
		
	    <properties>

		    <property name="jakarta.persistence.retrieveMode" value="BYPASS"/>
		    <property name="jakarta.persistence.storeMode" value="BYPASS"/>
		    <property name="eclipselink.target-database" value="org.eclipse.persistence.platform.database.H2Platform"/>
		    
		    <!-- drop-and-create-tables || create-or-extend-tables -->
		    <property name="eclipselink.ddl-generation" value="create-tables"/>
		    <property name="eclipselink.ddl-generation.output-mode" value="database"/>
		
		    <!-- ================== LOGGING ================== -->
		    <!-- Só erros graves + exceções -->
		    <property name="eclipselink.logging.level" value="SEVERE"/>
		    
		    <!-- Mostra as queries SQL executadas (com parâmetros) -->
		    <property name="eclipselink.logging.level.sql" value="FINE"/>
		    
		    <!-- Garante que exceções sempre apareçam -->
		    <property name="eclipselink.logging.exceptions" value="true"/>
		    
		    <!-- Mostra os valores dos parâmetros nas queries -->
		    <property name="eclipselink.logging.parameters" value="true"/>
		    
		    <!-- Formatação do log (opcional, mas útil) -->
		    <property name="eclipselink.logging.timestamp" value="true"/>
		    <property name="eclipselink.logging.thread" value="false"/>
		    <property name="eclipselink.logging.session" value="false"/>
		
		</properties>
	</persistence-unit> 
</persistence>""",
    "{APP_NAME}-ejb/src/main/resources/META-INF/ejb-jar.xml": """<?xml version="1.0" encoding="UTF-8"?>
<ejb-jar xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_1.xsd" version="3.1">
 
</ejb-jar>""",
    "{APP_NAME}-ejb/src/main/resources/META-INF/beans.xml": """<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="https://jakarta.ee/xml/ns/jakartaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/beans_4_0.xsd"
       version="4.0"
       bean-discovery-mode="all">
	<interceptors>
    	<class>org.tedros.server.interceptor.TSecurityInterceptor</class>
  	</interceptors>
</beans>""",
    "{APP_NAME}-ejb-client/pom.xml": """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<!-- PARENT INFO -->
	<parent>
		<groupId>org.tedros</groupId>
		<artifactId>{APP_NAME}</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<!-- MODULE INFO -->
	<artifactId>{APP_NAME}-ejb-client</artifactId>
	<packaging>jar</packaging>
	<name>{APP_NAME}-client</name>
	<description>The app ejb client</description>
	<!-- DEPENDENCIES -->
	<dependencies>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>{APP_NAME}-model</artifactId>
			<version>1.0-SNAPSHOT</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.tomee</groupId>
			<artifactId>openejb-client</artifactId>
			<version>${openejb.version}</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>${junit.version}</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>${compiler.plugin}</version>
				<configuration>
					<source>${java.version}</source>
					<target>${java.version}</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-source-plugin</artifactId>
				<version>${source.plugin}</version>
				<executions>
					<execution>
						<id>attach-sources</id>
						<phase>verify</phase>
						<goals>
							<goal>jar-no-fork</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<filtering>true</filtering>
				<includes>
					<include>**/*.*</include>
				</includes>
			</resource>
		</resources>
	</build>
</project>""",
    "{APP_NAME}-ejb-client/src/main/resource/jboss-ejb-client.properties": """endpoint.name=client-endpoint
remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED=false
remote.connections=default
remote.connection.default.host=127.0.0.1
remote.connection.default.port=8080
remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS=false
remote.connection.default.username=tdrs
remote.connection.default.password=s221978""",
    "{APP_NAME}-ejb-ear/pom.xml": """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<!-- PARENT INFO -->
	<parent>
		<groupId>org.tedros</groupId>
		<artifactId>{APP_NAME}</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<!-- MODULE INFO -->
	<artifactId>{APP_NAME}-ejb-ear</artifactId>
	<packaging>ear</packaging>
	<name>{APP_NAME}-ejb-ear</name>
	<url>The ear project to deploy</url>
	<!-- DEPENDENCIES -->
	<dependencies>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>{APP_NAME}-ejb</artifactId>
			<version>1.0-SNAPSHOT</version>
			<scope>compile</scope>
			<type>ejb</type>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<version>${h2.version}</version>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>${junit.version}</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<!-- BUILD -->
	<build>
		<finalName>${project.artifactId}</finalName>	
	    <plugins>
	        <plugin>
	            <groupId>org.apache.maven.plugins</groupId>
	            <artifactId>maven-resources-plugin</artifactId>
	            <version>3.3.1</version>
	            <executions>
	                <execution>
	                    <id>copy-ear-to-docker</id>
	                    <phase>package</phase>
	                    <goals>
	                        <goal>copy-resources</goal>
	                    </goals>
	                    <configuration>
	                        <outputDirectory>${docker.app.folder}</outputDirectory>
	                        <resources>
	                            <resource>
	                                <directory>${project.build.directory}</directory>
	                                <includes>
	                                    <include>${project.artifactId}.ear</include>
	                                </includes>
	                            </resource>
	                        </resources>
	                    </configuration>
	                </execution>
	            </executions>
	        </plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-ear-plugin</artifactId>
				<version>${ear.plugin}</version>
				<configuration>
					<defaultLibBundleDir>lib</defaultLibBundleDir>
					<modules>
						<ejbModule>
							<groupId>org.tedros</groupId>
							<artifactId>{APP_NAME}-ejb</artifactId>
						</ejbModule>
					</modules>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>${compiler.plugin}</version>
				<configuration>
					<source>${java.version}</source>
					<target>${java.version}</target>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>""",
    "{APP_NAME}-fx/pom.xml": """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.tedros</groupId>
		<artifactId>{APP_NAME}</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<artifactId>{APP_NAME}-fx</artifactId>
	<name>{APP_NAME}-fx</name>
	<description>JavaFx App - Front end</description>
	<dependencies>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>app-services-fx</artifactId>
			<version>${app.services}</version>
		</dependency>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>app-stock-fx</artifactId>
			<version>${app.stock}</version>
		</dependency>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>tedros-fx-api</artifactId>
			<version>${tedros.fx}</version>
		</dependency>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>{APP_NAME}-ejb-client</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>
        <!-- Feign Client dependencies explicitly imported based on reference complexity -->
		<dependency>
	        <groupId>io.github.openfeign</groupId>
	        <artifactId>feign-gson</artifactId>
	        <version>13.6</version>
	    </dependency>
		<dependency>
		    <groupId>io.github.openfeign</groupId>
		    <artifactId>feign-core</artifactId>
		    <version>13.6</version>
		</dependency>
		<dependency>
		    <groupId>io.github.openfeign</groupId>
		    <artifactId>feign-okhttp</artifactId>
		    <version>13.6</version>
		</dependency>
		<dependency>
		    <groupId>com.squareup.okhttp3</groupId>
		    <artifactId>okhttp</artifactId>
		    <version>4.12.0</version>
		</dependency>
		<dependency>
	        <groupId>net.java.dev.jna</groupId>
	        <artifactId>jna</artifactId>
	        <version>5.14.0</version> <!-- Versão estável para Java 17 -->
	    </dependency>
	    <dependency>
	        <groupId>net.java.dev.jna</groupId>
	        <artifactId>jna-platform</artifactId>
	        <version>5.14.0</version>
	    </dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.42</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.openjfx</groupId>
			<artifactId>javafx-web</artifactId>
			<version>17.0.2</version>
		</dependency>
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter</artifactId>
			<version>5.10.0</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<!-- PROPERTIES -->
	<properties>
		<java.version>17</java.version>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>
	<!-- BUILD -->
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>${compiler.plugin}</version>
				<configuration>
					<source>${java.version}</source>
					<target>${java.version}</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-source-plugin</artifactId>
				<version>${source.plugin}</version>
				<executions>
					<execution>
						<id>attach-sources</id>
						<phase>verify</phase>
						<goals>
							<goal>jar-no-fork</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-resources-plugin</artifactId>
				<version>${resource.plugin}</version>
				<configuration>
					<encoding>UTF-8</encoding>
				</configuration>
			</plugin>
		</plugins>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
			</resource>
			<resource>
				<directory>src/main/java</directory>
				<excludes>
					<exclude>**/*.java</exclude>
				</excludes>
			</resource>
		</resources>
	</build>
</project>""",
    "{APP_NAME}-fx/src/main/resources/{APP_NAME}_en.properties": """app.{APP_PREFIX}={APP_NAME} Application
menu.{APP_PREFIX}=Management
module.{APP_PREFIX}={APP_NAME} Module
module.desc.{APP_PREFIX}=Change this for your {APP_NAME} module description.
{APP_PREFIX}.my.view={APP_NAME}MV""",
    "{APP_NAME}-fx/src/main/resources/{APP_NAME}_pt.properties": """app.{APP_PREFIX}=Aplicação {APP_NAME}
menu.{APP_PREFIX}=Gerenciamento
module.{APP_PREFIX}=Módulo {APP_NAME}
module.desc.{APP_PREFIX}=Altere isso para a descrição do seu módulo {APP_NAME}.
{APP_PREFIX}.my.view={APP_NAME}MV""",
    "{APP_NAME}-model/pom.xml": """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<!-- PARENT INFO -->
	<parent>
		<groupId>org.tedros</groupId>
		<artifactId>{APP_NAME}</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>
	<!-- MODULE INFO -->
	<artifactId>{APP_NAME}-model</artifactId>
	<packaging>jar</packaging>
	<name>app model</name>
	<description>The app entitys</description>
	<!-- DEPENDENCIES -->
	<dependencies>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>tedros-server-client-api</artifactId>
			<version>${tedros.jee}</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>app-services-model</artifactId>
			<version>${app.services}</version>
		</dependency>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>app-stock-model</artifactId>
			<version>${app.stock}</version>
		</dependency>
		<dependency>
			<groupId>org.tedros</groupId>
			<artifactId>tedros-common-model</artifactId>
			<version>${tedros.jee}</version>
		</dependency>
		<dependency>
			<groupId>org.eclipse.persistence</groupId>
			<artifactId>eclipselink</artifactId>
			<version>${eclipselink.version}</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
			<version>${commons.lang.version}</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>${junit.version}</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>${compiler.plugin}</version>
				<configuration>
					<source>${java.version}</source>
					<target>${java.version}</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-source-plugin</artifactId>
				<version>${source.plugin}</version>
				<executions>
					<execution>
						<id>attach-sources</id>
						<phase>verify</phase>
						<goals>
							<goal>jar-no-fork</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<filtering>true</filtering>
				<includes>
					<include>**/*.*</include>
				</includes>
			</resource>
		</resources>
	</build>
</project>"""
}

# Also need to create standard Java package directories for each module
directories = [
    "{APP_NAME}-ejb/src/main/java/org/tedros/{APP_PACKAGE}/cdi/bo",
    "{APP_NAME}-ejb/src/main/java/org/tedros/{APP_PACKAGE}/cdi/eao",
    "{APP_NAME}-ejb/src/main/java/org/tedros/{APP_PACKAGE}/domain",
    "{APP_NAME}-ejb/src/main/java/org/tedros/{APP_PACKAGE}/ejb/controller",
    "{APP_NAME}-ejb/src/main/java/org/tedros/{APP_PACKAGE}/ejb/service",
    "{APP_NAME}-ejb/src/main/java/org/tedros/{APP_PACKAGE}/ejb/startup",
    "{APP_NAME}-ejb-client/src/main/java/org/tedros/{APP_PACKAGE}/ejb/controller",
    "{APP_NAME}-fx/src/main/java/org/tedros/{APP_PACKAGE}",
    "{APP_NAME}-model/src/main/java/org/tedros/{APP_PACKAGE}/entity"
]

for dir_tmpl in directories:
    dir_path = os.path.join(BASE_DIR, dir_tmpl.format(APP_NAME=APP_NAME, APP_PACKAGE=APP_PACKAGE).replace("/", os.sep))
    os.makedirs(dir_path, exist_ok=True)

for file_tmpl, content_tmpl in files.items():
    file_path = os.path.join(BASE_DIR, file_tmpl.format(APP_NAME=APP_NAME, APP_PACKAGE=APP_PACKAGE).replace("/", os.sep))
    # Note: replacing format placeholders for placeholders in Python strings might fail if 'content_tmpl' contains { or }
    # So instead of `.format()`, we just replace specific strings.
    real_content = content_tmpl.replace("{APP_NAME}", APP_NAME).replace("{APP_PACKAGE}", APP_PACKAGE).replace("{APP_PREFIX}", APP_PREFIX)
    os.makedirs(os.path.dirname(file_path), exist_ok=True)
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(real_content)

print(f"Project {APP_NAME} scaffolded successfully at {BASE_DIR}")
