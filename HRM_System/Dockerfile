FROM amazoncorretto:17
EXPOSE 8080
COPY target/*.war HRM_System.war
ENTRYPOINT [ "java","-jar","HRM_System.war" ]