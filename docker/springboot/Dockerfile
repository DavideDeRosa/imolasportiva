FROM eclipse-temurin:17
RUN mkdir -p /opt/conf
COPY target/imolasportiva-1.2.0-SNAPSHOT.jar /opt/app/imolasportiva-1.2.0-SNAPSHOT.jar
COPY conf/local/application.properties /opt/conf/application.properties
EXPOSE 8080
CMD ["java", "-Dopenjdk.app.properties.dir=/opt/conf", "-jar", "/opt/app/imolasportiva-1.2.0-SNAPSHOT.jar"]

#docker run --name imolasportiva --network imolasportivanetwork -p 8080:8080 imolasportiva:1.2.0
#docker start imolasportiva -a