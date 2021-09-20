# ARFNA Backend

Contains all the code to start up ARFNA API service and make connections with database for blogging and website content (minus credentials to the database of course).

## Setup
### Installing Java
The java project uses Java 8. Make sure that you have it installed on your laptop. You can check the versions using the following 
```
$ which java
	/usr/bin/java
$ java -version
	java version "1.8.0_101"
	Java(TM) SE Runtime Environment (build 1.8.0_101-b13)
	Java HotSpot(TM) 64-Bit Server VM (build 25.101-b13, mixed mode)
```
### Installing Maven
This project requires maven to build and run the project. This will install all required dependencies on your computer, including `jetty` which can be used to spin up a server on your localhost.
To check if you have it installed
```
$ which mvn
	/opt/apache-maven-3.8.2/bin/mvn
$ mvn -version
	**Apache Maven 3.8.2 (ea98e05a04480131370aa0c110b8c54cf726c06f)**
	Maven home: /opt/apache-maven-3.8.2
	Java version: 1.8.0_101, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre
	Default locale: en_CA, platform encoding: UTF-8
	OS name: "mac os x", version: "10.15.4", arch: "x86_64", family: "mac"
```
#### Downloading Maven
https://maven.apache.org/download.cgi
Downloading the `Binary tar.gz archive` is simple
#### Ensuring Maven is accessible
https://maven.apache.org/install.html
### Building Project
Compile all modules using `mvn clean install` inside of the `arfna-main` module
### Launching Servlet
In command line, run `mvn jetty:run -f pom.xml` inside of the `server` module. This will spin up a server on port `8080`. You can change this configuration inside of `server/pom.xml`