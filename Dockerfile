FROM tomcat
RUN ["rm", "-rf", "/usr/local/tomcat/webapps/ROOT"]
ADD target/project-base-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]