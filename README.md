# Project
## Development Environment Setup
- Install dependencies
  - .jar files can be located in `lib/` directory
  - Use the shell script in `cfg/setup.sh` to install all dependencies to the local Maven repository
  - Alternatively, use the command from the script to install the dependencies manually:
```
mvn install:install-file -Dfile=../lib/lib-gluon-charm-glisten-6.2.3.jar -DgroupId=com.gluon -DartifactId=charm -Dversion=6.2.3 -Dpackaging=jar
mvn install:install-file -Dfile=../lib/lib-gluon-util-4.0.6.jar -DgroupId=com.gluon -DartifactId=util -Dversion=4.0.6 -Dpackaging=jar
```
## Build, Packaging and Execution
Build, packaging and execution is managed by the JavaFX-Maven-plugin
- Clean, compile and run application: `mvn clean javafx:run`
- Clean, compile and package application: `mvn clean javafx:jlink`