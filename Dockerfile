# Imagen base
FROM openjdk:8
# Autor
LABEL authors="David Lache"
# Directorio donde se suben las imagenes
RUN mkdir -p /app/uploadEpps && mkdir -p /app/uploadEmpleados && mkdir -p /app/uploadNotificaciones
# Directorio donde se va copiar el JAR o aplicacion
WORKDIR /app
# Copiamos el JAR al directorio de trabajo /app
COPY ./target/spring-boot-backend-apirest-monitor-0.0.2-SNAPSHOT.jar .
# Puerto donde se expone la aplicacion
EXPOSE 8081
# Punto de entrada de comandos para ejecutar la aplicacion
ENTRYPOINT ["java", "-jar", "spring-boot-backend-apirest-monitor-0.0.1-SNAPSHOT.jar"]