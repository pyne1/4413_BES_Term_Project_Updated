FROM tomcat:9.0-jdk21-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./build/app.war /usr/local/tomcat/webapps/ROOT.war
